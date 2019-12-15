package com.example.testreceiptapp

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testreceiptapp.adapter.RecipeTypeSpinnerAdapter
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import com.example.testreceiptapp.viewmodel.TypeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
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
    private val mTypeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(TypeViewModel::class.java)
    }

    private var selectedItem: Int = 0

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

        //retrieve the xml value for the tpy
        mTypeViewModel.initTypeList(context!!)
        //init the Type spinner
        update_edit_type.adapter = RecipeTypeSpinnerAdapter(
            context!!,
            R.layout.support_simple_spinner_dropdown_item,
            mTypeViewModel.getTypeList().toList()
        )

        update_edit_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        update_edit_button.setOnClickListener {
            var recipeModel =  RecipeModel(
                id = mRecipeViewModel.selectedRecipeModel.id,
                title = update_edit_title.text.toString(),
                type_id = selectedItem,
                type_desc = mTypeViewModel.getTypeList()[selectedItem],
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


        configureDetail()
    }

    private fun configureDetail() {
        update_edit_title.setText(mRecipeViewModel.selectedRecipeModel.title)
        update_edit_type.setSelection(mRecipeViewModel.selectedRecipeModel.type_id)
        update_edit_time.setText(mRecipeViewModel.selectedRecipeModel.time.toString())
        update_edit_desc.setText(mRecipeViewModel.selectedRecipeModel.description.replace("\\n", System.getProperty("line.separator")!!, false))
        update_edit_ingredient.setText(mRecipeViewModel.selectedRecipeModel.ingredient.replace("\\n", System.getProperty("line.separator")!!, false))
        update_edit_recipe.setText(mRecipeViewModel.selectedRecipeModel.recipe.replace("\\n", System.getProperty("line.separator")!!, false))
        val bmp = BitmapFactory.decodeByteArray(mRecipeViewModel.selectedRecipeModel.image, 0,
            mRecipeViewModel.selectedRecipeModel.image!!.size)
        update_image.setImageBitmap(bmp)
    }

    interface OnUpdateFragmentInteractionListener{
        fun onBackDetail()
    }
}