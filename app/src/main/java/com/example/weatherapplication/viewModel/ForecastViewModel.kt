package com.example.weatherapplication.viewModel

import androidx.lifecycle.*
import com.example.weatherapplication.model.Forecast
import com.example.weatherapplication.network.DataUseCase
import com.example.weatherapplication.network.NetworkRepository
import com.example.weatherapplication.network.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val dataUseCase: DataUseCase) : ViewModel() {

    fun getForecast(
        zip: String,
        units: String,
        appId: String
    ): LiveData<ResultData<Forecast>> {

        return flow {
            emit(ResultData.Loading())
            try {
                emit(dataUseCase.getForeCast(zip, units, appId))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultData.Exception())
            }
        }.asLiveData(Dispatchers.IO)
    }
}