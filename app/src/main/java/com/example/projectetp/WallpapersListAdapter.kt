package com.example.projectetp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

//private val View.list_single_image: ImageView
//    get() {}

class WallpapersListAdapter(
    var wallpapersList:List<WallpapersModel>,
    val clickListener: (WallpapersModel)->Unit
): RecyclerView.Adapter<WallpapersListAdapter.WallpapersViewHolder>() {

    class WallpapersViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(wallpapers:WallpapersModel, clickListener: (WallpapersModel)->Unit){
            //Load image
            Glide.with(itemView.context).load(wallpapers.thumbnail).listener(
                object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }
            ).into(itemView.list_single_image)
            //click listener
            itemView.setOnClickListener{
                clickListener(wallpapers)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersViewHolder {

        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.list_single_item,parent,false)
        return WallpapersViewHolder(view)

    }

    override fun getItemCount(): Int {
     return wallpapersList.size
    }

    override fun onBindViewHolder(holder: WallpapersViewHolder, position: Int) {
        (holder as WallpapersViewHolder).bind(wallpapersList[position],clickListener)
    }
}