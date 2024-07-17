package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.TravelAppApplication.Companion.appContext
import cz.mendelu.xnazarja.va2.foodMe.architecture.BaseViewModel
import cz.mendelu.xnazarja.va2.foodMe.architecture.CommunicationResult
import cz.mendelu.xnazarja.va2.foodMe.communication.TravelRemoteRepositoryImpl
import cz.mendelu.xnazarja.va2.foodMe.constants.Constants
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.IDataStoreRepository
import cz.mendelu.xnazarja.va2.foodMe.map.DefaultLocationClient
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListOfPlacesViewModel(
    private var travelRepository: TravelRemoteRepositoryImpl,
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel() {

    var selectedCategory = mutableStateOf<String?>(null)
    var searchQuery = mutableStateOf("")

    val dataStore = dataStoreRepository
    val placesUiState: MutableState<ListOfPlacesUiState<PlacesResponse>>
            = mutableStateOf(ListOfPlacesUiState.Start())

    // TODO: Update to current location
    var longitude: Double = 16.606836
    var latitude: Double = 49.195061

    fun loadPlaces() {
        launch {
            val categoriesToUse = selectedCategory.value ?: dataStoreRepository.getCategories()
            val result = withContext(Dispatchers.IO) {
                travelRepository.places(
                    radius = dataStoreRepository.getRadius(),
                    latitude = latitude,
                    longitude = longitude,
                    categories = categoriesToUse,
                    limit = dataStoreRepository.getLimit(),
                    rate = "3h",
                    api = Constants.API_KEY
                )
            }

            when (result) {
                is CommunicationResult.Error -> placesUiState.value =
                    ListOfPlacesUiState.Error(R.string.list_failed)
                is CommunicationResult.Exception -> placesUiState.value =
                    ListOfPlacesUiState.Error(R.string.no_internet_connection)
                is CommunicationResult.Success -> {
                    val filteredFeatures = if (searchQuery.value.isNotEmpty()) {
                        result.data.features.filter {
                            it.properties.name.contains(searchQuery.value, ignoreCase = true)
                        }
                    } else {
                        result.data.features
                    }

                    placesUiState.value = ListOfPlacesUiState.Success(
                        PlacesResponse(features = filteredFeatures, type = result.data.type)
                    )
                }
            }
        }
    }
}