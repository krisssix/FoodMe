package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.UiState
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.BackArrowScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.ErrorScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun TrackPlaceScreen(navigation: INavigationRouter,
                     id: String,
                     viewModel: TrackPlaceViewModel = getViewModel()
) {

    viewModel.placeId = id

    val uiState: MutableState<UiState<PlaceDetailResponse>> = rememberSaveable {
        mutableStateOf(UiState.Loading())
    }

    viewModel.trackPlaceUiState.value.let {
        when(it) {
            is PlaceDetailUiState.Error -> {
                println("TrackPlace Error")
                uiState.value = UiState.Error(it.error)
            }
            is PlaceDetailUiState.Start -> {
                println("TrackPlace Start")
                LaunchedEffect(it) {
                    viewModel.loadPlace()
                }
            }
            is PlaceDetailUiState.Success -> {
                println("TrackPlace Success")
                uiState.value = UiState.DataLoaded(it.data)
            }
        }
    }

    BackArrowScreen(
        topBarText = stringResource(id = R.string.tracking_screen),
        disablePadding = true,
        drawFullScreenContent = true,
        content = {
            TrackPlaceScreenContent(
                navigation = navigation,
                viewModel = viewModel,
                uiState = uiState.value
            )
        },
        onBackClick = { navigation.returnBack() }
    )

}


@Composable
fun TrackPlaceScreenContent(
    navigation: INavigationRouter,
    viewModel: TrackPlaceViewModel,
    uiState: UiState<PlaceDetailResponse>
) {

    Box(Modifier.fillMaxSize()) {
        uiState.let {
            when (it) {
                is UiState.DataLoaded -> TrackMap(
                    navigation = navigation,
                    viewModel = viewModel,
                    place = it.data
                )
                is UiState.Error -> ErrorScreen(text = stringResource(id = it.error))
                is UiState.Loading -> LoadingScreen()
            }
        }
    }

}


@Composable
fun TrackMap(
    navigation: INavigationRouter,
    viewModel: TrackPlaceViewModel,
    place: PlaceDetailResponse
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(place.point.lat, place.point.lon),
            16f
        )
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            cameraPositionState = cameraPositionState
        ) {

            Marker(
                state = MarkerState(position = LatLng(place.point.lat, place.point.lon)),
                title = place.name
            )

            Circle(
                center = LatLng(place.point.lat, place.point.lon),
                radius = 100.0,
                strokeColor = colorResource(id = R.color.purple_500),
                fillColor = colorResource(id = R.color.purple_200T)
            )

        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 64.dp,
                    end = 64.dp,
                    bottom = 16.dp
                )
                .align(Alignment.BottomCenter),
            onClick = {
                viewModel.savePlace()
                navigation.returnBack()
            }) {
            Text(text = stringResource(id = R.string.collect))
        }
    }


}
