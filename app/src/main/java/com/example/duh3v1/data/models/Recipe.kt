package com.example.duh3v1.data.models

data class Recipe(
    val id: Long,
    val name: String,
    val ingredients: MutableList<String>,
    val steps: MutableList<String>
)