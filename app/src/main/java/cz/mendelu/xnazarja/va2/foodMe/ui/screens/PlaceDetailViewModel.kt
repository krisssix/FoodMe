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
import kotlinx.coroutines.*

class PlaceDetailViewModel(private val travelRepository: TravelRemoteRepositoryImpl, private val databaseRepository: IPlaceLocalRepository) : BaseViewModel() {

    val placeDetailUiState: MutableState<PlaceDetailUiState<PlaceDetailResponse>>
            = mutableStateOf(PlaceDetailUiState.Start())
    var placeId: String? = null
    var isDeleteVisible = mutableStateOf(false)
    var checkedState = mutableStateOf(false)
    lateinit var place: PlaceDetailResponse

    fun loadPlaceFromDatabase(){
        launch {
            databaseRepository.findById(placeId!!)?.let {
                place = it
                placeDetailUiState.value =
                    PlaceDetailUiState.Success(it)
                if (place.favorte) {
                    checkedState.value = true
                }
                if(place.done) {
                    isDeleteVisible.value = true
                }
            }
            if (!::place.isInitialized) {
                loadPlaceFromAPI()
            }
        }
    }

    fun loadPlaceFromAPI(){
        if(placeId != null) {
            launch {
                if (!::place.isInitialized) {

                    val result = withContext(Dispatchers.IO) {
                        travelRepository.place(placeId!!, api = Constants.API_KEY)
                    }

                    when (result) {
                        is CommunicationResult.Error -> placeDetailUiState.value =
                            PlaceDetailUiState.Error(R.string.detail_failed)
                        is CommunicationResult.Exception -> placeDetailUiState.value =
                            PlaceDetailUiState.Error(R.string.no_internet_connection)
                        is CommunicationResult.Success -> {
                            placeDetailUiState.value =
                                PlaceDetailUiState.Success(result.data)
                            place = PlaceDetailUiState.Success(result.data).data
                        }
                    }
                }
            }
        } else {
            placeDetailUiState.value = PlaceDetailUiState.Error(R.string.detail_failed)
        }
    }

    fun starAction(){
        if (checkedState.value) {
            place.favorte = true
            savePlace()
        } else {
            if(place.done){
                place.favorte = false
                savePlace()
            } else {
                deletePlace()
            }
        }
    }

    fun savePlace(){
        launch {
            databaseRepository.insertPlace(place)
        }
    }

    fun deletePlace(){
        launch {
            databaseRepository.deletePlace(place)
        }
    }


}