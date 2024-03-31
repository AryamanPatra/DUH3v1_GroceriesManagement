package com.example.duh3v1.data.models

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String = "Title",

    @ColumnInfo(name="quantity left")
    var qLeft: Float = 0f,
    @ColumnInfo(name="quantity reserved")
    var qReserved: Float = 0f,
    @ColumnInfo(name="quantity used")
    var qUsed: Float = 0f,

    var unit: MetricUnit = MetricUnit.PC,
    var category: String = "None",
    @ColumnInfo(name="reference image")
    var refImage: Image? = null,
)
