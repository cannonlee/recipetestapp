package com.example.testreceiptapp.module

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

//class App : DaggerApplication(){
//
//    override fun onCreate() {
//        super.onCreate()
//    }
//
//    override fun applicationInjector(): AndroidInjector<out App> {
//        return DaggerAppComponent.builder().create(this)
//    }
//}

class App : Application(),
    HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDagger()
        context = applicationContext
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector!!
    }

    fun initDagger() {
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    companion object {
        var context: Context? = null
    }
}