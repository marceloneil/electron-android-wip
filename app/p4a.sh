#!/bin/bash

# Arguments: [electron dir] [sdk dir] [ndk dir] [api version]

set -ex

mv "$1"/electron-cash "$1"/main.py

/usr/bin/python3 -m pythonforandroid.toolchain apk \
  --dist-name electroncash \
  --requirements python3crystax,openssl \
  --bootstrap library \
  --private "$1" \
  --sdk-dir "$2" \
  --ndk-dir "$3" \
  --android-api "$4" \
  --arch armeabi-v7a \
  --storage-dir $(pwd)/.p4a-storage/ \
  --java-build-tool=none \
  --blacklist $(pwd)/blacklist.txt

mv "$1"/main.py "$1"/electron-cash