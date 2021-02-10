package by.a_makarevich.androidacademyhw1.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ActorsRecyclerDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition: Int? = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (itemPosition != 0) {
            outRect.left = spacing / 2
        } else outRect.left = spacing


        if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.right = spacing
        } else outRect.right = spacing / 2

        Log.d("MyLog", "itemCountActorsRecyclerDecorator == $itemCount")
    }
}