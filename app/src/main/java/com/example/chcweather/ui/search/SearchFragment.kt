package com.example.chcweather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chcweather.databinding.FragmentSearchBinding
import com.example.chcweather.databinding.FragmentSearchDetailBinding
import com.example.chcweather.ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val uiScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main) }
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchDetailBinding: FragmentSearchDetailBinding
    private lateinit var viewModel: SearchViewModel

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
        viewModel = ViewModelProvider(
            this@SearchFragment,
            factory
        )[SearchViewModel::class.java]
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.zeroHits.visibility = View.GONE
                query?.let {
                    viewModel.getSearchWeather(it, uiScope)
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