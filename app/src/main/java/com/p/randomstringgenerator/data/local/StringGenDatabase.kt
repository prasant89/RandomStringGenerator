package com.p.randomstringgenerator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GeneratedString::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StringGenDatabase : RoomDatabase() {
    abstract fun stringDao(): StringGenDao
}