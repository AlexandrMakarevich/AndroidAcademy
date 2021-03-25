package by.a_makarevich.androidacademyhw1

import android.content.res.Configuration
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieListItemDecoration(private val spacing: Int, private val orientation: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val layoutManager = parent.layoutManager as GridLayoutManager

        val spanCount = when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                layoutManager.spanCount = 2
                2
            }
            else -> {
                layoutManager.spanCount = 4
                4
            }
        }
        val position = parent.getChildAdapterPosition(view) // item position
        val column: Int = position % spanCount // item column

        outRect.left =
            spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)

        outRect.right =
            (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

        outRect.bottom = spacing; // item bottom
    }
}