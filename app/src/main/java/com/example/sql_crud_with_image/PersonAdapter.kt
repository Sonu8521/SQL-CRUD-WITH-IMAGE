package com.example.sql_crud_with_image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PersonAdapter(private val persons: List<Person>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    class PersonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)

    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }
    override fun getItemCount(): Int {
        return persons.size
    }
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]

        // Load image using Picasso or any other image loading library
        Glide.with(holder.itemView.context)
            .load(person.imageUri)
            .placeholder(R.drawable.baseline_add_box_24)
            .error(R.drawable.baseline_add_photo_alternate_24)
            .into(holder.imageView)

        holder.textViewName.text = person.name
        holder.textViewEmail.text = person.email


        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }
}