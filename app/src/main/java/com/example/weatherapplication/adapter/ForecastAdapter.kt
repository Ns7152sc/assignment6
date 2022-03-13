package com.example.weatherapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ForecastItemBinding
import com.example.weatherapplication.model.ForecastList
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(private val list: List<ForecastList>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ForecastItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            val model = list[position]
            binding.tvSunrise.text = "Sunrise: ${
                SimpleDateFormat(
                    "hh:mm a",
                    Locale.ENGLISH
                ).format(Date(model.sunrise * 1000))
            }"
            binding.tvSunset.text = "Sunset: ${
                SimpleDateFormat(
                    "hh:mm a",
                    Locale.ENGLISH
                ).format(Date(model.sunset * 1000))
            }"
            binding.tvTemperature.text =
                "${(model.temp?.day)?.toInt()}${context.getString(R.string.degree)}"
            binding.tvTemperatureHigh.text =
                "High ${(model.temp?.max)?.toInt()}${context.getString(R.string.degree)}"
            binding.tvTemperatureLow.text =
                "Low ${(model.temp?.min)?.toInt()}${context.getString(R.string.degree)}"
            binding.tvDate.text =
                SimpleDateFormat("MMM dd", Locale.ENGLISH).format(Date(model.dt * 1000))
            Glide.with(context)
                .load("http://openweathermap.org/img/wn/${model.weather?.get(0)?.icon}@2x.png")
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.forecast_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = list.size


}