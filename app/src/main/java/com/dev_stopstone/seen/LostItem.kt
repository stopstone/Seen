package com.dev_stopstone.seen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LostItem(
    val id: String,
    val title: String,
    val itemUrlImage: String,
    val description: String,
    val location: String,
    val lostDate: String,
    val createAt: String,
) : Parcelable