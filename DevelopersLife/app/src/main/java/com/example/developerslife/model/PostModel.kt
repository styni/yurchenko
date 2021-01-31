package com.example.developerslife.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("gifURL")
    val gifURL: String? = null,

)
