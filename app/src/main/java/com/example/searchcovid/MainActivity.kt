package com.example.searchcovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmretrofit.CovidApi
import com.example.mvvmretrofit.CovidViewAdapter
import com.example.mvvmretrofit.repository.CovidRepository
import com.example.mvvmretrofit.viewmodel.CovidViewModel
import com.example.mvvmretrofit.viewmodel.CovidViewModelFactory
import com.example.searchcovid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var covidViewModel: CovidViewModel
    private val covidAdapter: CovidViewAdapter by lazy {
        CovidViewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = CovidRepository()
        val viewModelFactory = CovidViewModelFactory(repository)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        covidViewModel = ViewModelProvider(this, viewModelFactory).get(CovidViewModel::class.java)
        covidViewModel.getAll(CovidApi.TOKEN)
        initAdapter()
        observeCovidList()
    }

    private fun initAdapter() {
        binding.re.adapter = covidAdapter
    }

    private fun observeCovidList() {
        covidViewModel.liveCovidVo.observe(this, Observer { covidList ->
            covidAdapter.setList(covidList)
        })
    }
}