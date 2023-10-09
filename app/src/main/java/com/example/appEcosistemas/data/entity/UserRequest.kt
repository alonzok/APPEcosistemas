package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRequest(
    var username: String,
    var password: String
): Parcelable

