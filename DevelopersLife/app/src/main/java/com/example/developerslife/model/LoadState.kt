package com.example.developerslife.model

sealed class LoadState{
    object Loading : LoadState()
    object Error : LoadState()
    object Success : LoadState()
}
