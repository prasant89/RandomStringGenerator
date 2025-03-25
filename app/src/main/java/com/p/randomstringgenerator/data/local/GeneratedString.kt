package com.p.randomstringgenerator.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "generated_strings")
data class GeneratedString(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val stringValue: String,
    val length: Int,
    val timestamp: Date
)