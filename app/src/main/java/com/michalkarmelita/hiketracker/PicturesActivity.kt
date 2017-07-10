package com.michalkarmelita.hiketracker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.michalkarmelita.hiketracker.picturefeed.PictureFeedService
import com.michalkarmelita.hiketracker.picturefeed.PictureFeedService.LocalBinder


class PicturesActivity : AppCompatActivity() {

    val NOTIFICATION_ID = 12345
    lateinit var serviceIntent: Intent
    lateinit var service: PictureFeedService
    var bound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serviceIntent = Intent(this, PictureFeedService::class.java)
        startService(serviceIntent)
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
        super.onDestroy()
        stopService(serviceIntent)
        cancelNotification(NOTIFICATION_ID)
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
            this@PicturesActivity.service = binder.service
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }
}
