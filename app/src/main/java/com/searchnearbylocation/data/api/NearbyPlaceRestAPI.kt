package com.searchnearbylocation.data.api

import com.searchnearbylocation.data.api.model.NearbyPlaceModel
import com.searchnearbylocation.data.model.NearbyPlacePhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface NearbyPlaceRestAPI {

    @GET("/v3/places/search")
    suspend fun getNearbyPlaceList(@QueryMap options: Map<String, String>)
            : Response<NearbyPlaceModel>

    @GET("/v3/places/{fsq_id}/photos")
    suspend fun getNearbyPlacePhoto(@Path("fsq_id") fsq_id: String)
            : Response<NearbyPlacePhotoModel>
}