package com.p.randomstringgenerator.di

import android.content.Context
import androidx.room.Room
import com.p.randomstringgenerator.data.local.StringGenDao
import com.p.randomstringgenerator.data.local.StringGenDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStringDatabase(@ApplicationContext context: Context): StringGenDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StringGenDatabase::class.java,
            "string_database"
        ).build()
    }

    @Provides
    fun provideStringDao(database: StringGenDatabase): StringGenDao {
        return database.stringDao()
    }
}