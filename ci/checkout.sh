#!/usr/bin/env bash
# Étape : récupération du projet (état du dépôt Git)
source "$(dirname "$0")/lib.sh"
log STEP "=== Récupération du projet ==="
run git status --short || fail "Dépôt Git invalide" 5
if [ "${CI_PULL:-0}" = "1" ]; then
  run git pull --ff-only || fail "git pull impossible" 6
fi
log INFO "Branche : $(git rev-parse --abbrev-ref HEAD) / commit : $(git rev-parse --short HEAD)"
log OK "Récupération du projet : réussi"
