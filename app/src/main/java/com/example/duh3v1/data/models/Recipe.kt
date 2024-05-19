package com.example.duh3v1.data.models

data class Recipe(
    val id: Long,
    val recipeName: String,
    val description: String,
    val ingredients: MutableList<Int>,
    val instructions: MutableList<String>,
    val difficulty: Difficulty = Difficulty.EASY,
    val prepTime: Int, // in minutes
    val cookingTime: Int, // in minutes
    val totalTime: Int, // in minutes = prepTime + cookingTime, let's just keep it for now
    val recipePicture: ByteArray, //as recommended https://stackoverflow.com/questions/46337519/how-insert-image-in-room-persistence-library
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}