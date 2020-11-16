package com.mytech.basiclayoutmanagerexample.custom

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.max
import kotlin.math.min

class CustomVerticalLayoutManager(c: Context) : RecyclerView.LayoutManager() {
    private var firstPosition = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        val parentBottom = height - paddingBottom
        val oldTopView = if (childCount > 0) getChildAt(0) else null
        var oldTop = paddingTop
        if (oldTopView != null) {
            oldTop = oldTopView.top
        }
        detachAndScrapAttachedViews(recycler)
        var top = oldTop
        var bottom: Int
        val left = paddingLeft
        val count = state.itemCount
        var i = 0
        while (firstPosition + i < count && top < parentBottom) {
            val v = recycler.getViewForPosition(firstPosition + i)
            addView(v, i)
            measureChildWithMargins(v, 0, 0)
            bottom = top + getDecoratedMeasuredHeight(v)
            val right = getDecoratedMeasuredWidth(v)
            layoutDecorated(v, left, top, right, bottom)
            i++
            top = bottom
        }
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        if (childCount == 0) {
            return 0
        }
        var scrolled = 0
        val left = paddingLeft
        if (dy < 0) {
            while (scrolled > dy) {
                val topView = getChildAt(0)
                val hangingTop = max(-getDecoratedTop(topView!!), 0)
                val scrollBy = min(scrolled - dy, hangingTop)
                scrolled -= scrollBy
                offsetChildrenVertical(scrollBy)
                if (firstPosition > 0 && scrolled > dy) {
                    firstPosition--
                    Log.d(TAG, "Get View for position $firstPosition")
                    val v = recycler.getViewForPosition(firstPosition)
                    addView(v, 0)
                    measureChildWithMargins(v, 0, 0)
                    val bottom = getDecoratedTop(topView)
                    val top = bottom - getDecoratedMeasuredHeight(v)
                    val right = getDecoratedMeasuredWidth(v)
                    layoutDecorated(v, left, top, right, bottom)
                } else {
                    break
                }
            }
        } else if (dy > 0) {
            val parentHeight = height
            while (scrolled < dy) {
                val bottomView = getChildAt(childCount - 1)
                val hangingBottom = max(getDecoratedBottom(bottomView!!) - parentHeight, 0)
                val scrollBy = -min(dy - scrolled, hangingBottom)
                scrolled -= scrollBy
                offsetChildrenVertical(scrollBy)
                if (scrolled < dy && itemCount > firstPosition + childCount) {
                    Log.d(TAG, "Get View for position ${firstPosition + childCount}")
                    val v = recycler.getViewForPosition(firstPosition + childCount)
                    val top = getDecoratedBottom(getChildAt(childCount - 1)!!)
                    addView(v)
                    measureChildWithMargins(v, 0, 0)
                    val bottom = top + getDecoratedMeasuredHeight(v)
                    val right = getDecoratedMeasuredWidth(v)
                    layoutDecorated(v, left, top, right, bottom)
                } else {
                    break
                }
            }
        }
        recycleViewsOutOfBounds(recycler)
        return scrolled
    }

    data class State(val firstPosition: Int) : Parcelable {

        constructor(parcel: Parcel) : this(parcel.readInt())

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(firstPosition)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<State> {
            override fun createFromParcel(parcel: Parcel): State = State(parcel)
            override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
        }
    }

    override fun onSaveInstanceState(): Parcelable =
        State(firstPosition)

    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as? State)?.let {
            firstPosition = state.firstPosition
        }
    }

    private fun recycleViewsOutOfBounds(recycler: Recycler?) {
        val childCount = childCount
        val parentHeight = height
        var foundFirst = false
        var first = 0
        var last = 0
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v!!.hasFocus() || getDecoratedBottom(v) >= 0 && getDecoratedTop(v) <= parentHeight
            ) {
                if (!foundFirst) {
                    first = i
                    foundFirst = true
                }
                last = i
            }
        }
        for (i in childCount - 1 downTo last + 1) {
            Log.d(TAG, "Remove and Recycled View at bottom position $i")
            //removeAndRecycleViewAt(i, recycler!!)
            detachAndScrapViewAt(i, recycler!!)
        }
        for (i in first - 1 downTo 0) {
            Log.d(TAG, "Remove and Recycled View at top position $i")
            //removeAndRecycleViewAt(i, recycler!!)
            detachAndScrapViewAt(i, recycler!!)
        }
        if (getChildCount() == 0) {
            firstPosition = 0
        } else {
            firstPosition += first
        }
    }

    companion object {
        const val TAG = "CUSTOM_VERTICAL_LAYOUT"
        private const val SCROLL_DISTANCE = 80
    }
}