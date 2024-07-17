package cz.mendelu.xnazarja.va2.foodMe.di

import android.content.Context
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.DataStoreRepositoryImpl
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.IDataStoreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { provideDataStoreRepository(androidContext()) }
}

fun provideDataStoreRepository(context: Context): IDataStoreRepository
        = DataStoreRepositoryImpl(context)