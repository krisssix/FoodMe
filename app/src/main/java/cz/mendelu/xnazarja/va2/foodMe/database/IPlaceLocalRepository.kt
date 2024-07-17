package cz.mendelu.xnazarja.va2.foodMe.database

import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.flow.Flow

interface IPlaceLocalRepository {

    fun getAll(): Flow<List<PlaceDetailResponse>>
    suspend fun getList(): List<PlaceDetailResponse>
    suspend fun findById(id : String): PlaceDetailResponse?
    suspend fun insertPlace(place: PlaceDetailResponse)
    suspend fun deletePlace(place: PlaceDetailResponse)
}