package com.p.randomstringgenerator.data.datasource

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.p.randomstringgenerator.data.model.RandomTextData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId

class GenerateRandomStringDataSource(
    private val contentResolver: ContentResolver
) {
    companion object {
        private val CONTENT_URI: Uri = Uri.parse("content://com.iav.contestdataprovider/text")
        private const val COLUMN_DATA = "data"
    }

    suspend fun fetchRandomText(length: Int): RandomTextData? {
        return withContext(Dispatchers.IO) {
            try {
                val queryArgs = Bundle().apply {
                    putInt(ContentResolver.QUERY_ARG_LIMIT, length)
                }

                val cursor: Cursor? = contentResolver.query(
                    CONTENT_URI,
                    arrayOf(COLUMN_DATA),
                    queryArgs,
                    null
                )

                cursor?.use {
                    if (it.moveToFirst()) {
                        val jsonString = it.getString(it.getColumnIndexOrThrow(COLUMN_DATA))
                        return@withContext parseJsonToRandomText(jsonString)
                    }
                }
                return@withContext null
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }


    private fun parseJsonToRandomText(jsonString: String): RandomTextData {
        val jsonObject = JSONObject(jsonString).getJSONObject("randomText")

        val value = jsonObject.getString("value")
        val length = jsonObject.getInt("length")
        val created = jsonObject.getString("created")

        return RandomTextData(
            id = null,
            value = value,
            length = length,
            created = Instant.parse(created).atZone(ZoneId.systemDefault()).toLocalDateTime()
        )
    }
}