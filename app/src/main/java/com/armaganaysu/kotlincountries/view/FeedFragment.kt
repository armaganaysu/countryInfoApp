package com.armaganaysu.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.armaganaysu.kotlincountries.R
import com.armaganaysu.kotlincountries.adapter.CountryAdapter
import com.armaganaysu.kotlincountries.databinding.FragmentFeedBinding
import com.armaganaysu.kotlincountries.model.Country
import com.armaganaysu.kotlincountries.viewmodel.FeedViewModel


class FeedFragment : Fragment(R.layout.fragment_feed) {

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter


        binding.swipeRefreshlayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE // Recyler View Kaybolacak
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE

            viewModel.refreshFromAPI() // internetten tekrar çekecek
            binding.swipeRefreshlayout.isRefreshing = false
        }
        observeLiveData()
    /*   binding.fragmentButton.setOnClickListener {
           val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
           Navigation.findNavController(it).navigate(action)
       }*/

    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
        countries?.let{
            binding.countryList.visibility = View.VISIBLE
            countryAdapter.updateCountryList(countries)
        }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let{
                if(it){//booleanım true ise
                binding.countryError.visibility = View.VISIBLE
                }
                else{
                    binding.countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let{
                if(it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryError.visibility = View.GONE
                    binding.countryList.visibility = View.GONE
                }
                else{
                    binding.countryLoading.visibility = View.GONE
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