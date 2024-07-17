package cz.mendelu.xnazarja.va2.foodMe.communication

import cz.mendelu.xnazarja.va2.foodMe.architecture.CommunicationError
import cz.mendelu.xnazarja.va2.foodMe.architecture.CommunicationResult
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xnazarja.va2.foodMe.models.places.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TravelRemoteRepositoryImpl(private val travelAPI: TravelAPI)
    : ITravelRemoteRepository {

    override suspend fun places(
        radius: Int,
        longitude: Double,
        latitude: Double,
        categories: String,
        limit: Int,
        rate: String,
        api: String
    ): CommunicationResult<PlacesResponse> {
        try {
            val response = withContext(Dispatchers.IO) {
                travelAPI.places(
                    radius = radius,
                    longitude = longitude,
                    latitude = latitude,
                    categories =  categories,
                    rate = rate,
                    limit = limit
                )
            }
            if (response.isSuccessful){
                if(response.body() != null) {
                    return CommunicationResult.Success(response.body()!!)
                } else {
                    return CommunicationResult.Error(CommunicationError(response.code(), response.errorBody().toString()))
                }
            } else {
                return CommunicationResult.Error(CommunicationError(response.code(), response.errorBody().toString()))
            }
        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }

    override suspend fun place(xId: String, api: String): CommunicationResult<PlaceDetailResponse> {
        return try {
            processResponse(withContext(Dispatchers.IO){travelAPI.place(xId = xId, api = api)})
        } catch (timeoutEx: SocketTimeoutException) {
            return CommunicationResult.Exception(timeoutEx)
        } catch (unknownHostEx: UnknownHostException) {
            return CommunicationResult.Exception(unknownHostEx)
        }
    }
}