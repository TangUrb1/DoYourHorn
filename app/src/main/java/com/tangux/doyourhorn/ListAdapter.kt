package com.tangux.doyourhorn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tangux.doyourhorn.databinding.ItemListBinding

class ListAdapter (private var horns: List<Horn>) : RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horn = horns[position]
        with(holder.binding) {
            Picasso.get().load(horn.img).into(hornImageView)
            dateTextView.text = horn.date
            stateTextView.text = horn.state
        }
    }

    override fun getItemCount(): Int = horns.size
}