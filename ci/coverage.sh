#!/usr/bin/env bash
# Étape R&D 2 : couverture des tests unitaires (JaCoCo)
source "$(dirname "$0")/lib.sh"
log STEP "=== Couverture de tests (JaCoCo) ==="
MIN_COVERAGE="${MIN_COVERAGE:-3}"   # seuil en %, ex : MIN_COVERAGE=60 ./ci/main.sh
JACOCO=org.jacoco:jacoco-maven-plugin:0.8.12

for m in "${MODULES[@]}"; do
  mvn_module "$m" -B ${JACOCO}:prepare-agent test ${JACOCO}:report \
    || fail "Échec de la mesure de couverture sur $m" 55
  CSV="$m/target/site/jacoco/jacoco.csv"
  [ -f "$CSV" ] || fail "Rapport JaCoCo introuvable ($CSV)" 56
  # colonne 4 = instructions manquées, colonne 5 = instructions couvertes
  COV=$(awk -F, 'NR>1 {miss+=$4; cov+=$5} END {if (miss+cov==0) print 0; else printf "%.1f", 100*cov/(miss+cov)}' "$CSV")
  log INFO "$m : couverture ${COV}% (seuil ${MIN_COVERAGE}%) - rapport : $m/target/site/jacoco/index.html"
  awk -v c="$COV" -v t="$MIN_COVERAGE" 'BEGIN {exit !(c+0 >= t+0)}' \
    || fail "$m : couverture ${COV}% inférieure au seuil ${MIN_COVERAGE}%" 57
done
log OK "Couverture de tests : réussi"
