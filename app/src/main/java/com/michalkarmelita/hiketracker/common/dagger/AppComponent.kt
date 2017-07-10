package com.michalkarmelita.hiketracker.common.dagger

import com.michalkarmelita.hiketracker.common.App
import com.michalkarmelita.hiketracker.common.dagger.modules.AppModule
import com.michalkarmelita.hiketracker.picturefeed.dagger.PictureFeedComponent
import com.michalkarmelita.hiketracker.picturefeed.dagger.modules.LocationModule
import com.michalkarmelita.hiketracker.common.dagger.modules.NetworkModule
import dagger.Component

/**
 * Created by MK on 10/07/2017.
 */
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(application: App)

    fun plus(locationModule: LocationModule): PictureFeedComponent
}
