package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.UiState
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.ErrorScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel


@Composable
fun SavedPlacesScreen(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: SavedPlacesViewModel = getViewModel()
){

    val uiState: MutableState<UiState<List<PlaceDetailResponse>>> = rememberSaveable {
        mutableStateOf(UiState.Loading())
    }

    viewModel.savedPlacesUiState.value.let {
        when(it){
            is SavedPlacesUiState.Error -> {
                uiState.value = UiState.Error(it.error)
            }
            is SavedPlacesUiState.Start -> {
                LaunchedEffect(it) {
                    viewModel.loadSavedPlaces()
                }
            }
            is SavedPlacesUiState.Success -> {
                uiState.value = UiState.DataLoaded(it.data)
            }
        }
    }

    SavedPlacesScreenContent(paddingValues = paddingValues, navigation = navigation, uiState = uiState.value)

}

@Composable
fun SavedPlacesScreenContent(paddingValues: PaddingValues, navigation: INavigationRouter, uiState: UiState<List<PlaceDetailResponse>>){

    uiState.let {
        when(it){
            is UiState.DataLoaded -> SavedPlacesList(
                paddingValues = paddingValues,
                navigation = navigation,
                places = it.data
            )
            is UiState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is UiState.Loading -> LoadingScreen()
        }
    }

}

@Composable
fun SavedPlacesList(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    places: List<PlaceDetailResponse>
) {

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        places.forEach {
            item(key = it.xid) {
                SavedPlaceRow(
                    place = it,
                    onRowClick = {
                        navigation.navigateToPlaceDetail(it.xid)
                    }
                )
            }
        }
    }

}


@Composable
fun SavedPlaceRow(place: PlaceDetailResponse, onRowClick: () -> Unit){

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onRowClick)) {

        Column {
            Text(text = place.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .fillMaxWidth(0.8f)
            )

        }

        Spacer(modifier = Modifier.weight(1.0F))

        Column {
            if (place.done){
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Favourite")
            } else if (place.favorte){
                Image(painterResource(id = R.drawable.icon_filled), contentDescription = "Favourite")
            }
        }

    }

}
