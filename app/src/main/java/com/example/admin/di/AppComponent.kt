package com.example.admin.di

import com.example.admin.AdminApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, MapsActivityModule::class, NetModule::class])
interface AppComponent : AndroidInjector<AdminApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AdminApp>()
}