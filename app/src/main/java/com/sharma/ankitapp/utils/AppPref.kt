package com.sharma.ankitapp.utils

import PREF_ACCESS_TOKEN
import PREF_FCM_TOKEN
import PREF_IS_LOGGED_IN
import PREF_USER_ID
import com.sharma.ankitapp.core.BaseApp


var isLoggedIn: Boolean
    set(value) {
        BaseApp.INSTANCE.xtnPutKey(PREF_IS_LOGGED_IN, "$value")
    }
    get() {
        BaseApp.INSTANCE.xtnGetKey(PREF_IS_LOGGED_IN)?.let {
            return if (it.isNotEmpty()) it.toBoolean()
            else false
        } ?: kotlin.run {
            return false
        }
    }

var accessToken: String?
    set(value) {
        BaseApp.INSTANCE.xtnPutKey(PREF_ACCESS_TOKEN, value ?: "")
    }
    get() {
        return BaseApp.INSTANCE.xtnGetKey(PREF_ACCESS_TOKEN)
    }


var userId: String?
    set(value) {
        BaseApp.INSTANCE.xtnPutKey(PREF_USER_ID, value ?: "")
    }
    get() {
        return BaseApp.INSTANCE.xtnGetKey(PREF_USER_ID)
    }


var fcmToken: String?
    set(value) {
        BaseApp.INSTANCE.xtnPutKey(PREF_FCM_TOKEN, value ?: "")
    }
    get() {
        return BaseApp.INSTANCE.xtnGetKey(PREF_FCM_TOKEN)
    }

