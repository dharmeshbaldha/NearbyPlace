package com.searchnearbylocation.data.api.model

data class NearbyPlaceModel(
    val results: List<Result>
)

data class Result(
    val closed_bucket: String,
    val distance: Int,
    val fsq_id: String,
    val geocodes: Geocodes,
    val link: String,
    val location: Location,
    val name: String,
    val categories: List<Categories>
)

data class Categories(val icon: Icon)

data class Icon(
    val prefix: String,
    val suffix: String
)

data class Geocodes(
    val main: Main
)

data class Main(
    val latitude: Double,
    val longitude: Double
)

data class Location(
    val formatted_address: String,
    val address: String,
    val cross_street: String,
    val locality: String,
    val postcode: String,
    val region: String
)

