package com.example.projectaplikacja.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RoomRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val steps: String,
    val preparationTime: Int,
    val cuisine: String,
    val ingredients: String,
    val isVegeterian: Boolean
)
