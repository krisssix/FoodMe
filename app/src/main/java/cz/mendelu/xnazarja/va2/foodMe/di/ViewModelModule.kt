package cz.mendelu.xnazarja.va2.foodMe.di

import cz.mendelu.xnazarja.va2.foodMe.ui.activities.AppIntroViewModel
import cz.mendelu.xnazarja.va2.foodMe.ui.activities.MainActivityViewModel
import cz.mendelu.xnazarja.va2.foodMe.ui.screens.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListOfPlacesViewModel(get(), get()) }
    viewModel { PlaceDetailViewModel(get(), get()) }
    viewModel { SavedPlacesViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { AppIntroViewModel(get()) }
    viewModel { FilterScreenViewModel(get()) }
    viewModel { TrackPlaceViewModel(get(), get()) }

}