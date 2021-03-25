package by.a_makarevich.androidacademyhw1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.adapter.ActorAdapter

class FragmentMovieDetails : Fragment() {

    private val actorAdapter = ActorAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        val recyclerViewActors: RecyclerView? = view?.findViewById(R.id.recyclerViewActors)
        recyclerViewActors?.addItemDecoration(ActorsRecyclerDecorator(30))

        recyclerViewActors?.apply {
            this.adapter = actorAdapter
        }

        return view
    }
}