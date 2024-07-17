package cz.mendelu.xnazarja.va2.foodMe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import cz.mendelu.xnazarja.va2.foodMe.navigation.Destination
import cz.mendelu.xnazarja.va2.foodMe.navigation.NavGraph
import cz.mendelu.xnazarja.va2.foodMe.navigation.NavigationRouterImpl
import cz.mendelu.xnazarja.va2.foodMe.ui.screens.ListOfPlacesViewModel
import cz.mendelu.xnazarja.va2.foodMe.ui.theme.TravelAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private val placesViewModel: ListOfPlacesViewModel by viewModel()


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            viewModel.mainActivityScreenState.collect { value ->
                when (value) {
                    is MainActivityUiState.Default -> {
                        viewModel.checkAppState()
                    }
                    is MainActivityUiState.ContinueToApp -> {
                        setContent {
                            TravelAppTheme {
                                val navController = rememberNavController()
                                Scaffold(
                                    modifier = Modifier.fillMaxSize(),

                                ) {
                                    Box(modifier = Modifier.padding(it)) {
                                        NavGraph(
                                            startDestination = Destination.ListOfPlacesScreen.route,
                                            navController = navController
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is MainActivityUiState.RunForAFirstTime -> {
                        viewModel.setToContinue()

                        val intent = Intent(this@MainActivity, AppIntroActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
