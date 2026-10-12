#!/usr/bin/env bash
# Étape : packaging (génération des jar)
source "$(dirname "$0")/lib.sh"
log STEP "=== Packaging ==="
mvn_all -B package -DskipTests || fail "Échec du packaging" 60
for m in "${MODULES[@]}"; do
  JAR="$(ls "$m"/target/*.jar 2>/dev/null | grep -v '/app.jar' | head -n 1)"
  [ -n "$JAR" ] || fail "Aucun jar généré dans $m/target" 61
  log INFO "Artefact : $JAR"
done
log OK "Packaging : réussi"
