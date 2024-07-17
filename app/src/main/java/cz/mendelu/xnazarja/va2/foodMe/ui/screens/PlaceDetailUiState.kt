package cz.mendelu.xnazarja.va2.foodMe.ui.screens


sealed class PlaceDetailUiState<out T> {
    class Start : PlaceDetailUiState<Nothing>()
    class Success<T>(var data: T) : PlaceDetailUiState<T>()
    class Error(var error: Int) : PlaceDetailUiState<Nothing>()
}