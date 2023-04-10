package ru.oleshchuk.module19_kotlin.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/{category}")
    fun getFilms(
        @Path("category")
        category: String,
        @Query("api_key")
        api_key: String,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : Call<TmdbResultsDTO>

    @GET("search/movie")
    fun searchFilm(
        @Query("api_key")
        api_key: String,
        @Query("language")
        language: String,
        @Query("query")
        query: String,
        @Query("page")
        page: Int): Observable<TmdbResultsDTO>
}