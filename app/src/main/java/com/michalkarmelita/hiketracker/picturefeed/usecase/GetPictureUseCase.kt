package com.michalkarmelita.hiketracker.picturefeed.usecase

import android.location.Location
import com.michalkarmelita.hiketracker.picturefeed.model.ApiService
import com.michalkarmelita.hiketracker.picturefeed.model.apimodel.ProtosResponse
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by MK on 10/07/2017.
 */
class GetPictureUseCase @Inject constructor(val apiService: ApiService) {
    fun getPictures(location: Location): Observable<String> {
        return apiService.getPhotoForLocation(location.latitude, location.longitude)
                .map { response: ProtosResponse -> response.photos.photo.get(0).id }
    }

}

