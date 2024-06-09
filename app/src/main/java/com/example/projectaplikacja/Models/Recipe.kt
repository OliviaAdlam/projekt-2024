package com.example.projectaplikacja.Models

data class Recipe(
    val id: String = "",
    val name: String = "",
    val steps: String = "",
    val preparationTime: Int = 0,
    val cuisine: String = "",
    val ingredients: String = "",
    val vegeterian: Boolean = false,
    val userId: String = "",
    val favourite: Boolean = false,
)

