package cz.mendelu.xnazarja.va2.foodMe.di

import cz.mendelu.xnazarja.va2.foodMe.communication.TravelAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideTravelApi(get()) }
}

fun provideTravelApi(retrofit: Retrofit): TravelAPI
        = retrofit.create(TravelAPI::class.java)