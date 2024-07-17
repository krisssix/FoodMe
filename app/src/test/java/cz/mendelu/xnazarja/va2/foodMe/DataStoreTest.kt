package cz.mendelu.xnazarja.va2.foodMe

import cz.mendelu.xnazarja.va2.foodMe.database.data.repository.FakeDataStoreRepository
import cz.mendelu.xnazarja.va2.foodMe.ui.screens.FilterScreenViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DataStoreTest {

    private lateinit var fakeDataStoreRepository: FakeDataStoreRepository

    @Before
    fun setUp() {
        fakeDataStoreRepository = FakeDataStoreRepository()
    }

    @Test
    fun `Get first default, correct`() = runBlocking {
        val value = fakeDataStoreRepository.getFirstRun()
        Assert.assertEquals(value, false)
    }


    @Test
    fun `Get radius default, correct`() = runBlocking {
        val value = fakeDataStoreRepository.getRadius()
        Assert.assertEquals(value, 100)
    }

    @Test
    fun `Get limit default, correct`() = runBlocking {
        val value = fakeDataStoreRepository.getLimit()
        Assert.assertEquals(value, 20)
    }

    @Test
    fun `Get categories default, correct`() = runBlocking {
        val value = fakeDataStoreRepository.getCategories()
        Assert.assertEquals(value, "interesting_places")
    }

    @Test
    fun `Set first, correct`() = runBlocking {
        fakeDataStoreRepository.setFirstRun()
        Assert.assertEquals(fakeDataStoreRepository.getFirstRun(), true)
    }


    @Test
    fun `Set radius, correct`() = runBlocking {
        fakeDataStoreRepository.setRadius(200)
        Assert.assertEquals(fakeDataStoreRepository.getRadius(), 200)
    }

    @Test
    fun `Set limit, correct`() = runBlocking {
        fakeDataStoreRepository.setLimit(50)
        Assert.assertEquals(fakeDataStoreRepository.getLimit(), 50)
    }

    @Test
    fun `Set categories, correct`() = runBlocking {
        fakeDataStoreRepository.setCategories("religion,history,nature")
        Assert.assertEquals(fakeDataStoreRepository.getCategories(), "religion,history,nature")
    }

}
