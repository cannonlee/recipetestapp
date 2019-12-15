package com.example.testreceiptapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testreceiptapp.model.TypeModel
import com.example.testreceiptapp.repository.TypeRepository
import javax.inject.Inject

class TypeViewModel @Inject constructor(private val mTypeRepository: TypeRepository) : ViewModel() {
//    private var mTypeListModel : LiveData<List<TypeModel?>?>? = null
    private var mTypeListModel : List<TypeModel?>? = null

    fun initTypeList(mContext: Context) {
        if (mTypeListModel != null) {
            return
        }

        mTypeListModel = mTypeRepository.parseTypeXML(mContext)
    }

//    fun getTypeList(): LiveData<List<TypeModel?>?>? {
    fun getTypeList(): MutableList<String> {
        var mTempTypeList: MutableList<String> = mutableListOf<String>()

        for(value in mTypeListModel!!) {
            mTempTypeList.add(value!!.type)
        }

        return mTempTypeList
    }
}