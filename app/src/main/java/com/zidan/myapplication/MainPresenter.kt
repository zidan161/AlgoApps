package com.zidan.myapplication

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainPresenter(private val view: MainView) {


    fun getMemes(){
        val getDataService = DataRepository.create()
        getDataService.getData().enqueue(object : Callback<MemeResponse>{
            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                if (response.isSuccessful){
                    val data = response.body()!!.memeList.memes
                    view.hideLoading()
                    view.showMemeList(data)
                }
            }

            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                println("error")
            }

        })
    }
}

object DataRepository {
    fun create(): ApiRepository{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.imgflip.com")
            .build()
        return retrofit.create(ApiRepository::class.java)
    }
}