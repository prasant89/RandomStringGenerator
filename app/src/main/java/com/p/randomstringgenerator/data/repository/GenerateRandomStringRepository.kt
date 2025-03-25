package com.p.randomstringgenerator.data.repository

import com.p.randomstringgenerator.data.datasource.GenerateRandomStringDataSource
import com.p.randomstringgenerator.data.local.GeneratedString
import com.p.randomstringgenerator.data.local.StringGenDao
import com.p.randomstringgenerator.data.model.RandomTextData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class GenerateRandomStringRepository @Inject constructor(
    private val dataSource: GenerateRandomStringDataSource,
    private val stringDao: StringGenDao
) {

    fun getAllStrings(): Flow<List<RandomTextData>> =
        stringDao.getAllStrings().map { generatedStrings ->
            generatedStrings.map { generatedString ->
                RandomTextData(
                    id = generatedString.id,
                    value = generatedString.stringValue,
                    length = generatedString.length,
                    created = LocalDateTime.ofInstant(
                        generatedString.timestamp.toInstant(),
                        ZoneId.systemDefault()
                    )
                )
            }
        }

    suspend fun fetchAndInsertString(length: Int) {
        withContext(Dispatchers.IO) {
            val randomTextData = dataSource.fetchRandomText(length)
            randomTextData?.let {
                val generatedString = GeneratedString(
                    stringValue = it.value,
                    length = it.length,
                    timestamp = Date.from(it.created.atZone(ZoneId.systemDefault()).toInstant())
                )
                stringDao.insert(generatedString)
            }
        }
    }


    suspend fun deleteAllStrings() {
        withContext(Dispatchers.IO) {
            stringDao.deleteAllStrings()
        }
    }

    suspend fun deleteStringById(id: Long) {
        withContext(Dispatchers.IO) {
            stringDao.deleteStringById(id)
        }
    }
}
