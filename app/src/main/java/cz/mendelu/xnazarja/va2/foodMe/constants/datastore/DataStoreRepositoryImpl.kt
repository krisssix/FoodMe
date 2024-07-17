package cz.mendelu.xnazarja.va2.foodMe.constants.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.edit
import cz.mendelu.xnazarja.va2.foodMe.dataStore
import kotlinx.coroutines.flow.first

class DataStoreRepositoryImpl(private val context: Context) : IDataStoreRepository {

    override suspend fun setFirstRun() {
        val preferencesKey = booleanPreferencesKey(DataStoreConstants.FIRST_RUN)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = false
        }
    }

    override suspend fun getFirstRun(): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(DataStoreConstants.FIRST_RUN)
            val preferences = context.dataStore.data.first()
            if(!preferences.contains(preferencesKey)) {
                true
            } else {
                preferences[preferencesKey]!!
            }
        }catch (e: Exception){
            e.printStackTrace()
            true
        }
    }

    override suspend fun setRadius(radius: Int) {
        val preferencesKey = intPreferencesKey(DataStoreConstants.RADIUS)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = radius
        }
    }

    override suspend fun getRadius(): Int {
        return try {
            val preferencesKey = intPreferencesKey(DataStoreConstants.RADIUS)
            val preferences = context.dataStore.data.first()
            if(!preferences.contains(preferencesKey)) {
                1000
            } else {
                preferences[preferencesKey]!!
            }
        }catch (e: Exception){
            e.printStackTrace()
            1000
        }
    }

    override suspend fun setLimit(limit: Int) {
        val preferencesKey = intPreferencesKey(DataStoreConstants.LIMIT)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = limit
        }
    }

    override suspend fun getLimit(): Int {
        return try {
            val preferencesKey = intPreferencesKey(DataStoreConstants.LIMIT)
            val preferences = context.dataStore.data.first()
            if(!preferences.contains(preferencesKey)) {
                20
            } else {
                preferences[preferencesKey]!!
            }
        }catch (e: Exception){
            e.printStackTrace()
            20
        }
    }

    override suspend fun setCategories(categories: String) {
        val preferencesKey = stringPreferencesKey(DataStoreConstants.CATEGORIES)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = categories
        }
    }

    override suspend fun getCategories(): String {
        return try {
            val preferencesKey = stringPreferencesKey(DataStoreConstants.CATEGORIES)
            val preferences = context.dataStore.data.first()
            if(!preferences.contains(preferencesKey)) {
                "foods"
            } else {
                preferences[preferencesKey]!!
            }
        }catch (e: Exception){
            e.printStackTrace()
            "foods"
        }
    }


}