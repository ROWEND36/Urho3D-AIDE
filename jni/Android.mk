# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := c++_shared
LOCAL_SRC_FILES := libc++_shared.so
ifeq ($(TARGET_ARCH_ABI),x86)
    LOCAL_CFLAGS += -ffast-math -mtune=atom -mssse3 -mfpmath=sse
endif
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Urho3D
LOCAL_SRC_FILES := libUrho3D.so

ifeq ($(TARGET_ARCH_ABI),x86)
    LOCAL_CFLAGS += -ffast-math -mtune=atom -mssse3 -mfpmath=sse
endif

include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION := .cpp .cc
LOCAL_MODULE    := 03_HelloWorld
LOCAL_C_INCLUDES := /storage/sdcard0/UCDownloads/GameEngines/Urho3D-1.7-Android-SHARED/include/
LOCAL_C_INCLUDES += /storage/sdcard0/Android/data/com.n0n3m4.droidc/files/gcc/arm-linux-androideabi/include/c++/7.2.0/
LOCAL_C_INCLUDES += /storage/sdcard0/UCDownloads/GameEngines/Urho3D-1.7-Android-SHARED/include/Urho3D/ThirdParty/
LOCAL_SRC_FILES := HelloWorld.cpp SDL_android_main.c
LOCAL_SHARED_LIBRARIES := Urho3D c++_shared
LOCAL_LDLIBS := -lGLESv2 -llog -landroid
ifeq ($(TARGET_ARCH_ABI),x86)
    LOCAL_CFLAGS += -ffast-math -mtune=atom -mssse3 -mfpmath=sse
endif

include $(BUILD_SHARED_LIBRARY)
