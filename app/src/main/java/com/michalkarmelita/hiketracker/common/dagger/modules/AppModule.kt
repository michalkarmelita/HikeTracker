package com.michalkarmelita.hiketracker.common.dagger.modules

import com.michalkarmelita.hiketracker.common.App
import com.michalkarmelita.hiketracker.common.dagger.ForApplication
import com.scurab.android.absample.dagger.PerApp
import dagger.Module
import dagger.Provides

/**
 * Created by MK on 10/07/2017.
 */
@Module
class AppModule(var app: App) {

    @Provides
    @PerApp
    @ForApplication
    fun provideApplicationContext() = app.applicationContext

}
