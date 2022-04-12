package com.steven.movieapp.http

import com.steven.movieapp.bean.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Description:
 * Data：2019/1/26
 * Actor:Steven
 */

interface ServiceApi {

    /**
     * 院线正在热映
     * 请求实例：https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b
     * api接口已无法返回正常数据
     *
     */
    @GET("in_theaters")
    suspend fun getInTheatersMovie(@Query("apikey") apiKey: String): BaseSubjects<Movie>

    /**
     *
     * 即将上映
     * 请求实例 https://api.douban.com/v2/movie/coming_soon?apikey=0b2bdeda43b5688921839c8ecb20399b
     * api接口已无法返回正常数据
     */
    @GET("coming_soon")
    suspend fun getComingSoonMovie(@Query("apikey") apiKey: String): BaseSubjects<Movie>


    /**
     *
     * 口碑榜
     * 请求实例 https://api.douban.com/v2/movie/weekly?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("weekly")
    suspend fun getPraiseMovie(@Query("apikey") apiKey: String): BaseSubjects<Weekly>?


    /**
     *
     * 北美票房榜
     * 请求实例 https://api.douban.com/v2/movie/us_box?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("us_box")
    suspend fun getUsMovieBox(@Query("apikey") apiKey: String): BaseSubjects<Weekly>

    /**
     *
     * 新片榜
     * 请求实例 https://api.douban.com/v2/movie/new_movies?apikey=0b2bdeda43b5688921839c8ecb20399b
     */
    @GET("new_movies")
    suspend fun getMovieNewMovies(@Query("apikey") apiKey: String): BaseSubjects<Movie>


    /**
     * 豆瓣评分排名top250
     */
    @GET("top250")
    suspend fun getTop250Movie(
        @Query("apikey") apiKey: String,
        @Query("start") start: Int,
        @Query("count") count: Int
    ): BaseResult<List<Movie>>


    /**
     * 影片信息
     * @param movieId 影片id
     * @param apikey apiKey
     */
    @GET("subject/{movieId}")
    suspend fun getMovieInfo(
        @Path("movieId") movieId: String,
        @Query("apikey") apikey: String
    ): MovieInfo?

    /**
     * 电影的短评（热评）
     */
    @GET("subject/{movieId}/comments")
    suspend fun getMovieComments(
        @Path("movieId") movieId: String,
        @Query("apikey") apikey: String
    ): Comments?


    /**
     * 演员信息
     * @param actorId 演员id
     */
    @GET("celebrity/{celebrityId}")
    suspend fun getCelebrity(
        @Path("celebrityId") actorId: String,
        @Query("apikey") apikey: String
    ): ActorInfo?

    /**
     * 影人剧照
     * @param celebrityId 影片id
     */
    @GET("celebrity/{celebrityId}/photos")
    suspend fun getCelebrityPhotos(
        @Path("celebrityId") celebrityId: String,
        @Query("start") start: Int,
        @Query("count") count: Int,
        @Query("apikey") apikey: String
    ): Photos?

    /**
     * 根据标签搜索
     */
    @GET("search")
    suspend fun getMovieSearchByTag(
        @Query("tag") tag: String,
        @Query("start") start: Int,
        @Query("count") count: Int,
        @Query("apikey") apikey: String
    ): BaseResult<List<Movie>>
}


