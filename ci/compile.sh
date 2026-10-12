#!/usr/bin/env bash
# Étape : compilation (code + tests)
source "$(dirname "$0")/lib.sh"
log STEP "=== Compilation ==="
mvn_all -B compile test-compile || fail "Erreur de compilation" 30
log OK "Compilation : réussi"
