package com.example.testreceiptapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.testreceiptapp.model.RecipeModel

@Dao
interface RecipeDao {
    @Insert(onConflict = REPLACE)
    fun save(recipeModel: RecipeModel?)

    @Query("SELECT * FROM RecipeModel")
    fun getRecipeList(): LiveData<List<RecipeModel>>

    @Query("SELECT COUNT(*) FROM RecipeModel")
    fun countRecipe(): Int

    @Update
    fun updateReceipt(recipeModel: RecipeModel)

    @Delete
    fun deleteRecipe(recipeModel: RecipeModel)
}
