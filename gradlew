#!/bin/sh
set -eu

APP_HOME="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_URL="https://raw.githubusercontent.com/gradle/gradle/v8.14.3/gradle/wrapper/gradle-wrapper.jar"
EXPECTED_SHA256="7d3a4ac4de1c32b59bc6a4eb8ecb8e612ccd0cf1ae1e99f66902da64df296172"

mkdir -p "$(dirname "$WRAPPER_JAR")"

if [ ! -f "$WRAPPER_JAR" ]; then
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL --retry 3 -o "$WRAPPER_JAR" "$WRAPPER_URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$WRAPPER_JAR" "$WRAPPER_URL"
  else
    echo "ERROR: curl or wget is required to download Gradle Wrapper." >&2
    exit 1
  fi
fi

if command -v sha256sum >/dev/null 2>&1; then
  ACTUAL_SHA256="$(sha256sum "$WRAPPER_JAR" | awk '{print $1}')"
elif command -v shasum >/dev/null 2>&1; then
  ACTUAL_SHA256="$(shasum -a 256 "$WRAPPER_JAR" | awk '{print $1}')"
else
  ACTUAL_SHA256="$EXPECTED_SHA256"
fi

if [ "$ACTUAL_SHA256" != "$EXPECTED_SHA256" ]; then
  echo "ERROR: Gradle Wrapper JAR checksum mismatch." >&2
  rm -f "$WRAPPER_JAR"
  exit 1
fi

exec java -jar "$WRAPPER_JAR" "$@"
