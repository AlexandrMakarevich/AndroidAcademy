package by.a_makarevich.androidacademyhw1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.R

class ActorAdapter() : RecyclerView.Adapter<ViewHolderActor>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderActor {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, null)
        return ViewHolderActor(view)
    }

    override fun onBindViewHolder(holder: ViewHolderActor, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }


}

class ViewHolderActor(itemView: View) : RecyclerView.ViewHolder(itemView){

}