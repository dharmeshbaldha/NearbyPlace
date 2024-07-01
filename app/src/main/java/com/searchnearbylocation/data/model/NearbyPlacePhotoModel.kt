package com.searchnearbylocation.data.model

class NearbyPlacePhotoModel : ArrayList<NearbyPlacePhotoModelItem>()

data class NearbyPlacePhotoModelItem(
    val prefix: String,
    val suffix: String
)