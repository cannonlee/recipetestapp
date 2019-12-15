package com.example.testreceiptapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testreceiptapp.adapter.RecipeRecyclerAdapter
import com.example.testreceiptapp.adapter.RecipeTypeSpinnerAdapter
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.utility.CustomOnClickListener
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import com.example.testreceiptapp.viewmodel.SampleViewModel
import com.example.testreceiptapp.viewmodel.TypeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ListFragment : DaggerFragment() {
    private var mListener : OnListFragmentInteractionListener? = null

    private val FIRST_STARTUP = "first_startup"

    @Inject lateinit var mViewModelFactory : ViewModelProvider.Factory
    private val mRecipeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(RecipeViewModel::class.java)
    }
    private val mTypeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(TypeViewModel::class.java)
    }
    private val mSampleViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(SampleViewModel::class.java)
    }
    private lateinit var mRecipeRecyclerAdapter: RecipeRecyclerAdapter
    private lateinit var mSharedPref: SharedPreferences
    private var selectedItem: Int = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnListFragmentInteractionListener){
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
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
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
        initialFirstStartup()

        main_fab.setOnClickListener {
            mListener?.onFabClick()
        }
    }

    private fun configureViewModel() {
        mSampleViewModel.initSampleModel(context!!)
        mTypeViewModel.initTypeList(context!!)
        mRecipeViewModel.initRecipeList()
        configureSpinner()

        mRecipeViewModel.mRecipeListLiveData?.observe(viewLifecycleOwner, updateListUI())
    }

    private fun initialFirstStartup() {
        // Check is the application first startup
        // If it is first, populate sample data and change the boolean to true
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        if (!mSharedPref.getBoolean(FIRST_STARTUP, false)) {
            mSharedPref.edit().putBoolean(FIRST_STARTUP, true).apply()
            setupFirstStartup()
        }
    }

    private fun setupFirstStartup() {
        if (mSampleViewModel.getSampleList() != null) {

            for (value in mSampleViewModel.getSampleList()!!) {
                //Convert image into ByteArray
                val bitmap = (ResourcesCompat.getDrawable(
                    resources,
                    resources.getIdentifier(value!!.image, "drawable", context!!.packageName),
                    null
                ) as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image = stream.toByteArray()

                val recipeModel = RecipeModel(
                    0,
                    title = value.title,
                    type_id = value.type_id,
                    type_desc = value.type_desc,
                    time = value.time,
                    description = value.description,
                    ingredient = value.ingredient,
                    recipe = value.recipe,
                    image = image
                )

                mRecipeViewModel.saveRecipe(recipeModel)
            }

            mRecipeViewModel.initRecipeList()
            mRecipeViewModel.mRecipeListLiveData?.observe(viewLifecycleOwner, updateListUI())
        }
    }

    // Call when observing the LiveData to update the UI when value change
    private fun updateListUI(): Observer<List<RecipeModel>> {
        return Observer<List<RecipeModel>> {
            if (it != null && it.isNotEmpty()) {
                mRecipeRecyclerAdapter = RecipeRecyclerAdapter(it, object : CustomOnClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        mRecipeViewModel.selectedRecipeModel = it[position]
                        mListener?.onRecipeClick()
                    }
                })
                main_recycle_view.adapter = mRecipeRecyclerAdapter
                main_recycle_view.layoutManager = LinearLayoutManager(activity)
            } else {
                main_recycle_view.adapter = null
                main_recycle_view.layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    private fun configureSpinner() {

        val tempList: MutableList<String> = mTypeViewModel.getTypeList()
        tempList.add(0, "All")

        main_spinner.adapter = RecipeTypeSpinnerAdapter(
            context!!,
            R.layout.support_simple_spinner_dropdown_item,
//            mTypeViewModel.getTypeList().toList()
            tempList.toList()
        )

        main_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = position

                if (position == 0) {
                    mRecipeViewModel.initRecipeList()
                    mRecipeViewModel.mRecipeListLiveData?.observe(viewLifecycleOwner, updateListUI())
                } else {
                    // position - 1 because added a "All" value in the beginning of the array
                    mRecipeViewModel.initSearchRecipe(position - 1)
                    mRecipeViewModel.mSearchRecipeListLiveData?.observe(viewLifecycleOwner, updateListUI())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    interface OnListFragmentInteractionListener{
        fun onFabClick()
        fun onRecipeClick()
    }
}