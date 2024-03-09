package com.dev_stopstone.seenapp

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val nickName: String = "",
) : Parcelable