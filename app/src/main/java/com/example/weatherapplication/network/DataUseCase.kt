package com.example.weatherapplication.network

import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.model.Forecast
import javax.inject.Inject

class DataUseCase @Inject constructor(
    private val dataRepository: NetworkRepository
) {
    suspend fun getCurrentConditions(
        zip: String,
        units: String,
        appId: String
    ): ResultData<CurrentConditions> {
        val repositoriesModel = dataRepository.getCurrentConditions(zip, units, appId)

        val resultData = when (repositoriesModel != null) {
            true -> {
                ResultData.Success(repositoriesModel)
            }
            else -> {
                ResultData.Failed()
            }
        }
        return resultData
    }

    suspend fun getForeCast(
        zip: String,
        units: String,
        appId: String
    ): ResultData<Forecast> {
        val repositoriesModel = dataRepository.getForeCast(zip, units, appId)

        val resultData = when (repositoriesModel != null) {
            true -> {
                ResultData.Success(repositoriesModel)
            }
            else -> {
                ResultData.Failed()
            }
        }
        return resultData
    }
}