LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE            := main
LOCAL_SRC_FILES         := start.c pyjniusjni.c
LOCAL_LDLIBS            := -llog $(EXTRA_LDLIBS)
LOCAL_SHARED_LIBRARIES  := python_shared
include $(BUILD_SHARED_LIBRARY)

$(call import-module,python/3.5)