package com.dev_stopstone.seenapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LostItem(
    val id: String,
    val title: String,
    val itemUrlImage: String,
    val description: String,
    val location: String,
    val lostDate: String,
    val createAt: String,
    val rewardPrice: Int,
) : Parcelable