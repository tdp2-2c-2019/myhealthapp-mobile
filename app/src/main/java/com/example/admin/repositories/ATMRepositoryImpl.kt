package com.example.admin.repositories

import javax.inject.Singleton

@Singleton
class ATMRepositoryImpl(private val remoteDataSource: ATMRemoteRepository) : ATMRepository {

    override fun getATMs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
