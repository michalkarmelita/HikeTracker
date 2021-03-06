package com.michalkarmelita.hiketracker.photosfeed.dagger

import com.michalkarmelita.hiketracker.photosfeed.PictureFeedService
import com.michalkarmelita.hiketracker.photosfeed.dagger.modules.LocationModule
import com.michalkarmelita.hiketracker.common.dagger.modules.NetworkModule
import dagger.Subcomponent

/**
 * Created by MK on 10/07/2017.
 */
@Subcomponent(modules = arrayOf(NetworkModule::class, LocationModule::class))
interface PictureFeedComponent {
    fun inject(pictureFeedService: PictureFeedService)
}