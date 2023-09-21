package com.armaganaysu.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.armaganaysu.kotlincountries.R
import com.armaganaysu.kotlincountries.databinding.FragmentCountryBinding
import com.armaganaysu.kotlincountries.util.downloadFromMyUrl
import com.armaganaysu.kotlincountries.util.placeholderProgressBar
import com.armaganaysu.kotlincountries.viewmodel.CountryViewModel
import com.armaganaysu.kotlincountries.viewmodel.FeedViewModel


class CountryFragment : Fragment(R.layout.fragment_country) {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CountryViewModel

    private var countryUniqueId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // arguments kullanarak veri aktarımı
        arguments?.let {
            countryUniqueId = CountryFragmentArgs.fromBundle(it).countryUniqueId
        }

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUniqueId)

       observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.countryCurrency.text = country.countryCurrency
                binding.countryLanguage.text = country.countryLanguage
                binding.countryRegion.text = country.countryRegion
                context?.let{
                    binding.countryImage.downloadFromMyUrl(country.imageUrl, placeholderProgressBar(it))
                }

            }
        })
    }







    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}