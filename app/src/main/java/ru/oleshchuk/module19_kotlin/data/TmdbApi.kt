package ru.oleshchuk.module19_kotlin.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    fun getFilms(
        @Query("api_key")
        api_key: String,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : Call<TmdbResultsDTO>
}