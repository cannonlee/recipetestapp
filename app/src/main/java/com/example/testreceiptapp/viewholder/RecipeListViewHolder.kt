package com.example.testreceiptapp.viewholder

import android.graphics.BitmapFactory
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testreceiptapp.model.RecipeModel
import kotlinx.android.synthetic.main.view_holder_main_recycle_view.view.*


class RecipeListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindData(model : RecipeModel) {
        val bmp = BitmapFactory.decodeByteArray(model.image, 0, model.image!!.size)
        view.view_holder_main_recycle_img.setImageBitmap(bmp)
        view.view_holder_main_recycle_title.text = model.title
        view.view_holder_main_recycle_type.text = model.type_desc
        view.view_holder_main_recycle_time.text = model.time.toString()
    }
}