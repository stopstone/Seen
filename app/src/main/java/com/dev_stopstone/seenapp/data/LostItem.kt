package com.dev_stopstone.seenapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LostItem(
    val postId: String = "",
    val title: String = "",
    val itemUrlImage: List<String> = arrayListOf(),
    val description: String = "",
    val location: Location? = null,
    val lostDate: String = "",
    val createAt: String = "",
    val rewardPrice: String = "",
) : Parcelable