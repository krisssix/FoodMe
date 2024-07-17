package cz.mendelu.xnazarja.va2.foodMe.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.UiState
import cz.mendelu.xnazarja.va2.foodMe.models.places.Feature
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import cz.mendelu.xnazarja.va2.foodMe.navigation.INavigationRouter
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.ErrorScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.elements.LoadingScreen
import cz.mendelu.xnazarja.va2.foodMe.ui.theme.Purple40
import org.koin.androidx.compose.getViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfPlacesScreen(navigation: INavigationRouter,
                       viewModel: ListOfPlacesViewModel = getViewModel()
) {

    val uiState: MutableState<UiState<PlacesResponse>> = rememberSaveable {
        mutableStateOf(UiState.Loading())
    }

    viewModel.placesUiState.value.let {
        when(it){
            is ListOfPlacesUiState.Error -> {
                uiState.value = UiState.Error(it.error)
            }
            is ListOfPlacesUiState.Start -> {
                uiState.value = UiState.Loading()
                LaunchedEffect(it) {
                    viewModel.loadPlaces()
                }
            }
            is ListOfPlacesUiState.Success -> {
                uiState.value = UiState.DataLoaded(it.data)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .weight(1.5f)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.placesUiState.value = ListOfPlacesUiState.Start()
                    }) {
                        androidx.compose.material.Icon(
                            painter = painterResource(R.drawable.refresh),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {
                        navigation.navigateToFilterScreen()
                    }) {
                        androidx.compose.material.Icon(
                            painter = painterResource(R.drawable.adjust),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                elevation = 0.dp,
                backgroundColor = Purple40
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Tabs(
                    paddingValues = it,
                    navigation = navigation,
                    uiState = uiState.value,
                    viewModel = viewModel
                )
            }
        },
    )
}


@Composable
fun Tabs(paddingValues: PaddingValues,
         navigation: INavigationRouter,
         viewModel: ListOfPlacesViewModel,
         uiState: UiState<PlacesResponse>) {

    var tabIndex by remember { mutableStateOf(1) }
    val tabTitles = listOf("Favourites", "List", "Nearby")
    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) })
            }
        }
        when (tabIndex) {
            0 ->
                SavedPlacesScreen(
                    paddingValues = paddingValues,
                    navigation = navigation
                )
            1 -> PlacesListScreenContent(
                paddingValues = paddingValues,
                navigation = navigation,
                uiState = uiState,
                viewModel = viewModel

                )
            2 -> MapScreenContent(
                paddingValues = paddingValues,
                navigation = navigation,
                viewModel = viewModel,
                uiState = uiState
            )
        }
    }
}


@Composable
fun PlacesListScreenContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: ListOfPlacesViewModel,
    uiState: UiState<PlacesResponse>) {


    uiState.let {
            when (it) {
                is UiState.DataLoaded -> {
                    PlacesList(
                        paddingValues = paddingValues,
                        navigation = navigation,
                        places = it.data,
                        viewModel = viewModel
                    )
                }
                is UiState.Error -> ErrorScreen(text = stringResource(id = it.error))
                is UiState.Loading -> LoadingScreen()
            }
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesList(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: ListOfPlacesViewModel,
    places: PlacesResponse
) {

    var searchText by remember { mutableStateOf("") }

    //TODO: searchbar
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
    ) {
        Text(
            text = "Where do you want to \n" +
                    "eat today?",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search for places") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.searchQuery.value = searchText
                viewModel.loadPlaces()
                defaultKeyboardAction(ImeAction.Search)
            })
        )
    }

    //TODO: Categories - design
    val categories = listOf("restaurants", "cafes", "fast_food", "food_courts", "pubs", "bars", "biergartens", "picnic_site")
    Column {
        Text(
            text = "Choose category",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(categories.size) { index ->
                val category = categories[index]
                CategoryRow(
                    categoryName = category,
                    onCategorySelected = {
                        viewModel.selectedCategory.value = category
                        viewModel.loadPlaces()
                    }
                )
            }
        }
    }

    //TODO: Available restaurants - photos, UI, design
    Column {
        Text(
            text = "Available restaurants",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)

        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            if (places.features.isNotEmpty()) {
                places.features.forEach { feature ->
                    item(key = feature.properties.xid) {
                        PlaceRow(
                            place = feature,
                            onRowClick = {
                                navigation.navigateToPlaceDetail(feature.properties.xid)
                            }
                        )
                    }
                }
            } else {
                item {
                    EmptyPlaces()
                }
            }
        }
    }

}


@Composable
fun PlaceRow(
    place: Feature,
    onRowClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable(onClick = onRowClick),
    ) {

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onRowClick)) {

        Column {
            place.properties.let {
                Text(text = it.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth(0.8f)
                )
            }

        }

        Spacer(modifier = Modifier.weight(1.0F))

        Column {
            Text(text = "${place.properties.dist.roundToInt()} m",
                fontSize = 14.sp)
        }

    }
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    categoryName: String,
    onCategorySelected: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.White)
            //TODO make the border appear only on selected
            .clickable { onCategorySelected(categoryName) }
            .border(border = BorderStroke(0.5.dp, Color(0xff97000a)),
        shape = RoundedCornerShape(8.dp))
            .padding(all = 8.dp)
        ,
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White,
            contentColor = Color(0xff0c0507)
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.9334259033203125.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(width = 119.dp)
                .height(height = 50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image1648),
                contentDescription = "image 1648",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .requiredSize(size = 27.dp)
                    .clip(shape = RoundedCornerShape(11.900138854980469.dp)))
            Text(
                text = categoryName,
                color = Color(0xff0c0507),
                textAlign = TextAlign.Center,
                lineHeight = 1.63.em,
                style = TextStyle(
                    fontSize = 12.6934814453125.sp))
        }
    }
}


@Composable
fun EmptyPlaces() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image1657),
            contentDescription = "No Restaurants Found"
        )
        Text(
            text = "No restaurants available in this category",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


/*

@Composable
fun SearchBarWithSuggestions(
    viewModel: ListOfPlacesViewModel, // This should contain logic for fetching suggestions
    navigation: INavigationRouter
) {
    var searchText by remember { mutableStateOf("") }
    val suggestions = viewModel.suggestions.collectAsState()

    Column {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        suggestions.value.forEach { suggestion ->
            Text(
                text = suggestion.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // TODO: Handle the suggestion click
                      //  navigation.navigateToSomeScreen(suggestion.id)
                    }
                    .padding(16.dp)
            )
        }
    }

    LaunchedEffect(searchText) {
        viewModel.fetchSuggestions(searchText)
    }
}


 */