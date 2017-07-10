package com.michalkarmelita.hiketracker.picturefeed

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class PictureFeedService : Service() {

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        internal // Return this instance of LocalService so clients can call public methods
        val service: PictureFeedService
            get() = this@PictureFeedService
    }

    // Binder given to clients
    private val mBinder = LocalBinder()

    lateinit var rxLocation: RxLocation

    val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)

    lateinit var disposable: Disposable

    override fun onCreate() {
        super.onCreate()
        rxLocation = RxLocation(this);

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    /** method for clients  */
    fun startTracking() {
        disposable = rxLocation.location().updates(locationRequest)
                .flatMap<Any> { location -> rxLocation.geocoding().fromLocation(location).toObservable() }
                .subscribe { address ->
                    Log.d("LOC", address.toString())
                }
    }

    fun stopTracking() {
        disposable.dispose()
    }
}
