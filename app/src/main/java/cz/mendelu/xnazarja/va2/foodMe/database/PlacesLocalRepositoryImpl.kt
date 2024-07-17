package cz.mendelu.xnazarja.va2.foodMe.database

import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.flow.Flow

class PlacesLocalRepositoryImpl(private val placesDao: PlacesDao) : IPlaceLocalRepository {

    override fun getAll(): Flow<List<PlaceDetailResponse>> {
        return placesDao.getAll()
    }

    override suspend fun getList(): List<PlaceDetailResponse> {
        return placesDao.getList()
    }

    override suspend fun findById(id: String): PlaceDetailResponse? {
        return placesDao.findById(id)
    }

    override suspend fun insertPlace(place: PlaceDetailResponse) {
        return placesDao.insertPlace(place)
    }

    override suspend fun deletePlace(place: PlaceDetailResponse) {
        return placesDao.deletePlace(place)
    }


}