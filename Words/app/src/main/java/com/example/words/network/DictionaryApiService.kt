package com.example.words.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//enum class WordsFilter(val value: String) {
//    SHOW_ALL("show_all"),
//    SHOW_ACTIVE("show_active"),
//    SHOW_INACTIVE("show_inactive")
//}
enum class WordsFilter {
    SHOW_ALL,
    SHOW_ACTIVE,
    SHOW_INACTIVE
}
private const val BASE_URL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface DictionaryApiService {
    @GET("{searchWord}?key=${API_KEY}")
    suspend fun getWord(@Path("searchWord") word: String): Response<String>
}

object DictionaryApi {
    val retrofitService : DictionaryApiService by lazy {
        retrofit.create(DictionaryApiService::class.java) }
}