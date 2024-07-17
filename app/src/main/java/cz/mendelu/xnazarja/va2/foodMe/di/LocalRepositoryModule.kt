package cz.mendelu.xnazarja.va2.foodMe.di

import cz.mendelu.xnazarja.va2.foodMe.database.IPlaceLocalRepository
import cz.mendelu.xnazarja.va2.foodMe.database.PlacesDao
import cz.mendelu.xnazarja.va2.foodMe.database.PlacesLocalRepositoryImpl
import org.koin.dsl.module

val localRepositoryModule = module {
    fun provideLocalTaskRepository(dao: PlacesDao): IPlaceLocalRepository {
        return PlacesLocalRepositoryImpl(dao)
    }
    single { provideLocalTaskRepository(get()) }
}