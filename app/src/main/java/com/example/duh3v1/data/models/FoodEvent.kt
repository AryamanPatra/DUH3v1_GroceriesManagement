package com.example.duh3v1.data.models

import java.util.Calendar

class FoodEvent (
    val id: Long,
    val title: String,
    val description: String,
    val startTime: Calendar,
    val endTime: Calendar,
    val reminderInterval: Int, //in hours
    val itemTags: MutableList<Item>,
){

}