package com.zidan.myapplication

interface MainView {
    fun showMemeList(data: List<Meme>)
    fun showLoading()
    fun hideLoading()
}