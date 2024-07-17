package cz.mendelu.xnazarja.va2.foodMe.communication

import cz.mendelu.xnazarja.va2.foodMe.constants.Constants
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelAPI {

    @Headers("Content-Type: application/json")
    @GET("places/radius")
    suspend fun places(
        @Query("radius") radius: Int,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("kinds") categories: String,
        @Query("limit") limit: Int,
        @Query("rate") rate: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ) : Response<PlacesResponse>

    @Headers("Content-Type: application/json")
    @GET("places/xid/{xid}")
    suspend fun place(
        @Path("xid") xId: String,
        @Query("apikey") api: String
    ) : Response<PlaceDetailResponse>
}