package cz.mendelu.xnazarja.va2.foodMe.ui.screens


sealed class SavedPlacesUiState<out T> {
    class Start() : SavedPlacesUiState<Nothing>()
    class Success<T>(var data: T) : SavedPlacesUiState<T>()
    class Error(var error: Int) : SavedPlacesUiState<Nothing>()
}