package com.steven.movieapp.http

import android.util.Log
import com.steven.movieapp.utils.BASE_URL
import com.steven.movieapp.utils.TAG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
class RetrofitClient {
    companion object {
        val serviceApi: ServiceApi by lazy {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder().addInterceptor(
                        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                            override fun log(message: String) {
                                Log.d("RetrofitClient", "log: $message")
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
            retrofitClient.create(ServiceApi::class.java)
        }
    }
}
