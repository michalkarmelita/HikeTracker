package com.michalkarmelita.hiketracker.common.dagger

import com.michalkarmelita.hiketracker.common.App
import com.michalkarmelita.hiketracker.common.dagger.modules.AppModule
import com.michalkarmelita.hiketracker.photosfeed.dagger.PictureFeedComponent
import com.michalkarmelita.hiketracker.photosfeed.dagger.modules.LocationModule
import com.michalkarmelita.hiketracker.common.dagger.modules.NetworkModule
import com.michalkarmelita.hiketracker.photoslist.dagger.PhotosListComponent
import dagger.Component

/**
 * Created by MK on 10/07/2017.
 */
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(application: App)

    fun plus(): PhotosListComponent

    fun plus(locationModule: LocationModule): PictureFeedComponent
}
