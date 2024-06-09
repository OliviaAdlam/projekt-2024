package com.example.projectaplikacja.Models

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RoomRecipe::class], version = 4)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RoomRecipeDao

    companion object {
        @Volatile
        private var Instance:RecipeDatabase? = null

        fun getDatabase(context: Context):RecipeDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,RecipeDatabase::class.java,"recipe_database")
                    .addCallback(roomDatabaseCallback)
                    .build()
                    .also { Instance = it}
            }
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("RecipeDatabase", "Database onCreate called")
                Instance?.let { database ->
                    DatabaseInitializer.prepopulate(database)
                }
            }
        }
    }
}
