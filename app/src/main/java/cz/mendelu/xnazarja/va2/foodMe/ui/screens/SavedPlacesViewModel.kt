package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xnazarja.va2.foodMe.architecture.BaseViewModel
import cz.mendelu.xnazarja.va2.foodMe.database.IPlaceLocalRepository
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SavedPlacesViewModel(private val databaseRepository: IPlaceLocalRepository) : BaseViewModel() {

    val savedPlacesUiState: MutableState<SavedPlacesUiState<List<PlaceDetailResponse>>> = mutableStateOf(SavedPlacesUiState.Start())

    lateinit var places: List<PlaceDetailResponse>

    fun loadSavedPlaces(){
        launch {
            databaseRepository.getAll().collect {
                savedPlacesUiState.value = SavedPlacesUiState.Success(it)
                places = it
            }
        }
    }

}