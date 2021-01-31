package com.example.developerslife.model

import com.google.gson.annotations.SerializedName

data class PostList(
    @SerializedName("result")
    val result: List<PostModel>? = null,
)