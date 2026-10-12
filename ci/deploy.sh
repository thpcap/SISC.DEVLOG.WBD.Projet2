#!/usr/bin/env bash
# Étape : déploiement Docker + test de santé + rollback automatique
source "$(dirname "$0")/lib.sh"

COMPOSE=(docker compose -p devlog-ci -f "$PROJECT_DIR/docker-compose.ci.yml")
STATE_FILE="$CI_DIR/.deployed_version"        # dernière version déployée avec succès
HEALTH_URLS="${HEALTH_URLS:-http://localhost:8080/ http://localhost:8081/ http://localhost:80/}"
HEALTH_RETRIES="${HEALTH_RETRIES:-30}"        # 30 x 2 s = 60 s max par URL
VERSION="$(git describe --tags --always 2>/dev/null || echo local)-$(date +%Y%m%d%H%M%S)"
export VERSION

log STEP "=== Déploiement Docker (version $VERSION) ==="

docker info >/dev/null 2>&1 || fail "Docker n'est pas démarré (ouvre Docker Desktop)" 90
docker compose version >/dev/null 2>&1 || fail "Commande 'docker compose' indisponible" 90

# Si la stack de développement (Start.sh) tourne, les ports sont déjà pris
if docker ps --format '{{.Names}}' | grep -qE '^(mon-apache|Employe|Entreprise)$'; then
  fail "La stack de dev tourne encore : lance ./Stop.sh avant la pipeline" 94
fi

# 1) Version actuellement en ligne (pour le rollback)
PREV_VERSION="$(cat "$STATE_FILE" 2>/dev/null || true)"
[ -n "$PREV_VERSION" ] && log INFO "Version actuellement en ligne : $PREV_VERSION" \
                       || log INFO "Aucune version précédente (premier déploiement)"

# 2) Build d'une image par backend
for pair in "Employe:employe" "Entreprise:entreprise"; do
  DIR="Backend/${pair%%:*}"; NAME="devlog-${pair##*:}"
  JAR="$(ls "$DIR"/target/*.jar 2>/dev/null | grep -v '/app.jar' | head -n 1)"
  [ -n "$JAR" ] || fail "Aucun jar dans $DIR/target (lance package.sh avant)" 91
  cp "$JAR" "$DIR/target/app.jar"
  run docker build -f ci/Dockerfile.backend -t "$NAME:$VERSION" "$DIR" \
    || fail "docker build a échoué pour $NAME" 92
done

# 3) Lancement de la nouvelle version
run "${COMPOSE[@]}" up -d || fail "docker compose up a échoué" 93

# 4) Test de santé : chaque URL doit répondre en HTTP avec un code < 500
check_url() {
  local url="$1" i code
  for i in $(seq 1 "$HEALTH_RETRIES"); do
    code="$(curl -s -o /dev/null -w '%{http_code}' --max-time 3 "$url" || true)"
    if [ -n "$code" ] && [ "$code" != "000" ] && [ "$code" -lt 500 ]; then
      log OK "$url répond (HTTP $code)"
      return 0
    fi
    log INFO "$url : tentative $i/$HEALTH_RETRIES, pas encore prêt (code=${code:-n/a})"
    sleep 2
  done
  return 1
}

ALL_OK=1
for url in $HEALTH_URLS; do
  check_url "$url" || { log ERROR "$url ne répond pas"; ALL_OK=0; break; }
done

# 5) Succès, ou rollback
if [ "$ALL_OK" -eq 1 ]; then
  echo "$VERSION" > "$STATE_FILE"
  log OK "Application déployée et opérationnelle (version $VERSION)"
  exit 0
fi

log ERROR "Déploiement en échec : logs des conteneurs ci-dessous"
"${COMPOSE[@]}" logs --tail 30 2>&1 | tee -a "$LOG_FILE"
bash "$CI_DIR/rollback.sh" "$PREV_VERSION"
exit 100
