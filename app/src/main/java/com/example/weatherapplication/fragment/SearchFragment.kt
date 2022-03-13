package com.example.weatherapplication.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.databinding.FragmentSearchBinding
import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.network.ResultData
import com.example.weatherapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var currentConditions: CurrentConditions
    val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Search"
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.btnSearch.setOnClickListener {
            getDataAndSubscribeEvents()
        }
        binding.etZipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSearch.isEnabled = p0?.length == 5
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private val repositoryObserver = Observer<ResultData<CurrentConditions?>> { resultData ->
        when (resultData) {
            is ResultData.Loading -> {
                Toast.makeText(requireContext(), "Loading data", Toast.LENGTH_SHORT).show()
            }
            is ResultData.Success -> {
                currentConditions = resultData.data!!
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
                        currentConditions,
                        binding.etZipCode.text.toString()
                    )
                )
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
        val repositoriesLiveData = viewModel.getCurrentConditions(
            binding.etZipCode.text.toString(),
            "imperial",
            "df5f5ad7dec319cdbd10e03799917453"
        )
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