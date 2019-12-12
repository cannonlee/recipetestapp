package com.example.testreceiptapp.module

import com.example.testreceiptapp.AddFragment
import com.example.testreceiptapp.DetailFragment
import com.example.testreceiptapp.ListFragment
import com.example.testreceiptapp.UpdateFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeListFragment() : ListFragment
    @ContributesAndroidInjector
    abstract fun contributeAddFragment() : AddFragment
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment() : DetailFragment
    @ContributesAndroidInjector
    abstract fun contributeUpdateFragment() : UpdateFragment
}