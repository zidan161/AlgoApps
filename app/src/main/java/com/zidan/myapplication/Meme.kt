package com.zidan.myapplication

import com.google.gson.annotations.SerializedName

data class MemeResponse(
    @SerializedName("data")
    val memeList: Memes)

data class Memes(
    @SerializedName("memes")
    var memes: List<Meme>)

data class Meme(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("url")
    var img: String? = null,

    @SerializedName("name")
    var name: String? = null
)
