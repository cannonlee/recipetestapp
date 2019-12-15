package com.example.testreceiptapp.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.testreceiptapp.model.SampleModel
import com.example.testreceiptapp.model.TypeModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

class SampleRepository {

    fun parseSampleXML(context: Context) : List<SampleModel?>? {
        val parserFactory: XmlPullParserFactory
        var sampleList: List<SampleModel?>? = null
        try {
            parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            val `is`: InputStream = context.assets.open("samplerecipes.xml")
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(`is`, null)
            sampleList = processParsing(parser)
        } catch (e: XmlPullParserException) {
        } catch (e: IOException) {
        }

        return when(sampleList) {
            null -> null
            else -> sampleList
        }
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun processParsing(parser: XmlPullParser) : List<SampleModel?> {
        val samples: ArrayList<SampleModel?> = ArrayList()
        var eventType = parser.eventType
        var currentSample: SampleModel? = null
        while (eventType != XmlPullParser.END_DOCUMENT) {
            var eltName: String? = null
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    eltName = parser.name
                    if ("recipe" == eltName) {
                        currentSample = SampleModel()
                        samples.add(currentSample)
                    } else if (currentSample != null) {
                        if ("title" == eltName) {
                            currentSample.title = parser.nextText()
                        } else if("type_id" == eltName) {
                            currentSample.type_id = parser.nextText().toInt()
                        } else if("type_desc" == eltName) {
                            currentSample.type_desc = parser.nextText()
                        } else if ("time" == eltName) {
                            currentSample.time = parser.nextText().toInt()
                        } else if("description" == eltName) {
                            currentSample.description = parser.nextText()
                        } else if("ingredient" == eltName) {
                            currentSample.ingredient = parser.nextText()
                        } else if("steps" == eltName) {
                            currentSample.recipe = parser.nextText()
                        } else if("image" == eltName) {
                            currentSample.image = parser.nextText()
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        return samples
    }
}