package com.example.developerslife.di.components

import com.example.developerslife.di.modules.AppModule
import com.example.developerslife.ui.MainActivity
import com.example.developerslife.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel)
}