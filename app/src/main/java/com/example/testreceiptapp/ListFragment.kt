package com.example.testreceiptapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testreceiptapp.adapter.RecipeRecyclerAdapter
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.utility.CustomOnClickListener
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : DaggerFragment() {
    private var mListener : OnListFragmentInteractionListener? = null

    @Inject lateinit var mViewModelFactory : ViewModelProvider.Factory
    private val mRecipeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(RecipeViewModel::class.java)
    }
    private lateinit var mRecipeRecyclerAdapter: RecipeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        main_fab.setOnClickListener {
            mListener?.onFabClick()
        }
    }

    private fun configureViewModel() {
        mRecipeViewModel.initRecipeList()
        mRecipeViewModel.mRecipeListLiveData?.observe(viewLifecycleOwner, Observer<List<RecipeModel>> {
            if (it != null && it.isNotEmpty()) {
                mRecipeRecyclerAdapter = RecipeRecyclerAdapter(it, object : CustomOnClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        mRecipeViewModel.selectedRecipeModel = it[position]
                        mListener?.onRecipeClick()
                    }
                })
                main_recycle_view.adapter = mRecipeRecyclerAdapter
                main_recycle_view.layoutManager = LinearLayoutManager(activity)
            }
         })
    }

    interface OnListFragmentInteractionListener{
        fun onFabClick()
        fun onRecipeClick()
    }
}