#!/bin/bash

set -e

read -p "Keystore path [./release-key.jks]: " USER_KEYSTORE_PATH
KEYSTORE_PATH="${USER_KEYSTORE_PATH:-./release-key.jks}"
KEYSTORE_ABS_PATH=$(realpath $KEYSTORE_PATH)

if [[ ! -f "$KEYSTORE_ABS_PATH" ]]; then
  echo "Keystore file not found at: $KEYSTORE_ABS_PATH"
  exit 1
fi

echo "Enter keystore credentials:"
read -p "Key alias: " KEY_ALIAS
read -s -p "Keystore password: " KEYSTORE_PASSWORD; echo

rm -rf build
mkdir -p build
for appdir in */ ; do
  if [[ -f "$appdir/config.json" && -f "$appdir/gradlew" ]]; then
    echo "===================================="
    echo "Building app in: $appdir"

    cd "$appdir"

    # Read config
    FLAG=$(jq -r '.flag' config.json)
    FILES=$(jq -r '.files[]' config.json)

    # Placeholder build
    echo "Building placeholder version..."
    ./gradlew clean assembleRelease \
      -Pandroid.injected.signing.store.file=$KEYSTORE_ABS_PATH \
      -Pandroid.injected.signing.store.password=$KEYSTORE_PASSWORD \
      -Pandroid.injected.signing.key.alias=$KEY_ALIAS \
      -Pandroid.injected.signing.key.password=$KEYSTORE_PASSWORD

    cp app/build/outputs/apk/release/app-release.apk "../build/${appdir%/}-placeholder.apk"

    echo -e "\n\n"
    # Flagged build
    echo "Replacing placeholder string with actual flag..."
    for file in $FILES; do
      echo "Modifying file: $file"
      sed -i "s/DROIDGROUND_FLAG_PLACEHOLDER/${FLAG//\//\\/}/g" "$file"
    done

    echo ""
    echo "Building flagged version..."
    ./gradlew clean assembleRelease \
      -Pandroid.injected.signing.store.file=$KEYSTORE_ABS_PATH \
      -Pandroid.injected.signing.store.password=$KEYSTORE_PASSWORD \
      -Pandroid.injected.signing.key.alias=$KEY_ALIAS \
      -Pandroid.injected.signing.key.password=$KEYSTORE_PASSWORD

    cp app/build/outputs/apk/release/app-release.apk "../build/${appdir%/}-flag.apk"

    echo "Restoring modified source files..."
    git restore .

    cd ..
    echo "Finished building: $appdir"
    echo
  fi
done

echo "All builds complete. APKs are in the \"build\" folder."
