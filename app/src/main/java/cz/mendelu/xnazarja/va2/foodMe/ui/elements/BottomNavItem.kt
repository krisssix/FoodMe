package cz.mendelu.xnazarja.va2.foodMe.ui.elements

import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.constants.Constants.TT_FILTER_SCREEN_BOTTOM_ITEM
import cz.mendelu.xnazarja.va2.foodMe.constants.Constants.TT_LIST_OF_PLACES_BOTTOM_ITEM
import cz.mendelu.xnazarja.va2.foodMe.constants.Constants.TT_TRACK_BOTTOM_ITEM

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var route: String,
    var testTag: String
) {
    object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        route = "list_of_places",
        testTag = TT_LIST_OF_PLACES_BOTTOM_ITEM
    )

    object Categories : BottomNavItem(
        title = "Categories",
        icon = R.drawable.ic_bullet,
        route = "filter_screen",
        testTag = TT_FILTER_SCREEN_BOTTOM_ITEM
    )

    object Map : BottomNavItem(
        title = "Nearby",
        icon = R.drawable.ic_map,
        route = "track_screen",
        testTag = TT_TRACK_BOTTOM_ITEM
    )
}