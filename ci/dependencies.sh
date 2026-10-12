#!/usr/bin/env bash
# Étape : gestion des dépendances
source "$(dirname "$0")/lib.sh"
log STEP "=== Gestion des dépendances ==="
mvn_all -B dependency:resolve || fail "Résolution des dépendances impossible" 20
mvn_all -B dependency:tree -Dverbose || fail "Arbre de dépendances impossible" 21
log OK "Gestion des dépendances : réussi"
