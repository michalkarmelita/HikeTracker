package com.michalkarmelita.hiketracker.photoslist.dagger

import com.michalkarmelita.hiketracker.photoslist.PhotosActivity
import dagger.Subcomponent

/**
 * Created by MK on 10/07/2017.
 */
@Subcomponent(modules = arrayOf(PhotosListModule::class))
interface PhotosListComponent {
    fun inject(activity: PhotosActivity)
}

