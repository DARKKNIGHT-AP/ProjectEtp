package com.example.projectetp

import android.media.Image
import java.sql.Timestamp

data class WallpapersModel(
    val name:String="",val image: String="",val thumbnail:String="",val date:Timestamp?=null
)
