package com.example.testreceiptapp.module

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
//    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class,
    AppModule::class])
interface AppComponent : AndroidInjector<App> {
//interface AppComponent {
    @Component.Builder
//    abstract class Builder : AndroidInjector.Builder<App>()
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override
    fun inject(app: App)
}