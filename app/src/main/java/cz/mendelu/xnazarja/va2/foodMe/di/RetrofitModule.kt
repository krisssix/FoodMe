package cz.mendelu.xnazarja.va2.foodMe.di

import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    val dispatcher = Dispatcher()
    httpClient.dispatcher(dispatcher)
    return httpClient.addInterceptor(httpLoggingInterceptor).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    val gson = GsonBuilder()
        .setLenient()
        .create()

    return Retrofit.Builder().baseUrl("https://api.opentripmap.com/0.1/en/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}