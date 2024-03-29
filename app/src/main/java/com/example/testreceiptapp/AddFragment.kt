package com.example.testreceiptapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testreceiptapp.adapter.RecipeTypeSpinnerAdapter
import com.example.testreceiptapp.model.RecipeModel
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import com.example.testreceiptapp.viewmodel.TypeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject

class AddFragment : DaggerFragment() {
    private var mListener : AddFragment.OnAddFragmentInteractionListener? = null

    @set:Inject
    var mViewModelFactory: ViewModelProvider.Factory? = null
    var mRecipeViewModel: RecipeViewModel? = null
    private val mTypeViewModel by lazy {
        ViewModelProviders.of(activity!!, mViewModelFactory).get(TypeViewModel::class.java)
    }
    private val GALLERY = 1
    private var selectedItem: Int = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is AddFragment.OnAddFragmentInteractionListener){
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnAddFragmentInteractionListener")
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
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()

        //retrieve the xml value for the tpy
        mTypeViewModel.initTypeList(context!!)
        //init the Type spinner
        add_edit_type.adapter = RecipeTypeSpinnerAdapter(
            context!!,
            R.layout.support_simple_spinner_dropdown_item,
            mTypeViewModel.getTypeList().toList()
        )

        add_edit_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        add_edit_button.setOnClickListener(configureAddButton())
        add_button_photo.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(galleryIntent, GALLERY)
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, contentURI)
                    Toast.makeText(context, "Image Imported!", Toast.LENGTH_SHORT).show()
                    add_image!!.setImageBitmap(bitmap)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun configureViewModel() {
        mRecipeViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(RecipeViewModel::class.java)
    }

    fun configureAddButton(): View.OnClickListener {
        return View.OnClickListener {
            val bitmap = (add_image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()

            var recipeModel = RecipeModel(
                0,
                add_edit_title.text.toString(),
//                add_edit_type.text.toString(),
                selectedItem,
                mTypeViewModel.getTypeList()[selectedItem],
                Integer.parseInt(
                    when (add_edit_time.text.toString()) {
                        "" -> "0"
                        else -> add_edit_time.text.toString()
                    }
                ),
                add_edit_desc.text.toString(),
                add_edit_ingredient.text.toString(),
                add_edit_recipe.text.toString(),
                image
            )

            mRecipeViewModel!!.saveRecipe(recipeModel)

            mListener?.onBackAdd()
        }
    }

    interface OnAddFragmentInteractionListener{
        fun onBackAdd()
    }
}