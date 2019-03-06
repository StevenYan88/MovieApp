package com.steven.movieapp.api

import androidx.lifecycle.LiveData
import com.steven.movieapp.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Description: https://www.jianshu.com/p/a7e51129b042
 * Data：2019/1/26
 * Actor:Steven
 */

interface ServiceApi {

    /**
     * 院线正在热映
     * 请求实例：https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b
     *
     */
    @GET("in_theaters")
    fun getInTheaters(@Query("apikey") apiKey: String): LiveData<BaseResult<List<Movie>>>


    /**
     *
     * 即将上映
     * 请求实例 https://api.douban.com/v2/movie/coming_soon?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("coming_soon")
    fun getComingSoon(@Query("apikey") apiKey: String): LiveData<BaseResult<List<Movie>>>

    /**
     *
     * 口碑榜
     * 请求实例 https://api.douban.com/v2/movie/weekly?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("weekly")
    fun getMovieWeekly(@Query("apikey") apiKey: String): LiveData<BaseSubjects<Weekly>>

    /**
     *
     * 北美票房榜
     * 请求实例 https://api.douban.com/v2/movie/us_box?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("us_box")
    fun getMovieUsBox(@Query("apikey") apiKey: String): LiveData<BaseSubjects<Weekly>>


    /**
     *
     * 新片榜
     * 请求实例 https://api.douban.com/v2/movie/new_movies?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("new_movies")
    fun getMovieNewMovies(@Query("apikey") apiKey: String): LiveData<BaseResult<List<Movie>>>

    /**
     * 豆瓣评分排名top250
     *
     */
    @GET("top250")
    fun getTop250Movie(@Query("apikey") apiKey: String, @Query("start") start: Int, @Query("count") count: Int):
            LiveData<BaseResult<List<Movie>>>

    @GET("subject/{movieId}")
    fun getMovieInfo(@Path("movieId") movieId: String, @Query("apikey") apikey: String): LiveData<MovieInfo>

    /**
     * 电影的短评（热评）
     */
    @GET("subject/{movieId}/comments")
    fun getComments(@Path("movieId") movieId: String, @Query("apikey") apikey: String): LiveData<Comments>


    /**
     * 影人信息
     */
    @GET("celebrity/{celebrityId}")
    fun getCelebrity(@Path("celebrityId") movieId: String, @Query("apikey") apikey: String): LiveData<ActorInfo>


    /**
     * 影人剧照
     */
    @GET("celebrity/{celebrityId}/photos")
    fun getCelebrityPhotos(
        @Path("celebrityId") movieId: String,
        @Query("start") start: Int,
        @Query("count") count: Int,
        @Query("apikey") apikey: String
    ): LiveData<Photos>


    /**
     * 根据标签搜索
     */

    @GET("search")
    fun getMovieSearchByTag(
        @Query("tag") tag: String,
        @Query("apikey") apikey: String,
        @Query("start") start: Int,
        @Query("count") count: Int
    ): LiveData<BaseResult<List<Movie>>>

}


