package com.armaganaysu.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.armaganaysu.kotlincountries.model.Country
import com.armaganaysu.kotlincountries.services.CountryAPIService
import com.armaganaysu.kotlincountries.services.CountryDatabase
import com.armaganaysu.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.Collections.list

class FeedViewModel(application: Application) : BaseViewModel(application){
    private var countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable() //İndirilip kullanılmayan componentları silip hafızayı rahatlatıyor
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L //10 dakika

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private fun storeInSQLite(list: List<Country>){
    launch {
        val dao = CountryDatabase(getApplication()).countryDao()
        dao.deleteAllCountries()
        val listLong = dao.insertAll(*list.toTypedArray()) // -> list -> individual
        var i = 0
        while(i<list.size){
            list[i].uuidModel = listLong[i].toInt()
            i++
        }
        showCountries(list)
    }
        customPreferences.saveTime(System.nanoTime())
    }
    private fun showCountries(countryList: List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
    private fun getDataFromAPI(){
        countryLoading.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                    storeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries from API",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }
    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries from SQLite",Toast.LENGTH_LONG).show()
        }
    }
    fun refreshData(){
       val updateTime = customPreferences.getTime()
        if(updateTime !=null && updateTime != 0L && System.nanoTime() - updateTime< refreshTime){
            getDataFromSQLite()
        } else{
            getDataFromAPI()
        }

        /*
        test cases
        val country = Country("Turkey","Europe","Ankara","TRY","Turkish","www.ss.com")
        val country2 = Country("France","Europe","Paris","EUR","French","www.ss.com")
        val country3 = Country("Germany","Europe","Berlin","EUR","German","www.ss.com")

        val countryList = arrayListOf<Country>(country,country2,country3)
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false*/
    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}