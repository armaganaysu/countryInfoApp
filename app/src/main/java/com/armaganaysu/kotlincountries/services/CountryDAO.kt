package com.armaganaysu.kotlincountries.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.armaganaysu.kotlincountries.model.Country

@Dao
interface CountryDAO {

    //Data Access Object

    @Insert
     suspend fun insertAll(vararg countries: Country) : List<Long>

// Insert ->    INSERT INTO
// suspend -> corouitineler içerisinde çağırılır, pause & resume
//vararg -> sayısı tam belli olmayan miktarda objeyi vermek için (listeyi tek tek vermek gibi) bunun sonucunda liste döndürecek
//List<Long> -> primary key döndürüyor

    @Query("SELECT * FROM country")
     suspend  fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuidModel = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}