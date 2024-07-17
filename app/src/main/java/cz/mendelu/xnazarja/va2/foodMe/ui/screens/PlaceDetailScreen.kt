package cz.mendelu.xnazarja.va2.foodMe.ui.screens


import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.UiState
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.BackArrowScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.ErrorScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun PlaceDetailScreen(navigation: INavigationRouter,
                      id: String,
                      viewModel: PlaceDetailViewModel = getViewModel()
) {

    viewModel.placeId = id

    val uiState: MutableState<UiState<PlaceDetailResponse>> = rememberSaveable {
        mutableStateOf(UiState.Loading())
    }



    viewModel.placeDetailUiState.value.let {
        when(it) {
            is PlaceDetailUiState.Error -> {
                uiState.value = UiState.Error(it.error)
            }
            is PlaceDetailUiState.Start -> {
                viewModel.isDeleteVisible.value = false
                LaunchedEffect(it) {
                    viewModel.loadPlaceFromDatabase()
                }
            }
            is PlaceDetailUiState.Success -> {
                uiState.value = UiState.DataLoaded(it.data)
            }
        }
    }

    BackArrowScreen(
        topBarText = stringResource(R.string.place_detail),
        content = {
            PlaceDetailScreenContent(
                navigation = navigation,
                uiState = uiState.value
            )
        },
        actions = {
            if (viewModel.isDeleteVisible.value) {
                IconButton(
                    onClick = {
                        viewModel.deletePlace()
                        viewModel.isDeleteVisible.value = false
                        viewModel.checkedState.value = false
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_24),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
            displayToggleButton(viewModel)
        },
        onBackClick = { navigation.returnBack() }
    )

}


@Composable
fun PlaceDetailScreenContent(
    navigation: INavigationRouter,
    uiState: UiState<PlaceDetailResponse>
    ) {

    uiState.let {
        when(it){
            is UiState.DataLoaded -> PlaceDetail(
                navigation = navigation,
                place = it.data
            )
            is UiState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is UiState.Loading -> LoadingScreen()

        }
    }

}

@Composable
fun PlaceDetail(
    navigation: INavigationRouter,
    place: PlaceDetailResponse
    ){

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {


        Text(text = place.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)

        if (place.preview?.source?.isNotEmpty() == true) {
            AsyncImage(
                model = place.preview.source,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)


            )
        }

        if (place.done){
            Text(text = stringResource(id = R.string.collected), style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.padding(16.dp))
        }

        if (place.info?.descr?.isNotEmpty() == true) {
            Text(text = place.info.descr)
        } else if (place.wikipedia_extracts?.text?.isNotEmpty() == true) {
            Text(text = place.wikipedia_extracts.text)
        } else {
            Text(text = "No description")
        }

        if (place.kinds?.isNotEmpty() == true) {
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Categories: ${formatKinds(place.kinds)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }


        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(place.point.lat, place.point.lon), 20f)
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(10.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(place.point.lat, place.point.lon)),
                title = place.name
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navigation.navigateToTrackPlaceScreen(place.xid)
            }
        ) {
            Text(
                text = stringResource(id = R.string.track)
            )
        }

    }


}

fun formatKinds(kinds: String): String {
    return kinds.replace(",", ", ").replace("_", " ")
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun displayToggleButton(viewModel: PlaceDetailViewModel) {

        IconToggleButton(
            checked = viewModel.checkedState.value,
            onCheckedChange = {
                viewModel.checkedState.value = !viewModel.checkedState.value
                viewModel.starAction()
            },
            modifier = Modifier.padding(10.dp)
        ) {

            val transition = updateTransition(viewModel.checkedState.value, label = "asdfgh")

            val tint by transition.animateColor(label = "iconColor") { isChecked ->
                if (isChecked) Color.Yellow else Color.Black
            }

            val size by transition.animateDp(
                transitionSpec = {
                    if (false isTransitioningTo true) {
                        keyframes {
                            durationMillis = 250
                            30.dp at 0 with LinearOutSlowInEasing
                            35.dp at 15 with FastOutLinearInEasing
                            40.dp at 75
                            35.dp at 150
                        }
                    } else {
                        spring(stiffness = Spring.StiffnessVeryLow)
                    }
                },
                label = "Size"
            ) { 30.dp }


            val imagePainter = if (viewModel.checkedState.value) {
                painterResource(id = R.drawable.icon_filled)
            } else {
                painterResource(id = R.drawable.icon_filled)
            }

            Icon(
                painter = imagePainter,
                contentDescription = "Icon",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }