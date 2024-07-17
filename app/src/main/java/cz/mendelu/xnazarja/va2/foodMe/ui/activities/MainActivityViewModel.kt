package cz.mendelu.xnazarja.va2.foodMe.ui.activities

import cz.mendelu.xnazarja.va2.foodMe.architecture.BaseViewModel
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.IDataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val dataStoreRepository: IDataStoreRepository) : BaseViewModel() {

    private val _mainActivityScreenState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Default)
    val mainActivityScreenState: StateFlow<MainActivityUiState> = _mainActivityScreenState

    fun checkAppState(){
        launch {
            if (dataStoreRepository.getFirstRun()){
                _mainActivityScreenState.value = MainActivityUiState.RunForAFirstTime
            } else {
                _mainActivityScreenState.value = MainActivityUiState.ContinueToApp
            }

        }
    }

    fun setToContinue(){
        _mainActivityScreenState.value = MainActivityUiState.ContinueToApp
    }

}