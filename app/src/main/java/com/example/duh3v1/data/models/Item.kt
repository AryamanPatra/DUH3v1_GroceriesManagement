package com.example.duh3v1.data.models

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "Title",

    @ColumnInfo(name="quantity left")
    val qLeft: Float = 0f,
    @ColumnInfo(name="quantity reserved")
    val qReserved: Float = 0f,
    @ColumnInfo(name="quantity used")
    val qUsed: Float = 0f,

    val unit: MetricUnit = MetricUnit.PC,
    val category: String = "None",
    @ColumnInfo(name="reference image")
    val refImage: Image? = null,
)
