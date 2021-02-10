package by.a_makarevich.androidacademyhw1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.R
import by.a_makarevich.androidacademyhw1.data.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class MovieAdapterPagination(private val onClickListenerDetailPagination: OnClickListenerDetailPagination) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private  val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item, null)
        Log.d("MyLog", "onCreateViewHolder")
        return MovieViewHolderPagination(view)
    }

    override fun onBindViewHolder(holderPagination: RecyclerView.ViewHolder, position: Int) {
        Log.d("MyLog", "Adapter_onBindViewHolder$position")
        (holderPagination as MovieViewHolderPagination).bind(getItem(position)!!)
        holderPagination.itemView.setOnClickListener {
            onClickListenerDetailPagination.onItemClick(getItem(position)!!.id)
        }
    }
}

class MovieViewHolderPagination(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title_item)
    private val imageViewMovie: ImageView = itemView.findViewById(R.id.image_view_movie)
    private val minimumAge: TextView = itemView.findViewById(R.id.minimumAge)
    private val genre: TextView = itemView.findViewById(R.id.genre)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
    private val reviews: TextView = itemView.findViewById(R.id.review)
    private val runTime: TextView = itemView.findViewById(R.id.runtime)
    fun bind(movie: Movie) {

        title.text = movie.title
        minimumAge.text = movie.minimumAge
        genre.text =
            movie.genres.joinToString(separator = ", ", transform = { it.name })
        ratingBar.rating = movie.ratings / 2
        reviews.text = movie.numberOfRatings.toString().plus(" Reviews")
        imageViewGlide(movie)
        if ((movie.runtime != 0)) runTime.text = movie.runtime.toString().plus(" min")
        else runTime.text = ""

    }

    private fun imageViewGlide(movie: Movie) {
        Glide.with(itemView)
            .load(movie.poster)
            .transform(CenterInside(), RoundedCorners(24))
            .error(R.drawable.ic_heart_24)
            .into(imageViewMovie)
    }
}

interface OnClickListenerDetailPagination {
    fun onItemClick(movie_id: Int)
}
