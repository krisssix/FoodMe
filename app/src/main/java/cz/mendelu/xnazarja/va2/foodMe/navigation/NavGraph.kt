package cz.mendelu.xnazarja.va2.foodMe.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Constraints
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.mendelu.xnazarja.va2.foodMe.constants.Constants
import cz.mendelu.xnazarja.va2.foodMe.ui.screens.*


@ExperimentalFoundationApi
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember { NavigationRouterImpl(navController) },
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Destination.ListOfPlacesScreen.route) {
            ListOfPlacesScreen(navigation = navigation)
        }

        composable(Destination.PlaceDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument(Constants.ID) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val id = it.arguments?.getString(Constants.ID)
            PlaceDetailScreen(navigation = navigation, id = id!!)
        }
        composable(Destination.FilterScreen.route){
            FilterScreen(navigation = navigation)
        }

        composable(Destination.TrackPlaceScreen.route + "/{id}",
        arguments = listOf(
            navArgument(Constants.ID) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
        ) {
            val id = it.arguments?.getString(Constants.ID)
            TrackPlaceScreen(navigation = navigation, id = id!!)
        }
    }

}