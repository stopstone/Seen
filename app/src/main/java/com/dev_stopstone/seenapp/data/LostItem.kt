package com.dev_stopstone.seenapp.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LostItem(
    val postId: String = "",
    val uId: String = "",
    val title: String = "",
    val imageUris: MutableList<String> = mutableListOf(),
    val description: String = "",
    val location: Location? = null,
    val lostDate: String = "",
    val createAt: String = "",
    val rewardPrice: String = "",
) : Parcelable