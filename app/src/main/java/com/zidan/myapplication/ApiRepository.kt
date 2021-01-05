package com.zidan.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiRepository {

    @GET("get_memes")
    fun getData(): Call<MemeResponse>
}