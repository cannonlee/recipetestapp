package com.example.testreceiptapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.testreceiptapp.model.SampleModel
import com.example.testreceiptapp.repository.SampleRepository
import javax.inject.Inject

class SampleViewModel @Inject constructor(private val mSampleRepository: SampleRepository) : ViewModel() {
    private var mSampleList: List<SampleModel?>? = null

    fun initSampleModel(mContext: Context) {
        if (mSampleList != null) {
            return
        }
        mSampleList = mSampleRepository.parseSampleXML(mContext)
    }

    fun getSampleList() : List<SampleModel?>? {
        return mSampleList
    }
}