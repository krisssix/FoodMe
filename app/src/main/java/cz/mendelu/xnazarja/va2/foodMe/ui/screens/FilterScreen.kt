package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.UiState
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.BackArrowScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.ErrorScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun FilterScreen(
    navigation: INavigationRouter,
    viewModel: FilterScreenViewModel = getViewModel()
){

    val uiState: MutableState<UiState<Boolean>> = rememberSaveable{
        mutableStateOf(UiState.Loading())
    }

    viewModel.filterUiState.value.let {
        when(it){
            is FilterScreenUiStates.Start -> {
                LaunchedEffect(it) {
                    viewModel.loadPreferences()
                }
            }
            is FilterScreenUiStates.Success -> {
                uiState.value = UiState.DataLoaded(it.data)
            }
        }

    }

    BackArrowScreen(
        topBarText = stringResource(R.string.filters),
        content = {
            FilterScreenContent(
                navigation = navigation,
                viewModel = viewModel,
                uiState = uiState.value
            )
        },
        actions = {

        },
        onBackClick = { navigation.returnBack() }
    )

}

@Composable
fun FilterScreenContent(
    navigation: INavigationRouter,
    viewModel: FilterScreenViewModel,
    uiState: UiState<Boolean>
){

    uiState.let {
        when(it){
            is UiState.DataLoaded -> Filters(
                navigation = navigation,
                viewModel = viewModel
            )
            is UiState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is UiState.Loading -> LoadingScreen()
        }
    }

}

@Composable
fun Filters(
    navigation: INavigationRouter,
    viewModel: FilterScreenViewModel
){



    var radiusSlider by remember { mutableStateOf(viewModel.radius.toFloat()) }
    var limitSlider by remember { mutableStateOf(viewModel.limit.toFloat()) }

    Column {

        Text(text = stringResource(id = R.string.radius) + ": ${radiusSlider.toInt()} m", style = MaterialTheme.typography.subtitle1)
        Slider(value = radiusSlider, onValueChange = { radiusSlider = it }, steps = 0, valueRange = 100f..5000f, enabled = true )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = stringResource(id = R.string.limit) + ": ${limitSlider.toInt()}", style = MaterialTheme.typography.subtitle1)
        Slider(value = limitSlider, onValueChange = { limitSlider = it }, steps = 0, valueRange = 1f..200f, enabled = true )

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedButton(onClick = {
            viewModel.savePreferences(
                radius = radiusSlider.toInt(),
                limit = limitSlider.toInt()
            )
            navigation.returnBack()
        }) {
            Text(stringResource(id = R.string.save))
        }

    }

}