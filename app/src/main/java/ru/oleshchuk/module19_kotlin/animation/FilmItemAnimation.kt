package ru.oleshchuk.module19_kotlin.animation

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.adapter.FilmAdapter
import ru.oleshchuk.module19_kotlin.view.MyRatingView

class FilmItemAnimation(val context: Context) : DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        Log.d("lkLog", "animateChange : start")
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        Log.d("lkLog", "start")
        return true
    }

    override fun onAddStarting(item: RecyclerView.ViewHolder?) {
        Log.d("lkLog", "start")

    }

    override fun getAddDuration(): Long {
        return 1000
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        Log.d("lkLog", "animateAdd : start")
//        (item as FilmAdapter.FilmHolder)?.apply {
//            val view = this.item.findViewById<MyRatingView>(R.id.vwRating)
//            Log.d("lkLog", "view = $view")
//        }
        return true
    }

    override fun onChangeStarting(item: RecyclerView.ViewHolder?, oldItem: Boolean) {
        Log.d("lkLog", "onChangeStarting : start")
        super.onChangeStarting(item, oldItem)
    }

    override fun onMoveStarting(item: RecyclerView.ViewHolder?) {
        Log.d("lkLog", "onMoveStarting : start")
        super.onMoveStarting(item)
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        Log.d("lkLog", "animateChange : start")
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }


}