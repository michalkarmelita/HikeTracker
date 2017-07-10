package com.michalkarmelita.hiketracker.picturefeed

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import com.michalkarmelita.hiketracker.common.App
import com.michalkarmelita.hiketracker.picturefeed.dagger.modules.LocationModule
import com.michalkarmelita.hiketracker.picturefeed.usecase.GetPictureUseCase
import com.michalkarmelita.hiketracker.picturefeed.usecase.LocationUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import javax.inject.Inject


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

    @Inject
    lateinit var locationUseCase: LocationUseCase
    @Inject
    lateinit var getPictureUseCase: GetPictureUseCase

    lateinit var disposable: Disposable

    override fun onCreate() {
        super.onCreate()
        injectComponent()
    }

    private fun injectComponent() {
        App.component
                .plus(LocationModule())
                .inject(this)
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

    private lateinit var resultsSubject: ReplaySubject<String>

    /** method for clients  */
    fun startTracking() {
        resultsSubject = ReplaySubject.create()
        locationUseCase
                .getLocationUpdatesObservable()
                .switchMap { location: Location ->
                    getPictureUseCase.getPictures(location)
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsSubject)
    }

    fun getResultsObservable() = resultsSubject

    fun stopTracking() {
        resultsSubject.onComplete();
        disposable.dispose()
    }
}
