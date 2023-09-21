package com.armaganaysu.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.armaganaysu.kotlincountries.model.Country
import com.armaganaysu.kotlincountries.services.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application): BaseViewModel(application){
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuidModel : Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuidModel)
            countryLiveData.value = country
        }
    }
}