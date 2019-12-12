package com.example.testreceiptapp.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testreceiptapp.dao.RecipeDao
import com.example.testreceiptapp.model.RecipeModel
import dagger.Provides

@Database(entities = [RecipeModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // --- DAO ---
    abstract fun recipeDao(): RecipeDao

        /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //TODO: Write Migration if needed
        }
    };*/
    // --- SINGLETON ---
//    @Volatile
//    private var INSTANCE: AppDatabase? = null
}