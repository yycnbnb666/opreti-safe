#!/usr/bin/env bash
# Pre-download STT model assets on CI (follows redirects, mirrors via hf-mirror).
set -euo pipefail
MF=app/config/stt-model-assets.properties
OUT=app/build/generated/stt-model-assets
mkdir -p "$OUT"
fail=0
while IFS='|' read -r path url size sha rest; do
  case "$path" in ''|'#'*) continue ;; esac
  [ -z "$url" ] && continue
  dest="$OUT/$path"
  if [ -f "$dest" ]; then
    cur_size=$(stat -c%s "$dest")
    cur_sha=$(sha256sum "$dest" | cut -d' ' -f1)
    if [ "$cur_size" = "$size" ] && { [ -z "$sha" ] || [ "$cur_sha" = "$sha" ]; }; then
      echo "SKIP $path"
      continue
    fi
  fi
  mkdir -p "$(dirname "$dest")"
  murl=$(printf '%s' "$url" | sed 's#https://huggingface.co#https://hf-mirror.com#')
  echo "GET $path"
  if curl -fL --retry 5 --retry-delay 3 --retry-all-errors -m 1200 -o "$dest.part" "$murl"; then
    mv -f "$dest.part" "$dest"
    echo "OK $path ($(stat -c%s "$dest") bytes)"
  else
    rm -f "$dest.part"
    echo "FAIL $path"
    fail=1
  fi
done < <(grep -v '^[[:space:]]*#' "$MF" | grep -v '^[[:space:]]*$')
echo "STT_DONE fail=$fail"
exit 0
