package com.michalkarmelita.hiketracker.picturefeed.dagger.modules

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.michalkarmelita.hiketracker.common.dagger.ForApplication
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides

/**
 * Created by MK on 10/07/2017.
 */
@Module
class LocationModule {

    @Provides
    fun provideLocationRequest() = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)

    @Provides
    fun provideRxLocation(@ForApplication context: Context) = RxLocation(context)

}