package com.dev_stopstone.seenapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val nickName: String = "",
) : Parcelable