package com.example.developerslife.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.developerslife.R
import com.example.developerslife.databinding.ActivityMainBinding
import com.example.developerslife.model.LoadState
import com.example.developerslife.model.PostModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val disposable = CompositeDisposable()
    private var loadingState: MutableLiveData<LoadState?> =  MutableLiveData<LoadState?>().apply {
        value = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observe()
        binding?.next?.callOnClick()
        binding?.prev?.visibility = View.GONE
    }

    private fun observe() {
        binding?.next?.setOnClickListener {
            loadingState.value = LoadState.Loading
            disposable.add(
                binding?.viewModel?.next()
                    ?.delay(500, TimeUnit.MILLISECONDS)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        setData(it)
                    },{
                        loadingState.value = LoadState.Error
                    })!!
            )
        }

        binding?.prev?.setOnClickListener {
            binding?.viewModel?.previous()?.let { it -> setData(it) }
        }

        binding?.last?.setOnClickListener {
            loadingState.value = LoadState.Loading
            disposable.add(
                binding?.viewModel?.getLatestPost()
                    ?.delay(500, TimeUnit.MILLISECONDS)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        it.result?.get(0)?.let { it1 -> setData(it1) }
                    },{
                        loadingState.value = LoadState.Error
                    })!!
            )
        }

        binding?.hot?.setOnClickListener {
            loadingState.value = LoadState.Loading
            disposable.add(
                binding?.viewModel?.getHotPost()
                    ?.delay(500, TimeUnit.MILLISECONDS)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        it.result?.get(0)?.let { it1 -> setData(it1) }
                    },{
                        loadingState.value = LoadState.Error
                    })!!
            )
        }

        binding?.best?.setOnClickListener {
            loadingState.value = LoadState.Loading
            disposable.add(
                binding?.viewModel?.getTopPost()
                    ?.delay(500, TimeUnit.MILLISECONDS)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        it.result?.get(0)?.let { it1 -> setData(it1) }
                    },{
                        loadingState.value = LoadState.Error
                    })!!
            )
        }


        loadingState.observe(this, Observer {
            when(it) {
                is LoadState.Loading -> loading()
                is LoadState.Success -> success()
                is LoadState.Error -> error()

            }
        })
    }

    private fun error() {
        binding?.cardView?.visibility = View.GONE
        binding?.progress?.visibility = View.GONE
        binding?.errorText?.visibility = View.VISIBLE
    }

    private fun success() {
        binding?.cardView?.visibility = View.VISIBLE
        binding?.progress?.visibility = View.GONE
        binding?.errorText?.visibility = View.GONE
    }

    private fun loading() {
        binding?.cardView?.visibility = View.VISIBLE
        binding?.progress?.visibility = View.VISIBLE
        binding?.errorText?.visibility = View.GONE
    }

    private fun setData(post: PostModel) {
        if (post.description == binding?.description?.text) {
            binding?.prev?.visibility = View.GONE
            return
        }

        binding?.prev?.visibility = View.VISIBLE

        Glide.with(this)
            .asGif()
            .load(post.gifURL)
            .centerCrop()
            .into(binding?.image!!)



        binding?.description?.text = post.description
        loadingState.value = LoadState.Success
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}