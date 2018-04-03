#!/bin/bash

# Arguments: [electron dir] [sdk dir] [ndk dir] [api version] [ndk project path]

set -ex
echo "$@"

if [ ! -f /lib/cpp ]; then
    export CXXCPP=/usr/bin/cpp
fi

if [ ! -d venv ]; then
    python3.5 -m venv venv
fi

source venv/bin/activate

cp "$1"/electron-cash "$1"/main.py

export JNI_PATH="$5"
/usr/bin/python3 -m pythonforandroid.toolchain clean_bootstrap_builds \
  --storage-dir $(pwd)/.p4a-storage/
/usr/bin/python3 -m pythonforandroid.toolchain apk \
  --dist-name electroncash \
  --requirements python3crystax,openssl,genericndkbuild,pyjnius \
  --bootstrap library \
  --private "$1" \
  --sdk-dir "$2" \
  --ndk-dir "$3" \
  --android-api "$4" \
  --arch armeabi-v7a \
  --storage-dir $(pwd)/.p4a-storage/ \
  --java-build-tool=none \
  --blacklist $(pwd)/blacklist.txt

rm "$1"/main.py
