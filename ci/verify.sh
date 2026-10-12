#!/usr/bin/env bash
# Étape : vérification (tests d'intégration)
source "$(dirname "$0")/lib.sh"
log STEP "=== Vérification (mvn verify) ==="
mvn_all -B verify || fail "Échec de mvn verify" 70
log OK "Vérification : réussi"
