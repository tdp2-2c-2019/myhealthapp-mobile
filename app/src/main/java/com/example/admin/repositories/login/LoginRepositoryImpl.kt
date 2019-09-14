package com.example.admin.repositories.login

import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl(private val remoteDataSource: LoginRemoteRepository) : LoginRepository {

}
