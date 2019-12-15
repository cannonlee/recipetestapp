package com.example.testreceiptapp.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.testreceiptapp.dao.RecipeDao
import com.example.testreceiptapp.repository.RecipeRepository
import com.example.testreceiptapp.repository.SampleRepository
import com.example.testreceiptapp.repository.TypeRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    // ---- DATABASE INJECTION ---- //
    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        //add .fallbackToDestructiveMigration() if want to clear DB when updating DB version
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "AppDatabase.db"
        ) //.addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.recipeDao()
    }

    // ---- REPOSITORY INJECTION ---- //
    // --- REPOSITORY INJECTION ---
    @Provides
    open fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDao: RecipeDao, executor: Executor): RecipeRepository {
        return RecipeRepository(recipeDao, executor)
    }

    @Provides
    @Singleton
    fun provideTypeRepository(): TypeRepository {
        return TypeRepository()
    }

    @Provides
    @Singleton
    fun provideSampleRepository(): SampleRepository {
        return SampleRepository()
    }

    // ---- API INTERFACE INJECTION ---- //
//    @Provides
//    @Singleton
//    fun provideApiInterface() : APIInterface{
//        return APIClient.getAPIClient().create(APIInterface::class.java)
//    }
}