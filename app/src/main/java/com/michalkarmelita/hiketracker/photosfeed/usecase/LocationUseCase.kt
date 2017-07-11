package com.michalkarmelita.hiketracker.photosfeed.usecase

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by MK on 10/07/2017.
 */
class LocationUseCase @Inject constructor(val rxLocation: RxLocation, val locationRequest: LocationRequest) {

    @SuppressLint("MissingPermission")
    fun getLocationUpdatesObservable(): Observable<Location> {
        return rxLocation
                .location()
                .updates(locationRequest)
    }

}