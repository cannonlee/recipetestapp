package com.example.testreceiptapp

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.view_holder_main_recycle_view.view.*
import javax.inject.Inject

class UpdateFragment : DaggerFragment() {
    private var mListener : UpdateFragment.OnUpdateFragmentInteractionListener? = null

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val mRecipeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(RecipeViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is UpdateFragment.OnUpdateFragmentInteractionListener){
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnUpdateFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureDetail()

        update_edit_button.setOnClickListener {
            var recipeModel =  RecipeModel(
                id = mRecipeViewModel.selectedRecipeModel.id,
                title = update_edit_title.text.toString(),
                type = update_edit_type.text.toString(),
                time = Integer.parseInt(update_edit_time.text.toString()),
                description = update_edit_desc.text.toString(),
                ingredient = update_edit_ingredient.text.toString(),
                recipe = update_edit_recipe.text.toString(),
                image = mRecipeViewModel.selectedRecipeModel.image
            )
            mRecipeViewModel.selectedRecipeModel = recipeModel
            mRecipeViewModel.updateRecipe(mRecipeViewModel.selectedRecipeModel)
            mListener?.onBackDetail()
        }
    }

    private fun configureDetail() {
        update_edit_title.setText(mRecipeViewModel.selectedRecipeModel.title)
        update_edit_type.setText(mRecipeViewModel.selectedRecipeModel.type)
        update_edit_time.setText(mRecipeViewModel.selectedRecipeModel.time.toString())
        update_edit_desc.setText(mRecipeViewModel.selectedRecipeModel.description)
        update_edit_ingredient.setText(mRecipeViewModel.selectedRecipeModel.ingredient)
        update_edit_recipe.setText(mRecipeViewModel.selectedRecipeModel.recipe)
        val bmp = BitmapFactory.decodeByteArray(mRecipeViewModel.selectedRecipeModel.image, 0,
            mRecipeViewModel.selectedRecipeModel.image!!.size)
        update_image.setImageBitmap(bmp)
    }

    interface OnUpdateFragmentInteractionListener{
        fun onBackDetail()
    }
}