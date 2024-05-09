package com.example.duh3v1.data.models

data class Recipe(
    val id: Long,
    val recipeName: String,
    val description: String,
    val ingredients: MutableList<Item>,
    val instructions: MutableList<String>,
    val difficulty: Difficulty = Difficulty.EASY,
    val prepTime: Int, // in minutes
    val cookingTime: Int, // in minutes
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}