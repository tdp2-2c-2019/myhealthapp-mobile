package com.example.admin.di

import com.example.admin.repositories.atm.ATMRepository
import com.example.admin.repositories.login.LoginRepository
import com.example.admin.screens.login.LoginActivity
import com.example.admin.screens.login.LoginViewModelFactory
import com.example.admin.screens.map.MapsActivity
import com.example.admin.screens.map.MapViewModelFactory
import com.example.admin.screens.sign_in.SignInActivity
import com.example.admin.screens.sign_in.SignInViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivitiesModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesMapViewModelFactory(ATMRepository: ATMRepository) : MapViewModelFactory {
            return MapViewModelFactory(ATMRepository)
        }

        @JvmStatic
        @Provides
        internal fun providesLoginViewModelFactory(loginRepository: LoginRepository) : LoginViewModelFactory {
            return LoginViewModelFactory(loginRepository)
        }

        @JvmStatic
        @Provides
        internal fun providesSignInViewModelFactory(loginRepository: LoginRepository) : SignInViewModelFactory {
            return SignInViewModelFactory(loginRepository)
        }
    }

    @ContributesAndroidInjector
    internal abstract fun mapsActivity(): MapsActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun signInActivity(): SignInActivity

}