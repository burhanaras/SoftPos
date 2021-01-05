package com.payz.softpos.presentation

import android.app.Application
import com.facebook.stetho.Stetho
import com.payz.externalconnection.api.ExternalPaymentService
import com.payz.softpos.BuildConfig
import com.payz.softpos.data.local.database.SoftPosDB
import com.payz.softpos.data.local.datasource.LocalDataSource
import com.payz.softpos.data.repository.Repository

class SoftPosApp : Application() {

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        ExternalPaymentService.init(this)
        
        initStetho()

        initRoomDB()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initRoomDB() {
        val dao = SoftPosDB.getInstance(this).dao()
        val localDataSource = LocalDataSource(dao)
        repository = Repository(localDataSource)
    }
}