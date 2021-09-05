package com.example.nikulin.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.nikulin.R
import com.example.nikulin.domain.entities.MemesEntity
import com.example.nikulin.databinding.ItemMemesBinding

class LatestMemesAdapter() : RecyclerView.Adapter<LatestMemesAdapter.ViewHolder>() {

    val latestMemes = mutableListOf<MemesEntity>()

    inner class ViewHolder(val binding: ItemMemesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMemesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()
        val imgHeight = latestMemes[position].height.toDouble()
        val imgWidth = latestMemes[position].width.toDouble()
        val newSize =
            when (imgHeight / imgWidth) {
                in 0.0..0.4 -> 400
                in 0.4..0.6 -> 600
                in 0.6..0.8 -> 800
                in 0.8..1.0 -> 1000
                in 1.0..1.4 -> 1200
                else -> 1400
            }


        holder.binding.ivItemMemes.layoutParams.height = newSize

        Glide
            .with(holder.itemView.context.applicationContext)
            .asGif()
            .load(latestMemes[position].gifURL)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.ic_error_img)
            .into(holder.binding.ivItemMemes)

        holder.binding.tvItemMems.text = latestMemes[position].description
    }

    override fun getItemCount() = latestMemes.size

    fun refresh(memesList: List<MemesEntity>) {
        latestMemes.clear()
        latestMemes.addAll(memesList)
        notifyDataSetChanged()

    }

}