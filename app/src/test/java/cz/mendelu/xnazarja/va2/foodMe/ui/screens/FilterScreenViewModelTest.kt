package cz.mendelu.xnazarja.va2.foodMe.ui.screens


import cz.mendelu.xnazarja.va2.foodMe.database.data.repository.FakeDataStoreRepository
import cz.mendelu.xnazarja.va2.foodMe.di.dataStoreModule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilterScreenViewModelTest {

    private lateinit var filterScreenVM: FilterScreenViewModel
    private lateinit var fakeDataStoreRepository: FakeDataStoreRepository

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        fakeDataStoreRepository = FakeDataStoreRepository()
        filterScreenVM = FilterScreenViewModel(fakeDataStoreRepository)
        filterScreenVM.radius = 0
        filterScreenVM.limit = 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadPreferences default, correct`() = runBlocking{

            filterScreenVM.loadPreferences()

            assertEquals(filterScreenVM.radius, 100)
            assertEquals(filterScreenVM.limit,20)

    }

    @Test
    fun `savePreferences, correct`() = runBlocking{
            filterScreenVM.savePreferences(
                radius = 50,
                limit = 5
            )
            assertEquals(fakeDataStoreRepository.getRadius(), 50)
            assertEquals(fakeDataStoreRepository.getLimit(),5)
    }


}