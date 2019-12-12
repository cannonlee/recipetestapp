package com.example.testreceiptapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.testreceiptapp.dao.RecipeDao
import com.example.testreceiptapp.model.RecipeModel
import java.lang.reflect.Type
import java.util.concurrent.Executor
import javax.inject.Inject

class RecipeRepository @Inject constructor(var recipeDao: RecipeDao, var executor: Executor) {

    fun saveRecipe(recipeModel: RecipeModel) {
//        recipeDao.save(recipeModel)
        executor.execute{
            recipeDao.save(recipeModel)
        }
    }

    fun getRecipeList(): LiveData<List<RecipeModel>> {
//        var recipeModels: LiveData<List<RecipeModel>>?
//
//        executor.execute {
////            if (recipeDao.countRecipe() != 0) {
//                recipeModels = recipeDao.getRecipeList()
////            }
//        }
//
//        return recipeModels

        return recipeDao.getRecipeList()
    }

    fun updateRecipe(recipeModel: RecipeModel) {
        executor.execute{
            recipeDao.updateReceipt(recipeModel)
        }
    }

    fun deleteRecipe(recipeModel: RecipeModel) {
        executor.execute{
            recipeDao.deleteRecipe(recipeModel)
        }
    }
}