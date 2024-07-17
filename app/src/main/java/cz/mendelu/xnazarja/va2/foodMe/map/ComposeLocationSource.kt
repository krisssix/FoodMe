package cz.mendelu.xnazarja.va2.foodMe.map

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.LocationSource
import cz.mendelu.xnazarja.va2.foodMe.hasLocationPermission

class ComposeLocationSource(private val context: Context) : LocationSource {

    private val client = LocationServices.getFusedLocationProviderClient(context)
    private var listener: LocationSource.OnLocationChangedListener? = null

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        this.listener = listener
        if (!context.hasLocationPermission()) {
            return
        }
        client.lastLocation.addOnSuccessListener { location ->
            listener.onLocationChanged(location)
        }
    }


    override fun deactivate() {
        listener = null
    }
}