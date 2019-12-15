package com.example.testreceiptapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.example.testreceiptapp.model.TypeModel

class RecipeTypeSpinnerAdapter constructor(
    @NonNull private val mContext: Context,
    @LayoutRes private val mResource: Int,
    @NonNull private val items: List<String>
): ArrayAdapter<String>(mContext, mResource, 0, items) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getDropDownView(position, convertView, parent)
    }

}