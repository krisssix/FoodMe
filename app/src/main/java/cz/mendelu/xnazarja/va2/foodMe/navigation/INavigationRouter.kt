package cz.mendelu.xnazarja.va2.foodMe.navigation

import android.os.Bundle
import androidx.navigation.NavController
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse

interface INavigationRouter {
    fun getNavController(): NavController
    fun returnBack()
    fun navigateToListOfPlaces()
    fun navigateToPlaceDetail(id: String)
    fun navigateToFilterScreen()
    fun navigateToTrackPlaceScreen(id: String)

}