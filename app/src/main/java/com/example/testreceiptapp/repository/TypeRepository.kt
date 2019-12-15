package com.example.testreceiptapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testreceiptapp.model.TypeModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import javax.inject.Inject


class TypeRepository {

//    fun parseTypeXML(context: Context) : LiveData<List<TypeModel?>?>? {
    fun parseTypeXML(context: Context) : List<TypeModel?>? {
        val parserFactory: XmlPullParserFactory
//        var typeList: LiveData<List<TypeModel?>?>? = null
        var typeList: List<TypeModel?>? = null
        try {
            parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            val `is`: InputStream = context.assets.open("recipetypes.xml")
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(`is`, null)
            typeList = processParsing(parser)
        } catch (e: XmlPullParserException) {
        } catch (e: IOException) {
        }

        return when(typeList) {
            null -> null
            else -> typeList
        }
    }

    @Throws(IOException::class, XmlPullParserException::class)
//    private fun processParsing(parser: XmlPullParser) : LiveData<List<TypeModel?>> {
    private fun processParsing(parser: XmlPullParser) : List<TypeModel?> {
        val typesLiveData: MutableLiveData<List<TypeModel?>> = MutableLiveData<List<TypeModel?>>()
        val types: ArrayList<TypeModel?> = ArrayList()
        var eventType = parser.eventType
        var currentType: TypeModel? = null
        while (eventType != XmlPullParser.END_DOCUMENT) {
            var eltName: String? = null
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    eltName = parser.name
                    if ("type" == eltName) {
                        currentType = TypeModel()
                        types.add(currentType)
                    } else if (currentType != null) {
                        if ("name" == eltName) {
                            currentType.type = parser.nextText()
                        }
                    }
                }
            }
            eventType = parser.next()
        }

//        typesLiveData.value(types.toList())
//
//        return typesLiveData.value(types.toList())

        return types
    }
}