package cz.mendelu.xnazarja.va2.foodMe.map

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.places.Feature

class CustomMapRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Feature>
) : DefaultClusterRenderer<Feature>(context, map, clusterManager) {

    private val icons: MutableMap<Long, Bitmap> = mutableMapOf()

    override fun onBeforeClusterItemRendered(item: Feature, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.title(item.title)
            .icon(
                BitmapDescriptorFactory.fromBitmap(getIcon(item))
            )
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Feature>): Boolean {
        return cluster.size > 5
    }

    private fun getIcon(place: Feature): Bitmap {
        if (!icons.containsKey(place.id.toLong())) {
            icons.put(place.id.toLong(), MarkerUtil.createBitmapBasedOnType(place, context))
        }
        return icons[place.id.toLong()]!!
    }

}