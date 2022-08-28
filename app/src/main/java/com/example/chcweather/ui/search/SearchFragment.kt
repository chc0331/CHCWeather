package com.example.chcweather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.example.chcweather.databinding.FragmentSearchBinding
import com.example.chcweather.databinding.FragmentSearchDetailBinding
import com.example.chcweather.ui.BaseFragment

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchDetailBinding: FragmentSearchDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        searchDetailBinding = FragmentSearchDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.zeroHits.visibility = View.GONE
                if (query.isNullOrEmpty()) {
                    //todo : getSearchWeather by query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        val recyclerView = binding.locationSearchRecyclerview

    }
}