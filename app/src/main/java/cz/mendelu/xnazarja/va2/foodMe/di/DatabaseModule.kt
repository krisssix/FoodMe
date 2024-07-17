package cz.mendelu.xnazarja.va2.foodMe.di

import cz.mendelu.xnazarja.va2.foodMe.TravelAppApplication
import cz.mendelu.xnazarja.va2.foodMe.database.PlaceDatabase
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(): PlaceDatabase = PlaceDatabase.getDatabase(TravelAppApplication.appContext)
    single {
        provideDatabase()
    }
}