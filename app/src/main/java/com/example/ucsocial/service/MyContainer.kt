package com.example.ucsocial.service

import com.example.ucsocial.model.Pengguna
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyContainer {

    class AuthInterceptor(private val bearerToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                .header("Authorization", "Bearer $bearerToken")
                .build()
            return chain.proceed(request)
        }
    }
    companion object{
        var ACCESS_TOKEN = ""
        lateinit var user: Pengguna
    }

    // change based on localhost
    private val BASE_URL = "http://10.0.2.2:8000/api/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(ACCESS_TOKEN))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val retrofitService: UserClient by lazy{
        retrofit.create(UserClient::class.java)
    }

    val myRepos: MyRepos by lazy{
        MyRepos(retrofitService)
    }

}