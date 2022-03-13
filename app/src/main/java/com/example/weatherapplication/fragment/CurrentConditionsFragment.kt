package com.example.weatherapplication.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentCurrentConditionsBinding
import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.network.ResultData
import com.example.weatherapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs

@AndroidEntryPoint
class CurrentConditionsFragment : Fragment() {
    private lateinit var binding: FragmentCurrentConditionsBinding
    private lateinit var currentConditions: CurrentConditions
    val viewModel: MainViewModel by viewModels()
    private val navArgs: CurrentConditionsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCurrentConditionsBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Current Conditions"
        currentConditions = navArgs.currentConditions
        initViews()
        initListeners()
        //getDataAndSubscribeEvents()
        return binding.root
    }

    private fun initListeners() {
        binding.btnForecast.setOnClickListener {
            findNavController().navigate(
                CurrentConditionsFragmentDirections.actionCurrentConditionsFragmentToForecastFragment(
                    navArgs.zipCode
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        Glide.with(requireContext())
            .load("http://openweathermap.org/img/wn/${currentConditions.weather?.get(0)?.icon}@2x.png")
            .into(binding.ivSun)
        binding.tvTemperature.text =
            "${(currentConditions.main?.temp)?.toInt()}${resources.getString(R.string.degree)}"
        binding.tvFeelLike.text =
            "Feel like ${(currentConditions.main?.feels_like)?.toInt()}${resources.getString(R.string.degree)}"
        binding.tvHighTemperature.text =
            "High ${(currentConditions.main?.temp_max)?.toInt()}${resources.getString(R.string.degree)}"
        binding.tvLowTemperature.text =
            "Low ${(currentConditions.main?.temp_min)?.toInt()}${resources.getString(R.string.degree)}"
        binding.tvHumidity.text = "Humidity ${currentConditions.main?.humidity}%"
        binding.tvPressure.text = "Pressure ${currentConditions.main?.pressure}"
        binding.tvLocation.text = currentConditions.name
    }

    private val repositoryObserver = Observer<ResultData<CurrentConditions?>> { resultData ->
        when (resultData) {
            is ResultData.Loading -> {
                Toast.makeText(requireContext(), "Loading data", Toast.LENGTH_SHORT).show()
            }
            is ResultData.Success -> {
                currentConditions = resultData.data!!
                initViews()
            }
            is ResultData.Failed -> {
                showMessage(resultData.message.toString())
            }
            is ResultData.Exception -> {
                showMessage(resultData.exception?.message.toString())
            }
        }
    }

    private fun getDataAndSubscribeEvents() {
        val repositoriesLiveData =
            viewModel.getCurrentConditions("55437", "imperial", "df5f5ad7dec319cdbd10e03799917453")
        repositoriesLiveData.observe(viewLifecycleOwner, repositoryObserver)
    }

    private fun showMessage(text: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(text)
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()

            })

        val alert = dialogBuilder.create()
        alert.setTitle("Alert Message")
        alert.show()
    }
}