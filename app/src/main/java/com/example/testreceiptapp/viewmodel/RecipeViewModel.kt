package com.example.testreceiptapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.repository.RecipeRepository
import javax.inject.Inject

class RecipeViewModel @Inject constructor(private val mRecipeRepository: RecipeRepository) : ViewModel() {

    var mRecipeListLiveData: LiveData<List<RecipeModel>>? = null
    var mSearchRecipeListLiveData: LiveData<List<RecipeModel>>? = null
    lateinit var selectedRecipeModel : RecipeModel

    fun initRecipeList() {
        mRecipeListLiveData = null
        mRecipeListLiveData = mRecipeRepository.getRecipeList()
    }

    fun saveRecipe(recipeModel: RecipeModel) {
        mRecipeRepository.saveRecipe(recipeModel)
    }

    fun updateRecipe(recipeModel: RecipeModel) {
        mRecipeRepository.updateRecipe(recipeModel)
    }

    fun deleteRecipe(recipeModel: RecipeModel) {
        mRecipeRepository.deleteRecipe(recipeModel)
    }

    fun initSearchRecipe(searchTerm: Int) {
        mSearchRecipeListLiveData = null
        mSearchRecipeListLiveData = mRecipeRepository.searchRecipe(searchTerm)
    }
}