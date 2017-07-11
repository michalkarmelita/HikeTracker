package com.michalkarmelita.hiketracker.photoslist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.michalkarmelita.hiketracker.R
import javax.inject.Inject

/**
 * Created by MK on 10/07/2017.
 */
class PhotosAdapter @Inject constructor(): RecyclerView.Adapter<PhotoViewHolder>() {

    private val items: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder?, position: Int) {
        if (holder != null) {
            return holder.bind(items.get(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item, parent, false))

    }

    fun addItem(url: String) {
        items.add(0, url)
        notifyDataSetChanged()
    }
}

class PhotoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(url: String) {
        val photo: ImageView = view.findViewById<ImageView>(R.id.photo)
        Glide.with(view.context)
                .load(url)
                .into(photo)
    }

}
