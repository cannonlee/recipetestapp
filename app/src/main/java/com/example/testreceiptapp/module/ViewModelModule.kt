package com.example.testreceiptapp.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun bindRecipeListViewModel(recipeViewModel: RecipeViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory : ViewModelFactory) : ViewModelProvider.Factory
}