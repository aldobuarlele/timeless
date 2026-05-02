package com.rivaldo.timeless.data.local.di

import android.content.Context
import androidx.room.Room
import com.rivaldo.timeless.data.local.dao.DailyLogDao
import com.rivaldo.timeless.data.local.dao.UserProfileDao
import com.rivaldo.timeless.data.local.database.TimelessDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides Room database and DAO dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "timeless_database"

    @Provides
    @Singleton
    fun provideTimelessDatabase(
        @ApplicationContext context: Context
    ): TimelessDatabase {
        return Room.databaseBuilder(
            context,
            TimelessDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(
        database: TimelessDatabase
    ): UserProfileDao {
        return database.userProfileDao()
    }

    @Provides
    @Singleton
    fun provideDailyLogDao(
        database: TimelessDatabase
    ): DailyLogDao {
        return database.dailyLogDao()
    }
}
