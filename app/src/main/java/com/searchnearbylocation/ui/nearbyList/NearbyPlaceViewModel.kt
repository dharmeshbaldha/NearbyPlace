package com.searchnearbylocation.ui.nearbyList

import androidx.lifecycle.ViewModel
import com.searchnearbylocation.data.repository.NearbyPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NearbyPlaceViewModel @Inject constructor(private val repository: NearbyPlaceRepository) : ViewModel() {

    val locationListLiveData
        get() = repository.nearbyPlaceListData

    val nearbyPlacePhotoData
        get() = repository.nearbyPlacePhotoData

    suspend fun getNearbyLocation(options: Map<String, String>) {
        repository.getNearbyLocation(options)
    }

    suspend fun getNearbyPlacePhoto(fsq_id: String) {
        repository.getNearbyPlacePhoto(fsq_id)
    }

}