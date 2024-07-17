package cz.mendelu.xnazarja.va2.foodMe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.PlaceDetailResponse

@Database(entities = [PlaceDetailResponse::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    companion object {

        private var INSTANCE: PlaceDatabase? = null

        fun getDatabase(context: Context): PlaceDatabase {
            if (INSTANCE == null) {
                synchronized(PlaceDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PlaceDatabase::class.java, "places_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}