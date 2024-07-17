package cz.mendelu.xnazarja.va2.foodMe.communication

import cz.mendelu.xnazarja.va2.foodMe.architecture.CommunicationResult
import cz.mendelu.xnazarja.va2.foodMe.architecture.IBaseRemoteRepository
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface ITravelRemoteRepository : IBaseRemoteRepository {
    suspend fun places(
        @Query("radius") radius: Int,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("kinds") categories: String,
        @Query("limit") limit: Int,
        @Query("rate") rate: String,
        @Query("apikey") api: String
    ) : CommunicationResult<PlacesResponse>

    suspend fun place(
        @Path("xid") xId: String,
        @Query("apikey") api: String
    ) : CommunicationResult<PlaceDetailResponse>
}