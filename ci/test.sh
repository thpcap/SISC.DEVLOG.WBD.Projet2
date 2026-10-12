#!/usr/bin/env bash
# Étape : tests unitaires
source "$(dirname "$0")/lib.sh"
log STEP "=== Tests unitaires ==="
mvn_all -B test || fail "Des tests unitaires échouent" 40
log OK "Tests unitaires : réussi"
