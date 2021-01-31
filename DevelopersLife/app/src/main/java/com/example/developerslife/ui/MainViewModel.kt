package com.example.developerslife.ui

import androidx.lifecycle.ViewModel
import com.example.developerslife.data.PostRepository
import com.example.developerslife.di.App
import com.example.developerslife.model.PostList
import com.example.developerslife.model.PostModel
import io.reactivex.Observable
import javax.inject.Inject
import kotlin.math.max

class MainViewModel : ViewModel() {

    @Inject
    lateinit var repository: PostRepository

    private var index = -1
    private var list: ArrayList<PostModel> = ArrayList()

    init {
        App.instance.appComponent?.inject(this)
    }

    fun next(): Observable<PostModel>{
        return if (index == -1 || index == list.lastIndex) {
            index++
            loadGIF()
        } else {
            index++
            Observable.just(list[index])
        }
    }

    fun previous(): PostModel {
        index = max(index - 1, 0)
        return list[index]
    }

    private fun loadGIF(): Observable<PostModel> {
        return repository.getRandomPost()
                .map {
                    list.add(it)
                    it
                }

    }

    fun getLatestPost(): Observable<PostList> {
        return repository.getLatestPost()
    }

    fun getHotPost(): Observable<PostList> {
        return repository.getHotPost()
    }

    fun getTopPost(): Observable<PostList> {
        return repository.getTopPost()
    }

}