package com.michalkarmelita.hiketracker.photosfeed

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import com.michalkarmelita.hiketracker.common.App
import com.michalkarmelita.hiketracker.common.Model.PhotoData
import com.michalkarmelita.hiketracker.photosfeed.dagger.modules.LocationModule
import com.michalkarmelita.hiketracker.photosfeed.usecase.GetPhotoDataUseCase
import com.michalkarmelita.hiketracker.photosfeed.usecase.LocationUseCase
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
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
    private var resultsSubject: ReplaySubject<PhotoData> = ReplaySubject.create()
    private val runningSubject = PublishSubject.create<Boolean>()

    @Inject
    lateinit var locationUseCase: LocationUseCase
    @Inject
    lateinit var getPictureUseCase: GetPhotoDataUseCase

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
        resultsSubject.onComplete()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    /** method for clients  */
    fun startTracking() {
        runningSubject.onNext(true)
        locationUseCase
                .getLocationUpdatesObservable()
                .compose(active())
                .switchMap { location: Location ->
                    getPictureUseCase.getPictures(location)
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsSubject)
    }

    fun getResultsObservable() = resultsSubject

    fun stopTracking() {
        runningSubject.onNext(false)
    }

    fun <R> active(): ObservableTransformer<R, R> {
        return ObservableTransformer { upstream ->
            upstream.takeUntil(runningSubject.filter { v -> !v })
        }
    }
}
