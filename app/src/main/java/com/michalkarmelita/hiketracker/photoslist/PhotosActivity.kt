package com.michalkarmelita.hiketracker.photoslist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.michalkarmelita.hiketracker.R
import com.michalkarmelita.hiketracker.cancelNotification
import com.michalkarmelita.hiketracker.common.App
import com.michalkarmelita.hiketracker.common.Model.PhotoData
import com.michalkarmelita.hiketracker.photosfeed.PictureFeedService
import com.michalkarmelita.hiketracker.photosfeed.PictureFeedService.LocalBinder
import com.michalkarmelita.hiketracker.photoslist.adapter.PhotosAdapter
import com.michalkarmelita.hiketracker.photoslist.presenter.PhotosListView
import com.michalkarmelita.hiketracker.photoslist.presenter.PhotosPresenter
import com.michalkarmelita.hiketracker.showNotification
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_photos.*
import javax.inject.Inject


class PhotosActivity : AppCompatActivity(), PhotosListView {

    val NOTIFICATION_ID = 12345
    lateinit var serviceIntent: Intent
    lateinit var service: PictureFeedService
    var bound = false

    @Inject
    lateinit var presenter: PhotosPresenter
    @Inject
    lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        injectComponent()
        presenter.attachView(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        serviceIntent = Intent(this, PictureFeedService::class.java)
        startService(serviceIntent)
    }

    fun injectComponent() {
        App.component
                .plus()
                .inject(this)
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE)
    }


    override fun onPostResume() {
        super.onPostResume()
        cancelNotification(NOTIFICATION_ID)
    }

    override fun onPause() {
        super.onPause()
        showNotification(NOTIFICATION_ID)
    }

    override fun onStop() {
        super.onStop()
        // Unbind from the service
        if (bound) {
            unbindService(mConnection)
            bound = false
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        stopService(serviceIntent)
        cancelNotification(NOTIFICATION_ID)
        super.onDestroy()
    }

    override fun photosDataObservable(): Observable<PhotoData> {
        return service.getResultsObservable()
    }

    override fun displayPhoto(url: String) {
        adapter.addItem(url)
    }

    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute)  */
    fun onButtonClick(v: View) {
        if (!v.isSelected) {
            service.startTracking()
            v.isSelected = true
        } else {
            service.stopTracking()
            v.isSelected = false
        }
    }

    /** Defines callbacks for service binding, passed to bindService()  */
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalBinder
            this@PhotosActivity.service = binder.service
            bound = true
            presenter.serviceBound()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }
}
