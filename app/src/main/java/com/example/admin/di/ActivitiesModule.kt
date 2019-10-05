package com.example.admin.di

import com.example.admin.repositories.atm.ATMRepository
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.repositories.login.LoginRepository
import com.example.admin.screens.forgot_password.*
import com.example.admin.screens.health_services.HealthServicesActivity
import com.example.admin.screens.health_services.HealthServicesViewModelFactory
import com.example.admin.screens.home.HomeActivity
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
        internal fun providesForgotPasswordViewModelFactory(loginRepository: LoginRepository) : ForgotPasswordViewModelFactory {
            return ForgotPasswordViewModelFactory(loginRepository)
        }

        @JvmStatic
        @Provides
        internal fun providesEmailViewModelFactory(loginRepository: LoginRepository) : EmailViewModelFactory {
            return EmailViewModelFactory(loginRepository)
        }

        @JvmStatic
        @Provides
        internal fun providesSignInViewModelFactory(loginRepository: LoginRepository) : SignInViewModelFactory {
            return SignInViewModelFactory(loginRepository)
        }

        @JvmStatic
        @Provides
        internal fun providesHealthServicesViewModelFactory(healthServicesRepository: HealthServicesRepository) : HealthServicesViewModelFactory {
            return HealthServicesViewModelFactory(healthServicesRepository)
        }
    }

    @ContributesAndroidInjector
    internal abstract fun mapsActivity(): MapsActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun signInActivity(): SignInActivity

    @ContributesAndroidInjector
    internal abstract fun healthServicesActivity(): HealthServicesActivity

    @ContributesAndroidInjector
    internal abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun forgotPasswordActivity(): ForgotPasswordActivity

    @ContributesAndroidInjector
    internal abstract fun emailActivity(): EmailActivity

}