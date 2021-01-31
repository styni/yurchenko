package com.example.developerslife.data

import com.example.developerslife.model.PostList
import com.example.developerslife.model.PostModel
import com.example.developerslife.network.Api
import io.reactivex.Observable
import javax.inject.Inject

class PostRepository @Inject constructor(private val api: Api) {

    fun getRandomPost(): Observable<PostModel> {
        return api.getRandomPost()
    }

    fun getLatestPost(): Observable<PostList>{
        return api.getLatestPost()
    }

    fun getHotPost(): Observable<PostList>{
        return api.getHotPost()
    }

    fun getTopPost(): Observable<PostList>{
        return api.getTopPost()
    }

}