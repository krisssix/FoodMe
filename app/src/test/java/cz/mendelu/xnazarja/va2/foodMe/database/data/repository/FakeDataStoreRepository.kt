package cz.mendelu.xnazarja.va2.foodMe.database.data.repository

import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.IDataStoreRepository

class FakeDataStoreRepository: IDataStoreRepository {

    private val firstPref = mutableStateOf(false)
    private val radiusPref = mutableStateOf(100)
    private val limitPref = mutableStateOf(20)
    private val categoriesPref = mutableStateOf("interesting_places")

    override suspend fun setFirstRun() {
        firstPref.value = true
    }

    override suspend fun getFirstRun(): Boolean {
        return firstPref.value
    }

    override suspend fun setRadius(radius: Int) {
        radiusPref.value = radius
    }

    override suspend fun getRadius(): Int {
        return radiusPref.value
    }

    override suspend fun setLimit(limit: Int) {
        limitPref.value = limit
    }

    override suspend fun getLimit(): Int {
        return limitPref.value
    }

    override suspend fun setCategories(categories: String) {
        categoriesPref.value = categories
    }

    override suspend fun getCategories(): String {
        return categoriesPref.value
    }
}