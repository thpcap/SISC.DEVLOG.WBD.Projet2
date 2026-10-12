#!/usr/bin/env bash
# Étape : vérification du Manifest (pom.xml) de chaque module
source "$(dirname "$0")/lib.sh"
log STEP "=== Vérification du Manifest (pom.xml) ==="
mvn_all -B validate || fail "pom.xml invalide" 10
log OK "Vérification du Manifest : réussi"
