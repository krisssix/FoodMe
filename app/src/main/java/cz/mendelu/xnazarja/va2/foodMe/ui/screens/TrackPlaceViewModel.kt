package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.architecture.BaseViewModel
import cz.mendelu.xnazarja.va2.foodMe.architecture.CommunicationResult
import cz.mendelu.xnazarja.va2.foodMe.communication.TravelRemoteRepositoryImpl
import cz.mendelu.xnazarja.va2.foodMe.constants.Constants
import cz.mendelu.xnazarja.va2.foodMe.database.IPlaceLocalRepository
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackPlaceViewModel(private val travelRepository: TravelRemoteRepositoryImpl, private val databaseRepository: IPlaceLocalRepository):BaseViewModel() {

    val trackPlaceUiState: MutableState<PlaceDetailUiState<PlaceDetailResponse>>
            = mutableStateOf(PlaceDetailUiState.Start())

    var placeId: String? = null
    lateinit var place: PlaceDetailResponse

    fun loadPlace(){
        if(placeId != null) {
            launch {

                if (!::place.isInitialized) {

                    val result = withContext(Dispatchers.IO) {
                        travelRepository.place(placeId!!, api = Constants.API_KEY)
                    }

                    when (result) {
                        is CommunicationResult.Error -> trackPlaceUiState.value =
                            PlaceDetailUiState.Error(R.string.detail_failed)
                        is CommunicationResult.Exception -> trackPlaceUiState.value =
                            PlaceDetailUiState.Error(R.string.no_internet_connection)
                        is CommunicationResult.Success -> {
                            trackPlaceUiState.value =
                                PlaceDetailUiState.Success(result.data)
                            place = PlaceDetailUiState.Success(result.data).data
                            println("PLACE INITIALIZED")
                        }
                    }
                }
            }
        } else {
            trackPlaceUiState.value = PlaceDetailUiState.Error(R.string.detail_failed)
        }
    }


    fun savePlace(){
        place.done = true
        launch {
            databaseRepository.insertPlace(place)
        }
    }

}