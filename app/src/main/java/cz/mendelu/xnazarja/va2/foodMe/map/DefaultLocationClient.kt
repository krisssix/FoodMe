package cz.mendelu.xnazarja.va2.foodMe.map

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.*
import cz.mendelu.xnazarja.va2.foodMe.TravelAppApplication.Companion.appContext
import cz.mendelu.xnazarja.va2.foodMe.hasLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {

    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            if(!context.hasLocationPermission()){
                throw LocationClient.LocationException("Missing location permission")
            }

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if(!isGpsEnabled && ! isNetworkEnabled){
                throw LocationClient.LocationException("GPS is disabled")
            }

            val request = LocationRequest.create()
                .setInterval(interval)
                .setFastestInterval(interval)

            val locationCallback = object : LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }

        }
    }

    override suspend fun getLastLocation(): Location {
            if(!context.hasLocationPermission()){
                throw LocationClient.LocationException("Missing location permission")
            }

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if(!isGpsEnabled && ! isNetworkEnabled){
                throw LocationClient.LocationException("GPS is disabled")
            }



            val result = client.lastLocation

            return result.result

        }

    override fun getCurrentLocation(callback: (Location) -> Unit) {
        val locationClient = LocationServices.getFusedLocationProviderClient(appContext)
        locationClient.lastLocation.addOnSuccessListener { location ->
            callback(location)
        }
    }



}

