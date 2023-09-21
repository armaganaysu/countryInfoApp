package com.armaganaysu.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.armaganaysu.kotlincountries.R
import com.armaganaysu.kotlincountries.databinding.ItemCountryBinding
import com.armaganaysu.kotlincountries.model.Country
import com.armaganaysu.kotlincountries.util.downloadFromMyUrl
import com.armaganaysu.kotlincountries.util.placeholderProgressBar
import com.armaganaysu.kotlincountries.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(val binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

        holder.binding.root.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuidModel)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.downloadFromMyUrl(countryList[position].imageUrl,
            placeholderProgressBar(holder.binding.root.context))
    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}