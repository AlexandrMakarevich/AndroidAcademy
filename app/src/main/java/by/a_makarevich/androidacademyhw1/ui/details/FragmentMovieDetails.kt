package by.a_makarevich.androidacademyhw1.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.R
import by.a_makarevich.androidacademyhw1.adapter.ActorAdapter
import by.a_makarevich.androidacademyhw1.adapter.ActorsRecyclerDecoration
import by.a_makarevich.androidacademyhw1.data.Actor
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMovieDetails : Fragment(R.layout.fragment_movie_details) {

    private val viewModel by viewModels<FragmentMovieDetailsViewModel>()

    private var textViewBack: TextView? = null
    private var recyclerViewActors: RecyclerView? = null
    private var textViewMinimumAge: TextView? = null
    private var textViewTitle: TextView? = null
    private var textViewGenre: TextView? = null
    private var ratingBar: RatingBar? = null
    private var textViewReview: TextView? = null
    private var textViewOverview: TextView? = null
    private var imageViewTitleImage: ImageView? = null
    private var textViewCast: TextView? = null
    private var progressBar: ProgressBar? = null

    private var movieId: Int? = 0

    private val actorAdapter = ActorAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        initViews(view)
        initClickListeners()
        setUpObservers()
        setUpActorAdapter()
        movieId = getMovieId()
        viewModel.getMovie(movieId!!)

        return view
    }

    override fun onDestroyView() {
        clearCashedViews()
        super.onDestroyView()
    }

    private fun initViews(view: View) {
        textViewBack = view.findViewById(R.id.back)
        recyclerViewActors = view.findViewById(R.id.recyclerViewActors)
        textViewMinimumAge = view.findViewById(R.id.minimumAge)
        textViewTitle = view.findViewById(R.id.movie_title)
        textViewGenre = view.findViewById(R.id.genre)
        ratingBar = view.findViewById(R.id.rating_bar)
        textViewReview = view.findViewById(R.id.review)
        textViewOverview = view.findViewById(R.id.overview)
        imageViewTitleImage = view.findViewById(R.id.title_image)
        textViewCast = view.findViewById(R.id.cast)
        progressBar = view.findViewById(R.id.progress_bar)

    }

    private fun clearCashedViews() {
        textViewBack = null
        recyclerViewActors = null
        textViewMinimumAge = null
        textViewTitle = null
        textViewGenre = null
        ratingBar = null
        textViewReview = null
        textViewOverview = null
        imageViewTitleImage = null
        progressBar = null
    }

    private fun initClickListeners() {
        Log.d("MyLog", "Back pressed")
        textViewBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getMovieId() : Int? {
        return arguments?.getInt(MOVIEID)
    }

    private fun setMovie(movie: Movie) {

        Glide.with(imageViewTitleImage!!)
            .load(movie.backdrop)
            .transform(CenterInside(), RoundedCorners(24))
            .into(imageViewTitleImage!!)

        textViewMinimumAge?.text = movie.minimumAge
        textViewTitle?.text = movie.title
        textViewGenre?.text = movie.genres.joinToString(separator = ", ", transform = { it.name})
        ratingBar?.rating = movie.ratings / 2
        textViewReview?.text = movie.numberOfRatings.toString().plus(" Reviews")
        textViewOverview?.text = movie.overview
        val sizeActors = movie.actors.size
        if (sizeActors == 0) textViewCast?.visibility = INVISIBLE
    }

    private fun setUpObservers(){
        viewModel.itemmovie.observe(viewLifecycleOwner) {
            setMovie(it)
            updateAdapter(it.actors)
        }
        viewModel.status.observe(viewLifecycleOwner){
            setProgressBar(it)
        }
    }

    private fun setUpActorAdapter() {
        recyclerViewActors?.addItemDecoration(ActorsRecyclerDecoration(30))
        recyclerViewActors?.apply {
            this.adapter = actorAdapter
        }
    }
    private fun updateAdapter(actors: List<Actor>){
        actorAdapter.setData(actors)
    }

    private fun setProgressBar(status: StatusResult) {
        when (status) {
            StatusResult.Loading -> progressBar?.visibility = View.VISIBLE
            StatusResult.Success -> progressBar?.visibility = INVISIBLE
            StatusResult.Error -> progressBar?.visibility = INVISIBLE
        }
    }

    companion object {
        const val MOVIEID = "movieId"
    }

}