package com.photovaltscan.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.photovaltscan.app.data.local.AppDatabase
import com.photovaltscan.app.data.local.dao.ProjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "photovaltscan_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideProjectDao(db: AppDatabase): ProjectDao {
        return db.projectDao
    }

    @Provides
    @Singleton
    fun provideProjectRepository(projectDao: ProjectDao): com.photovaltscan.app.domain.repository.ProjectRepository {
        return com.photovaltscan.app.data.repository.ProjectRepositoryImpl(projectDao)
    }
}
