package com.p.randomstringgenerator.data.model

import com.google.gson.Gson
import java.time.LocalDateTime

data class RandomTextData(
    val id: Long?,
    val value: String,
    val length: Int,
    val created: LocalDateTime
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): RandomTextData? {
            return Gson().fromJson(json, RandomTextData::class.java)
        }
    }
}