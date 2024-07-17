package cz.mendelu.xnazarja.va2.foodMe.di

import cz.mendelu.xnazarja.va2.foodMe.database.PlaceDatabase
import cz.mendelu.xnazarja.va2.foodMe.database.PlacesDao
import org.koin.dsl.module

val daoModule = module {
    fun providePlacesDao(database: PlaceDatabase): PlacesDao = database.placesDao()
    single {
        providePlacesDao(get())
    }
}