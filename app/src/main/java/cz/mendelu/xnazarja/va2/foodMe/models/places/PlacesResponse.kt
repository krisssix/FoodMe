package cz.mendelu.xnazarja.va2.foodMe.models.places

import java.io.Serializable

data class PlacesResponse(
    val features: List<Feature>,
    val type: String
): Serializable {

    var done: Boolean = false
    var favorte: Boolean = false

}