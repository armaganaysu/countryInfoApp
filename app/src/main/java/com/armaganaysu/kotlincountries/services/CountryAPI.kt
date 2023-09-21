package com.armaganaysu.kotlincountries.services

import com.armaganaysu.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    //https://raw.githubusercontent.com/armaganaysu/countrydataset/main/datasetcountries/countrydataset.json
    //BASE https://raw.githubusercontent.com/
    //REST armaganaysu/countrydataset/main/datasetcountries/countrydataset.json


//Retrofitteki Call yerine Rxjava'nın single'ı (emits exactly one element)
@GET("armaganaysu/countrydataset/main/datasetcountries/countrydataset.json")
fun getCountries():Single<List<Country>>
}
