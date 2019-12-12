package com.example.testreceiptapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.testreceiptapp.R
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.utility.CustomOnClickListener
import com.example.testreceiptapp.viewholder.RecipeListViewHolder

class RecipeRecyclerAdapter(private val recipeList : List<RecipeModel>, private val mListener : CustomOnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val viewHolder = RecipeListViewHolder(view)
        view.setOnClickListener{
            mListener.onItemClick(it, viewHolder.adapterPosition)
        }
        return viewHolder as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecipeListViewHolder).bindData(recipeList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.view_holder_main_recycle_view
    }
}