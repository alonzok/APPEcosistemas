package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String,
    var username: String,
    var password: String
): Parcelable
