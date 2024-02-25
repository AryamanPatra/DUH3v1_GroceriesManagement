package com.example.duh3v1.data.models

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey
    val id: Long,
    val name: String,
    val quantity: Float,
    val unit: MetricUnit,
    val category: String,
    val refImage: Image?
)