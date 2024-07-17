package cz.mendelu.xnazarja.va2.foodMe.models.places

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
) : Serializable, ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(geometry.coordinates[1], geometry.coordinates[0])
    }

    override fun getTitle(): String {
        return properties.name
    }

    override fun getSnippet(): String {
        return properties.name
    }
}