package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import cz.mendelu.xnazarja.va2.foodMe.architecture.BaseViewModel
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.DataStoreRepositoryImpl
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.IDataStoreRepository
import kotlinx.coroutines.launch

class FilterScreenViewModel(
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel() {

    val filterUiState: MutableState<FilterScreenUiStates<Boolean>> = mutableStateOf(FilterScreenUiStates.Start())

    var radius: Int = 100
    var limit: Int = 20

    fun loadPreferences(){
        launch {
            radius = dataStoreRepository.getRadius()
            limit = dataStoreRepository.getLimit()
            filterUiState.value = FilterScreenUiStates.Success(data = true)
        }
    }

    fun savePreferences(
        radius: Int,
        limit: Int
    ){
        launch {
            dataStoreRepository.setRadius(radius)
            dataStoreRepository.setLimit(limit)
        }
    }

}