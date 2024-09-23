package edu.udb.investigacion02dsm.ejercicio1_clima

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.udb.investigacion02dsm.R

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewCity: TextView = itemView.findViewById(R.id.textViewCity)
    private val textViewTemperature: TextView = itemView.findViewById(R.id.textViewTemperature)
    private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    private val textViewHumidity: TextView = itemView.findViewById(R.id.textViewHumidity)
    private val textViewPressure: TextView = itemView.findViewById(R.id.textViewPressure)
    private val imageViewWeather: ImageView = itemView.findViewById(R.id.imageViewWeather)

    fun bind(weatherResponse: WeatherResponse) {
        textViewTemperature.text = "${weatherResponse.main.temp} °C"
        textViewDescription.text = weatherResponse.weather[0].description
        textViewHumidity.text = "Humedad: ${weatherResponse.main.humidity}%"
        textViewPressure.text = "Presion de viento: ${weatherResponse.main.pressure} hPa"

    }
}
