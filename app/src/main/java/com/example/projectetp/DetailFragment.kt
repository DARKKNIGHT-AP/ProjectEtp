package com.example.projectetp

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.content.AsyncTaskLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class DetailFragment : Fragment() {

    private var image:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image =DetailFragmentArgs.fromBundle(arguments!!).wallpaperImage


        //set image
        Glide.with(context!!).load(image).listener(

            object :RequestListener<Drawable>{
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
                    //img loads then show the button
                    detail_set_btn.visibility=View.VISIBLE
                    return false
                }
            }
        ).info(detail_image)

      detail_set_btn.setOnClickListener(this)
    }
override fun onClick(v:View?){
    when(v!!.id){
        R.id.detail_set_btn->setWallpaper()
    }
}

    private fun setWallpaper(){
        //change text and disable the button
        detail_set_btn.isEnabled=false
        detail_set_btn_text="Wallpaper Set"

        val bitmap:Bitmap=detail_image.drawable.toBitmap()
        val task:SetWallpaperTask= SetWallpaperTask(context!!.bitmap)
        task.execute(true)
    }

    companion object{
        class SetWallpaperTask internal constructor(val context: Context, val bitmap : Bitmap):
            AsyncTask<Boolean,String,String>(){
            override fun doInBackground(vararg params: Boolean?): String? {
                val wallpaperManager:WallpaperManager=WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)

                return "Wallpaper Set"
            }
            }
    }

    override fun onStop() {
        super.onStop()
        Glide.with(context!!).clear(detail_image)
    }
}