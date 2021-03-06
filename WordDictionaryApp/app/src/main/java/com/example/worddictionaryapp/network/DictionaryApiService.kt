package com.example.worddictionaryapp.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"
private const val API_KEY = "ecfd5cea-fe48-49cd-a973-9c0adb24a211"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
interface DictionaryApiService {

    @GET("{word}?key=${API_KEY}")
    suspend fun getWord(@Path("word") type: String): Response<String>
}

object DictionaryApi {
    val retrofitService : DictionaryApiService by lazy { retrofit.create(DictionaryApiService::class.java) }
}