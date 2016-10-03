package com.stzemo.kotlinfirst.adapters.weatherAdapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stzemo.kotlinfirst.R
import java.util.*

class WeatherAdapter(listWeatherDays: ArrayList<String>) : RecyclerView.Adapter<WeatherViewHolder>() {

    private var list: ArrayList<String>

    init {
        list = listWeatherDays
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val input: String? = list.get(holder.adapterPosition)
        holder.tvName.text = input
    }

    override fun getItemCount(): Int {
        return list.size
    }
}