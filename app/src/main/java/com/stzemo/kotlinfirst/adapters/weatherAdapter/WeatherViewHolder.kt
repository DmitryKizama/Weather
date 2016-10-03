package com.stzemo.kotlinfirst.adapters.weatherAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.stzemo.kotlinfirst.R

class WeatherViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var layoutWeatherItem: LinearLayout
    var tvName: TextView
    var tvCode: TextView

    init {
        layoutWeatherItem = itemView?.findViewById(R.id.weather_item_layout) as LinearLayout
        tvName = itemView?.findViewById(R.id.tvWeatherName) as TextView
        tvCode = itemView?.findViewById(R.id.tvWeatherCode) as TextView
    }
}