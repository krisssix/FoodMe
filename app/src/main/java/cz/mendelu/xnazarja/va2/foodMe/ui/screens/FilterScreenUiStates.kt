package cz.mendelu.xnazarja.va2.foodMe.ui.screens

sealed class FilterScreenUiStates<out T> {
    class Start() : FilterScreenUiStates<Nothing>()
    class Success<T>(var data: T) : FilterScreenUiStates<T>()
}