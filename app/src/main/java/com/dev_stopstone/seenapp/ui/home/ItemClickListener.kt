package com.dev_stopstone.seenapp.ui.home

import com.dev_stopstone.seenapp.data.LostItem

interface ItemClickListener {
    fun onClickLostItem(lostItem: LostItem)
}