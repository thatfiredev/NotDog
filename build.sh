#!/bin/bash
# Build script based on: https://github.com/firebase/quickstart-android/blob/master/build.sh

# Exit on error
set -e

# Work off travis
if [[ ! -z TRAVIS_PULL_REQUEST ]]; then
  echo "TRAVIS_PULL_REQUEST: $TRAVIS_PULL_REQUEST"
else
  echo "TRAVIS_PULL_REQUEST: unset, setting to false"
  TRAVIS_PULL_REQUEST=false
fi

# Build
if [ $TRAVIS_PULL_REQUEST = false ] ; then
  echo "Building full project"
  # For a merged commit, build all configurations.
  echo "${GOOGLE_SERVICES}" | base64 --decode > app/google-services.json
  ./gradlew clean build
else
  # On a pull request, just build debug which is much faster and catches
  # obvious errors.
  # Copy mock google-services file
  echo "Using mock google-services.json"
  cp mock-google-services.json app/google-services.json
  ./gradlew clean assembleDebug check
fi