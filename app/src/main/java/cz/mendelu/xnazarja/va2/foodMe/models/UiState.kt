package cz.mendelu.xnazarja.va2.foodMe.models

import java.io.Serializable

sealed class UiState<out T> : Serializable {
    class Loading : UiState<Nothing>()
    class DataLoaded<T>(var data: T) : UiState<T>()
    class Error(var error: Int) : UiState<Nothing>()
}