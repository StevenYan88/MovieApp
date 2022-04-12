package com.steven.movieapp.repository

import com.steven.movieapp.base.BaseRepository
import com.steven.movieapp.http.RetrofitClient


/**
 * @description:
 * @create: 2021-07-24
 * @author: yanzhiwen
 */

class MovieRepository private constructor() : BaseRepository() {

    private val httpService by lazy { RetrofitClient.serviceApi }

    suspend fun getInTheatersMovie(apiKey: String) = httpService.getInTheatersMovie(apiKey)

    suspend fun getComingSoonMovie(apiKey: String) = httpService.getComingSoonMovie(apiKey)


    suspend fun getPraiseMovie(apiKey: String) = httpService.getPraiseMovie(apiKey)

    suspend fun getUsMovieBox(apiKey: String) = httpService.getUsMovieBox(apiKey)

    suspend fun getMovieNewMovies(apiKey: String) = httpService.getMovieNewMovies(apiKey)

    suspend fun getTop250Movie(apiKey: String, start: Int, count: Int) =
        httpService.getTop250Movie(apiKey, start, count)


    suspend fun getMovieInfo(movieId: String, apiKey: String) =
        httpService.getMovieInfo(movieId, apiKey)

    suspend fun getMovieComments(movieId: String, apiKey: String) =
        httpService.getMovieComments(movieId, apiKey)

    suspend fun getMovieSearchByTag(tag: String, start: Int, count: Int, apiKey: String) =
        httpService.getMovieSearchByTag(tag, start, count, apiKey)

    suspend fun getCelebrityPhotos(celebrityId: String, start: Int, count: Int, apiKey: String) =
        httpService.getCelebrityPhotos(celebrityId, start, count, apiKey)

    suspend fun getCelebrity(actorId: String, apiKey: String) =
        httpService.getCelebrity(actorId,apiKey)

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MovieRepository().also { instance = it }
            }
    }
}