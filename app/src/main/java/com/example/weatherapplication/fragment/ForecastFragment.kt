package com.example.weatherapplication.fragment

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
import androidx.navigation.fragment.navArgs
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.adapter.ForecastAdapter
import com.example.weatherapplication.databinding.FragmentForecastBinding
import com.example.weatherapplication.model.Forecast
import com.example.weatherapplication.model.ForecastList
import com.example.weatherapplication.network.ResultData
import com.example.weatherapplication.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding
    private var forecastList = ArrayList<ForecastList>()
    val viewModel: ForecastViewModel by viewModels()
    private val navArgs: ForecastFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Forecast"
        initAdapter()
        getDataAndSubscribeEvents()
        return binding.root
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = ForecastAdapter(forecastList)
    }


    private val repositoryObserver = Observer<ResultData<Forecast?>> { resultData ->
        when (resultData) {
            is ResultData.Loading -> {
                Toast.makeText(requireContext(), "Loading data", Toast.LENGTH_SHORT).show()
            }
            is ResultData.Success -> {
                forecastList.clear()
                forecastList.addAll(resultData.data?.list!!)
                binding.recyclerView.adapter?.notifyDataSetChanged()
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
            viewModel.getForecast(navArgs.zipCode, "imperial", "df5f5ad7dec319cdbd10e03799917453")
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