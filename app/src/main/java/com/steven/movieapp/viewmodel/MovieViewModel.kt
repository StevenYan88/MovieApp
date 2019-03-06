package com.steven.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.steven.movieapp.API_KEY
import com.steven.movieapp.api.RetrofitClient
import com.steven.movieapp.model.*

/**
 * Description:
 * Data：1/29/2019-2:32 PM
 * @author yanzhiwen
 */
class MovieViewModel : ViewModel() {
    /**
     * 院线热映
     */
    fun getInTheaters(): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getInTheaters(API_KEY)


    /**
     * 即将上映
     */
    fun getComingSoon(): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getComingSoon(API_KEY)

    /**
     * 口碑榜
     */
    fun getMovieWeekly(): LiveData<BaseSubjects<Weekly>> =
        RetrofitClient.serviceApi.getMovieWeekly(API_KEY)

    /**
     * 北美票房榜
     */
    fun getMovieUsBox(): LiveData<BaseSubjects<Weekly>> =
        RetrofitClient.serviceApi.getMovieUsBox(API_KEY)

    /**
     *  新片榜
     */
    fun getMovieNewMovies(): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getMovieNewMovies(API_KEY)

    /**
     *  top250
     */
    fun getTop250Movie(start: Int, count: Int): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getTop250Movie(API_KEY, start, count)

    /**
     * 电影信息
     */
    fun getMovieInfo(movieId: String): LiveData<MovieInfo> =
        RetrofitClient.serviceApi.getMovieInfo(movieId, API_KEY)

    /**
     * 热评
     */
    fun getComments(movieId: String): LiveData<Comments> =
        RetrofitClient.serviceApi.getComments(movieId, API_KEY)


    /**
     * 影人
     */
    fun getCelebrity(celebrityId: String): LiveData<ActorInfo> =
        RetrofitClient.serviceApi.getCelebrity(celebrityId, API_KEY)

    /**
     * 影人剧照
     */
    fun getCelebrityPhotos(celebrityId: String, start: Int, count: Int): LiveData<Photos> =
        RetrofitClient.serviceApi.getCelebrityPhotos(celebrityId, start, count, API_KEY)

    /**
     * 搜索影片
     */
    fun getMovieSearchByTag(
        tag: String,
        start: Int,
        count: Int
    ): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getMovieSearchByTag(tag, API_KEY, start, count)
}
