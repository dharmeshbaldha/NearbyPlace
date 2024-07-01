package com.searchnearbylocation.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.searchnearbylocation.data.api.NearbyPlaceRestAPI
import com.searchnearbylocation.data.api.model.NearbyPlaceModel
import com.searchnearbylocation.data.model.NearbyPlacePhotoModel
import com.searchnearbylocation.utils.Constants.TAG
import com.searchnearbylocation.utils.NetworkResult
import com.searchnearbylocation.utils.safeAPICall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NearbyPlaceRepository @Inject constructor(private val restAPI: NearbyPlaceRestAPI) {

    private val _nearbyPlaceListData = MutableLiveData<NetworkResult<NearbyPlaceModel>>()
    val nearbyPlaceListData: LiveData<NetworkResult<NearbyPlaceModel>>
        get() = _nearbyPlaceListData

    private val _nearbyPlacePhotoData = MutableLiveData<NetworkResult<NearbyPlacePhotoModel>>()
    val nearbyPlacePhotoData: LiveData<NetworkResult<NearbyPlacePhotoModel>>
        get() = _nearbyPlacePhotoData


    suspend fun getNearbyLocation(options: Map<String, String>) {
        _nearbyPlaceListData.postValue(NetworkResult.Loading())
        val locationListResponse = safeAPICall(Dispatchers.IO, restAPI.getNearbyPlaceList(options))
        _nearbyPlaceListData.postValue(locationListResponse)
        Log.d(TAG, "getNearbyLocation: ${locationListResponse.data.toString()}")
    }

    suspend fun getNearbyPlacePhoto(fsq_id: String) {
        _nearbyPlacePhotoData.postValue(NetworkResult.Loading())
        val nearbyPlacePhotoResponse = safeAPICall(Dispatchers.IO, restAPI.getNearbyPlacePhoto(fsq_id))
        _nearbyPlacePhotoData.postValue(nearbyPlacePhotoResponse)
        Log.d(TAG, "getNearbyPlacePhoto: ${nearbyPlacePhotoResponse.data.toString()}")
    }

}