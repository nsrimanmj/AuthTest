package com.example.authtest.data.model

sealed class UserState {
    object Loading:UserState()
    data class Success(val msg: String):UserState()
    data class Failure(val msg: String):UserState()

}