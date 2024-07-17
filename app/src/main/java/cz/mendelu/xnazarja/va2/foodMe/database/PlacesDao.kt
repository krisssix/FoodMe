package cz.mendelu.xnazarja.va2.foodMe.database

import androidx.room.*
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface PlacesDao {

    @Query("SELECT * FROM places")
    fun getAll(): Flow<List<PlaceDetailResponse>>

    @Query("SELECT * FROM places")
    suspend fun getList(): List<PlaceDetailResponse>

    @Query("SELECT * FROM places WHERE xid = :id")
    suspend fun findById(id : String): PlaceDetailResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceDetailResponse)

    @Delete
    suspend fun deletePlace(place: PlaceDetailResponse)

}