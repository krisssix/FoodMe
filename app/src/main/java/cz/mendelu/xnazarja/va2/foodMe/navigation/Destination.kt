package cz.mendelu.xnazarja.va2.foodMe.navigation

sealed class Destination(
    val route: String
){
    object ListOfPlacesScreen : Destination(route = "list_of_places")
    object PlaceDetailScreen: Destination(route = "place_detail")
    object FilterScreen : Destination(route = "filter_screen")
    object TrackPlaceScreen : Destination(route = "track_screen")

}