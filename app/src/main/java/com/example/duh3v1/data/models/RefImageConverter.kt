package com.example.duh3v1.data.models

import android.media.Image
import androidx.room.TypeConverter
import com.google.gson.Gson


class RefImageConverter {
    @TypeConverter
    fun fromImage(image: Image?): String{
        return if (image!=null){
            val gson = Gson()
            gson.toJson(image)
        } else{
            "null"
        }
    }
    @TypeConverter
    fun toImage(jsonString: String): Image?{
        return if (jsonString != "null"){
            val gson = Gson()
            gson.fromJson(jsonString,Image::class.java)
        } else{
            null
        }

    }
}