package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.compose.*
import cz.mendelu.xnazarja.va2.foodMe.map.CustomMapRenderer
import cz.mendelu.xnazarja.va2.foodMe.models.UiState
import cz.mendelu.xnazarja.va2.foodMe.models.places.Feature
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.ErrorScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.LoadingScreen

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapOfPlacesScreen(
    navigation: INavigationRouter,
    viewModel: ListOfPlacesViewModel = getViewModel()
) {

    val screenState: MutableState<ScreenState<PlacesResponse>> = rememberSaveable {
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.placesUiState.value.let {
        when(it){
            is ListOfPlacesUiState.Error -> {
                screenState.value = ScreenState.Error(it.error)
            }
            is ListOfPlacesUiState.Start -> {
                screenState.value = ScreenState.Loading()
                LaunchedEffect(it) {
                    viewModel.loadPlaces()
                }
            }
            is ListOfPlacesUiState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 0.dp)
                            .weight(1.5f)
                    )
                }
            },
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colorScheme.background
        )
    }, content = {
        MapScreenContent(
            paddingValues = it,
            navigation = navigation,
            screenState = screenState.value
        )
    },)

}*/


@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: ListOfPlacesViewModel,
    uiState: UiState<PlacesResponse>
){

    val disableReclustering = remember { mutableStateOf(false) }

    navigation.getNavController().addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            if (!destination.displayName.equals(controller.currentDestination?.displayName.toString())) {
                disableReclustering.value = true
            }
        }
    })

    uiState.let {
        when(it){
            is UiState.DataLoaded -> Map(
                paddingValues = paddingValues,
                navigation = navigation,
                viewModel = viewModel,
                disableReclustering = disableReclustering.value,
                places = it.data
            )
            is UiState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is UiState.Loading -> LoadingScreen()
        }
    }
}


@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun Map(
    paddingValues: PaddingValues,
    viewModel: ListOfPlacesViewModel,
    navigation: INavigationRouter,
    disableReclustering: Boolean,
    places: PlacesResponse
){

    val mapUiSettings by remember { mutableStateOf(
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            //myLocationButtonEnabled = true
            )
    ) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(viewModel.latitude, viewModel.longitude), 16f)
    }

    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var clusterManager by remember { mutableStateOf<ClusterManager<Feature>?>(null) }
    var clusterRenderer by remember { mutableStateOf<CustomMapRenderer?>(null) }

    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            googleMap?.clear()
        } }

    if (places.features.isNotEmpty()) {
        if(!disableReclustering) {
            clusterManager?.addItems(places.features)
            clusterManager?.cluster()
            //clusterManager?.setOnClusterInfoWindowClickListener { marker ->

            //}
        }
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ){

            MapEffect(places) { map ->
                if(googleMap == null) {
                    googleMap = map
                }

                if(clusterManager == null){
                    clusterManager = ClusterManager<Feature>(context, map)
                }
                clusterRenderer = CustomMapRenderer(context, map, clusterManager!!)

                clusterManager?.apply {
                    algorithm = GridBasedAlgorithm()
                    renderer = clusterRenderer
                    renderer?.setOnClusterItemInfoWindowClickListener { place ->
                        navigation.navigateToPlaceDetail(place.properties.xid)
                    }

                }

                map.setOnCameraIdleListener {
                    clusterManager?.cluster()
                }

            }
        }


    }

}