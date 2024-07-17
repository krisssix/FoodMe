package cz.mendelu.xnazarja.va2.foodMe.ui.activities

sealed class MainActivityUiState {
    object Default : MainActivityUiState()
    object RunForAFirstTime : MainActivityUiState()
    object ContinueToApp : MainActivityUiState()
}