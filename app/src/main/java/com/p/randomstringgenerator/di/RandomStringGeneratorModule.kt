package com.p.randomstringgenerator.di

import android.content.ContentResolver
import android.content.Context
import com.p.randomstringgenerator.data.datasource.GenerateRandomStringDataSource
import com.p.randomstringgenerator.data.local.StringGenDao
import com.p.randomstringgenerator.data.repository.GenerateRandomStringRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RandomStringGeneratorModule {

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideDataSource(contentResolver: ContentResolver): GenerateRandomStringDataSource {
        return GenerateRandomStringDataSource(contentResolver)
    }

    @Provides
    @Singleton
    fun provideRepository(
        dataSource: GenerateRandomStringDataSource,
        stringDao: StringGenDao
    ): GenerateRandomStringRepository {
        return GenerateRandomStringRepository(dataSource, stringDao)
    }


}