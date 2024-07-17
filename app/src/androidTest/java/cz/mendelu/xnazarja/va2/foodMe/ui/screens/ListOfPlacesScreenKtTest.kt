package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.Navigation
import cz.mendelu.xnazarja.va2.foodMe.FakeNavigation
import org.junit.Rule
import org.junit.Test

class ListOfPlacesScreenKtTest{

    @get:Rule
    val rule = createComposeRule()

    val tabSaved = hasText("Favorites") and hasClickAction()
    val tabList = hasText("List") and hasClickAction()
    val tabMap = hasText("Nearby") and hasClickAction()

    @Test
    fun tabsTest() {
        rule.setContent { ListOfPlacesScreen(navigation = FakeNavigation()) }

        rule.onNode(tabSaved).performClick()
        rule.onNode(tabList).performClick()
        rule.onNode(tabMap).performClick()
    }


}