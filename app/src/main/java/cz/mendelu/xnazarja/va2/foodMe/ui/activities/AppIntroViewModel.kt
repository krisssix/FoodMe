package cz.mendelu.xnazarja.va2.foodMe.ui.activities

import cz.mendelu.xnazarja.va2.foodMe.architecture.BaseViewModel
import cz.mendelu.xnazarja.va2.foodMe.constants.datastore.IDataStoreRepository

class AppIntroViewModel(private val dataStoreRepository: IDataStoreRepository): BaseViewModel() {
    suspend fun setFirstRun(){
        dataStoreRepository.setFirstRun()
    }
}