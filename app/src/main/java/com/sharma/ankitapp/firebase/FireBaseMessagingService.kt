package com.sharma.ankitapp.firebase

import com.sharma.ankitapp.utils.fcmToken
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FireBaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        fcmToken = s
        Timber.d("fcm token: $s")
    }
}