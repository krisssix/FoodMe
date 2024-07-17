package cz.mendelu.xnazarja.va2.foodMe.di

import cz.mendelu.xnazarja.va2.foodMe.communication.TravelAPI
import cz.mendelu.xnazarja.va2.foodMe.communication.TravelRemoteRepositoryImpl
import org.koin.dsl.module

val remoteRepositoryModule = module {
    single { provideTravelRemoteRepository(get()) }
}

fun provideTravelRemoteRepository(travelAPI: TravelAPI): TravelRemoteRepositoryImpl
        = TravelRemoteRepositoryImpl(travelAPI)