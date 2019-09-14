package com.example.admin.di

import com.example.admin.MyHealthApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class, NetModule::class])
interface AppComponent : AndroidInjector<MyHealthApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyHealthApp>()
}