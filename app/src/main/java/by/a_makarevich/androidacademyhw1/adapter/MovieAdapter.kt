package by.a_makarevich.androidacademyhw1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.R

class MovieAdapter(private val onClickListenerDetail: OnClickListenerDetail) : RecyclerView.Adapter<ViewHolderMovie>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMovie {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item, null)
        Log.d("MyLog", "onCreateViewHolder")
        return ViewHolderMovie(view)

    }

    override fun onBindViewHolder(holder: ViewHolderMovie, position: Int) {
        holder.image_view_movie.setOnClickListener {
            onClickListenerDetail.onItemClick()
        }

    }

    override fun getItemCount(): Int {
        return 1
    }

}

class ViewHolderMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image_view_movie = itemView.findViewById<ImageView>(R.id.image_view_movie)

}

interface OnClickListenerDetail {
    fun onItemClick()
}