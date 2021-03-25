package by.a_makarevich.androidacademyhw1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.adapter.MovieAdapter
import by.a_makarevich.androidacademyhw1.adapter.OnClickListenerDetail


class FragmentMoviesList : Fragment(), OnClickListenerDetail {

    private val movieAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyLog", "FragmentMoviesList_onCreateView")
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)

        val orientation = resources.configuration.orientation

        recyclerView?.addItemDecoration(MovieListItemDecoration(50, orientation))


        recyclerView.apply {
            this?.adapter = movieAdapter
        }
        return view
    }

    override fun onItemClick() {
        findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMovieDetails)
    }


}