package cz.mendelu.xnazarja.va2.foodMe.ui.screens

sealed class TrackPlaceUiState<out T> {
    class Start() : TrackPlaceUiState<Nothing>()
    class Success<T>(var data: T) : TrackPlaceUiState<T>()
    class Error(var error: Int) : TrackPlaceUiState<Nothing>()
}