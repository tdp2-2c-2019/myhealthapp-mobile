package com.example.admin

import com.example.admin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AdminApp : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}