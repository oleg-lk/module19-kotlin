package ru.oleshchuk.module19_kotlin.utils

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class FilmItemAnimation(val context: Context) : DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onAddStarting(item: RecyclerView.ViewHolder?) {

    }

    override fun getAddDuration(): Long {
        return 1000
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun onChangeStarting(item: RecyclerView.ViewHolder?, oldItem: Boolean) {
        super.onChangeStarting(item, oldItem)
    }

    override fun onMoveStarting(item: RecyclerView.ViewHolder?) {
        super.onMoveStarting(item)
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }


}