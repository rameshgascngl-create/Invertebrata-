#!/usr/bin/env bash
set -euo pipefail
bash .github/scripts/wait-native-external-storage.sh
case "$1" in
  established) exec bash .github/scripts/run-native-api34-tests.sh ;;
  unit23) exec bash .github/scripts/run-unit23-editorial-qa.sh ;;
  ciliary) exec bash .github/scripts/run-native-r16-ciliary.sh ;;
  quality) exec bash .github/scripts/run-native-r161-quality.sh ;;
  water) exec bash .github/scripts/run-native-r18-quality.sh ;;
  nuclear) exec bash .github/scripts/run-native-r17-quality.sh ;;
  nutrition) exec bash .github/scripts/run-native-r19-quality.sh ;;
  process-death) exec bash .github/scripts/run-native-n14-process-death.sh ;;
  *) exit 2 ;;
esac
