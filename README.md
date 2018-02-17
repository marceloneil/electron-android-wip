# Electron Cash Android (WIP)
The majority of code is lightly modified from [python-for-android](https://github.com/kivy/python-for-android/tree/master/pythonforandroid/bootstraps/service_only)

### Setup
Install [python-for-android](https://github.com/marceloneil/python-for-android)
```
sudo python3 -m pip install git+https://github.com/marceloneil/python-for-android
```

Add path to the [Electron Cash source code](https://github.com/fyookball/electrum) in gradle.properties
```
electron=/path/to/electron-cash/
```

Add path to the [CrystaX NDK](https://www.crystax.net/en/android/ndk) in local.properties
```
ndk.dir=/path/to/crystax-ndk/
```

### To-Do list
- [x] Build System
  - [x] Modified [python-for-android](https://github.com/marceloneil/python-for-android) to provide NDK libraries and compressed source code
  - [x] Gradle tasks compatible with the CrystaX NDK
- [ ] NDK Libraries
  - [x] CrystaX Python 3
  - [ ] OpenSSL (provided, but with errors)
  - [ ] Cryptodomex
  - [ ] Python 3
- [ ] JSON-RPC
  - [ ] Java Library
  - [ ] Authentication
- [ ] Engine
  - [ ] [Environment variable patches](https://github.com/fyookball/electrum/pull/559)
- [ ] UI

