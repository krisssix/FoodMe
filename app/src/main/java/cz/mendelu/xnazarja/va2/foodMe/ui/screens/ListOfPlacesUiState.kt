package cz.mendelu.xnazarja.va2.foodMe.ui.screens

sealed class ListOfPlacesUiState<out T> {
    class Start : ListOfPlacesUiState<Nothing>()
    class Success<T>(var data: T) : ListOfPlacesUiState<T>()
    class Error(var error: Int) : ListOfPlacesUiState<Nothing>()

}