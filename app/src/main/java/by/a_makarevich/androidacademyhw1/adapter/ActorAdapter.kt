package by.a_makarevich.androidacademyhw1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.R
import by.a_makarevich.androidacademyhw1.data.Actor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ActorAdapter() : RecyclerView.Adapter<ViewHolderActor>() {

    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderActor {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ViewHolderActor(view)
    }

    override fun onBindViewHolder(holder: ViewHolderActor, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }
   fun setData(data: List<Actor>) {
        actors = data
    }
}

class ViewHolderActor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageActor: ImageView = itemView.findViewById(R.id.image_actor)
    private val nameActor: TextView = itemView.findViewById(R.id.text_view_actor)
  
    fun bind(actor: Actor) {
        nameActor.text = actor.name
        Glide.with(itemView)
            .load(actor.picture)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(10)))
            .into(imageActor)
    }
}