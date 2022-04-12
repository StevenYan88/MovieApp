package com.steven.movieapp.viewmodel

import com.steven.movieapp.base.BaseViewModel
import com.steven.movieapp.base.support.SingleLiveData
import com.steven.movieapp.utils.API_KEY
import com.steven.movieapp.bean.*
import com.steven.movieapp.repository.MovieRepository

/**
 * Description:
 * Data：1/29/2019-2:32 PM
 * @author yanzhiwen
 */
class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    val weeklyLiveData: SingleLiveData<BaseSubjects<Weekly>> = SingleLiveData()

    val movieLiveData: SingleLiveData<BaseSubjects<Movie>> = SingleLiveData()

    val topMovieLiveData: SingleLiveData<BaseResult<List<Movie>>> = SingleLiveData()

    val movieInfoLiveData: SingleLiveData<MovieInfo> = SingleLiveData()

    val commentsLiveData: SingleLiveData<Comments> = SingleLiveData()

    val actorInfoLiveData: SingleLiveData<ActorInfo> = SingleLiveData()

    val photosLiveData: SingleLiveData<Photos> = SingleLiveData()

    val searchMovieLiveData: SingleLiveData<BaseResult<List<Movie>>> = SingleLiveData()


    /**
     * 电影详情
     * @param  movieId 影片id
     */
    fun getMovieInfo(movieId: String) {
        launch({
            val movieInfo = movieRepository.getMovieInfo(movieId, API_KEY)
            movieInfoLiveData.postValue(movieInfo)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }


    /**
     * 热评
     * @param  movieId 影片id
     */
    fun getMovieComments(movieId: String) {
        launch({
            val comments = movieRepository.getMovieComments(movieId, API_KEY)
            commentsLiveData.postValue(comments)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }


    /**
     * 影人
     * @param actorId 演员id
     */
    fun getCelebrity(actorId: String) {
        launch({
            val actorInfo = movieRepository.getCelebrity(actorId, API_KEY)
            actorInfoLiveData.postValue(actorInfo)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 影人剧照
     * @param celebrityId 影人id
     * @param start 起始位置
     * @param count 一页显示多少条
     */
    fun getCelebrityPhotos(celebrityId: String, start: Int, count: Int) {
        launch({
            val photos = movieRepository.getCelebrityPhotos(celebrityId, start, count, API_KEY)
            photosLiveData.postValue(photos)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }


    /**
     * 搜索影片
     * @param tag 标签名称
     * @param start 起始位置
     * @param count 一页显示多少条
     */
    fun getMovieSearchByTag(tag: String, start: Int, count: Int) {
        launch({
            val movieList = movieRepository.getMovieSearchByTag(tag, start, count, API_KEY)
            searchMovieLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }


    /**
     * 院线正在热映
     */
    fun getInTheatersMovie() {
        launch({
            val movieList = movieRepository.getInTheatersMovie(API_KEY)
            movieLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 院线正在热映
     */
    fun getComingSoonMovie() {
        launch({
            val movieList = movieRepository.getComingSoonMovie(API_KEY)
            movieLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 口碑榜
     */
    fun getPraiseMovie() {
        launch({
            val movieList = movieRepository.getPraiseMovie(API_KEY)
            weeklyLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }


    /**
     * 北美票房榜
     */
    fun getUsMovieBox() {
        launch({
            val movieList = movieRepository.getUsMovieBox(API_KEY)
            weeklyLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 新片榜
     */
    fun getMovieNewMovies() {
        launch({
            val movieList = movieRepository.getMovieNewMovies(API_KEY)
            movieLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 新片榜
     * @param start  起始位置
     * @param count 一页显示多少条
     */
    fun getTop250Movie(start: Int, count: Int) {
        launch({
            val movieList = movieRepository.getTop250Movie(API_KEY, start, count)
            topMovieLiveData.postValue(movieList)
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }
}