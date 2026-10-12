#!/usr/bin/env bash
# Étape : rollback. Usage : rollback.sh <version_précédente>
source "$(dirname "$0")/lib.sh"
COMPOSE=(docker compose -p devlog-ci -f "$PROJECT_DIR/docker-compose.ci.yml")
PREV="${1:-}"

log STEP "=== Rollback ==="

if [ -z "$PREV" ]; then
  log WARN "Aucune version précédente (premier déploiement) : arrêt de la nouvelle version"
  run "${COMPOSE[@]}" down
  exit 0
fi

if ! docker image inspect "devlog-employe:$PREV" >/dev/null 2>&1 \
   || ! docker image inspect "devlog-entreprise:$PREV" >/dev/null 2>&1; then
  run "${COMPOSE[@]}" down
  fail "Images de la version $PREV introuvables, rollback impossible" 81
fi

export VERSION="$PREV"
run "${COMPOSE[@]}" up -d --force-recreate || fail "Impossible de relancer la version $PREV" 82
log OK "Rollback effectué : la version $PREV est de nouveau en ligne"
