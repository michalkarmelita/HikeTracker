package com.michalkarmelita.hiketracker.photoslist.presenter

import com.michalkarmelita.hiketracker.common.Model.PhotoData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


/**
 * Created by MK on 10/07/2017.
 */
class PhotosPresenter @Inject constructor() {

    lateinit var disposable: Disposable

    lateinit var view: PhotosListView

    fun attachView(view: PhotosListView) {
        this.view = view
    }

    fun serviceBound() {
        disposable = view.photosDataObservable()
                .map { photoData: PhotoData -> createPhotoUrl(photoData) }
                .subscribe(view::displayPhoto)
    }

    fun detachView() {
        disposable.dispose()
    }

    fun createPhotoUrl(photoData: PhotoData): String {
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s_c.jpg", photoData.farmId, photoData.serverId, photoData.photoId, photoData.secret)
    }

}

interface PhotosListView {

    fun photosDataObservable(): Observable<PhotoData>

    fun displayPhoto(url: String)

}