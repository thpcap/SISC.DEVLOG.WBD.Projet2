#!/usr/bin/env bash
# Étape R&D 1 : qualité de code (Checkstyle, règles Google)
source "$(dirname "$0")/lib.sh"
log STEP "=== Qualité de code (Checkstyle) ==="
# On échoue uniquement sur les violations de sévérité "error"
mvn_all -B org.apache.maven.plugins:maven-checkstyle-plugin:3.4.0:check \
  -Dcheckstyle.config.location=google_checks.xml \
  -Dcheckstyle.violationSeverity=error \
  || fail "Violations Checkstyle bloquantes" 50
log OK "Qualité de code : réussi"
