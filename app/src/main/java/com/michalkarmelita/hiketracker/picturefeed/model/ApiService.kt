package com.michalkarmelita.hiketracker.picturefeed.model

import com.michalkarmelita.hiketracker.picturefeed.model.apimodel.ProtosResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by MK on 10/07/2017.
 */
interface ApiService {

    @GET("services/rest/")
    fun getPhotoForLocation(@Query("lat")lat: Double, @Query("lon")lon: Double): Observable<ProtosResponse>
}
