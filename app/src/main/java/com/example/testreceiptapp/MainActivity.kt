package com.example.testreceiptapp

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testreceiptapp.adapter.RecipeRecyclerAdapter
import com.example.testreceiptapp.viewmodel.RecipeViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(),
    ListFragment.OnListFragmentInteractionListener,
    AddFragment.OnAddFragmentInteractionListener,
    DetailFragment.OnDetailFragmentInteractionListener,
    UpdateFragment.OnUpdateFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, ListFragment()).commit()
    }

    override fun onFabClick() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AddFragment()).addToBackStack(null).commit()
    }

    override fun onRecipeClick() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailFragment()).addToBackStack(null).commit()
    }

    override fun onBackAdd() {
        supportFragmentManager.popBackStack()
    }

    override fun onUpdateFragmentClick() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, UpdateFragment()).addToBackStack(null).commit()
    }

    override fun onDeleteClick() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackDetail() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
