package cz.mendelu.xnazarja.va2.foodMe

import android.app.Application
import android.content.Context
import cz.mendelu.xnazarja.va2.foodMe.di.*
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TravelAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@TravelAppApplication)
            modules(listOf(
                apiModule,
                remoteRepositoryModule,
                retrofitModule,
                viewModelModule,
                databaseModule,
                daoModule,
                localRepositoryModule,
                dataStoreModule
            ))
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }


}