package com.example.developerslife.network


import com.example.developerslife.model.PostList
import com.example.developerslife.model.PostModel
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("random?json=true")
    fun getRandomPost(): Observable<PostModel>

    @GET("latest/0?json=true")
    fun getLatestPost(): Observable<PostList>

    @GET("hot/0?json=true")
    fun getHotPost(): Observable<PostList>

    @GET("top/0?json=true")
    fun getTopPost(): Observable<PostList>

    companion object {
        const val BACK_URL = "https://developerslife.ru/"
    }
}