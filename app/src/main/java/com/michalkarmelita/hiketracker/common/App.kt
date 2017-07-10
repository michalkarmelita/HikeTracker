package com.michalkarmelita.hiketracker.common

import android.app.Application
import com.michalkarmelita.hiketracker.common.dagger.AppComponent
import com.michalkarmelita.hiketracker.common.dagger.DaggerAppComponent
import com.michalkarmelita.hiketracker.common.dagger.modules.AppModule
import com.michalkarmelita.hiketracker.common.dagger.modules.NetworkModule

/**
 * Created by MK on 10/07/2017.
 */
class App: Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .build()
        component.inject(this)
    }
}