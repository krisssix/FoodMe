package cz.mendelu.xnazarja.va2.foodMe

import androidx.navigation.NavController
import cz.mendelu.xnazarja.va2.foodMe.TravelAppApplication.Companion.appContext
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter

class FakeNavigation: INavigationRouter {
    override fun getNavController(): NavController {
        return NavController(appContext)
    }

    override fun returnBack() {

    }

    override fun navigateToListOfPlaces() {

    }

    override fun navigateToPlaceDetail(id: String) {

    }

    override fun navigateToFilterScreen() {

    }

    override fun navigateToTrackPlaceScreen(id: String) {

    }
}