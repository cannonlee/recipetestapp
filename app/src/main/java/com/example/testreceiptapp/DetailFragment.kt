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
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class DetailFragment : DaggerFragment() {
    private var mListener : DetailFragment.OnDetailFragmentInteractionListener? = null

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val mRecipeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(RecipeViewModel::class.java)
    }
    lateinit var mRecipeModel: RecipeModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is DetailFragment.OnDetailFragmentInteractionListener){
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnDetailFragmentInteractionListener")
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
        return inflater.inflate(R.layout.fragment_detail,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureDetail()

        detail_button_edit.setOnClickListener {
            mRecipeViewModel.selectedRecipeModel = mRecipeModel
            mListener?.onUpdateFragmentClick()
        }

        detail_button_delete.setOnClickListener {
            mRecipeViewModel.deleteRecipe(mRecipeModel)
            mListener?.onDeleteClick()
        }
    }

    private fun configureDetail() {
        mRecipeModel = mRecipeViewModel.selectedRecipeModel
        detail_edit_title.text = mRecipeModel.title
        detail_edit_type.text = mRecipeModel.type
        detail_edit_time.text = mRecipeModel.time.toString()
        detail_edit_desc.text = mRecipeModel.description
        detail_edit_ingredient.text = mRecipeModel.ingredient
        detail_edit_recipe.text = mRecipeModel.recipe
        val bmp = BitmapFactory.decodeByteArray(mRecipeModel.image, 0,
            mRecipeModel.image!!.size)
        detail_image.setImageBitmap(bmp)
    }

    interface OnDetailFragmentInteractionListener{
        fun onUpdateFragmentClick()
        fun onDeleteClick()
    }
}