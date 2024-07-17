package cz.mendelu.xnazarja.va2.foodMe.constants.datastore

interface IDataStoreRepository {
    suspend fun setFirstRun()
    suspend fun getFirstRun(): Boolean

    suspend fun setRadius(radius: Int)
    suspend fun getRadius(): Int

    suspend fun setLimit(limit: Int)
    suspend fun getLimit(): Int

    suspend fun setCategories(categories: String)
    suspend fun getCategories(): String

}