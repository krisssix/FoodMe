package cz.mendelu.xnazarja.va2.foodMe.models.placeDetail

import androidx.room.Entity

@Entity
data class Bbox(
    val lat_max: Double,
    val lat_min: Double,
    val lon_max: Double,
    val lon_min: Double
)