package com.mvvm.mvvmandroid.model.response

import com.google.gson.annotations.SerializedName

sealed class Data<out T> {
    data class SUCCESS<T>(val data: T, val msg: String) : Data<T>()
    data class ERROR<T>(val throwable: Throwable): Data<T>()
}

open class BaseRes {
    @SerializedName("message")
    val message: String = ""

    @SerializedName("status")
    val status: Int = 0
}

class BaseRes1 : BaseRes() {
    @SerializedName("data")
    val data: String = ""
}
