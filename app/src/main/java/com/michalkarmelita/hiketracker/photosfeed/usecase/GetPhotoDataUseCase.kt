package com.michalkarmelita.hiketracker.photosfeed.usecase

import android.location.Location
import com.michalkarmelita.hiketracker.common.Model.PhotoData
import com.michalkarmelita.hiketracker.photosfeed.model.ApiService
import com.michalkarmelita.hiketracker.photosfeed.model.apimodel.ProtosResponse
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by MK on 10/07/2017.
 */
class GetPhotoDataUseCase @Inject constructor(val apiService: ApiService) {
    fun getPictures(location: Location): Observable<PhotoData> {
        return apiService.getPhotoForLocation(location.latitude, location.longitude)
                .map { response: ProtosResponse ->
                    val photo = response.photos.photo.get(0)
                    PhotoData(photo.id, photo.farm, photo.server, photo.secret)
                }
    }

}

