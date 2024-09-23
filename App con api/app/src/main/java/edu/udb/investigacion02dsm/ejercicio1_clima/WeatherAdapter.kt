package edu.udb.investigacion02dsm.ejercicio1_clima

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.udb.investigacion02dsm.R
import edu.udb.investigacion02dsm.databinding.ItemWeatherBinding

class WeatherAdapter(private val weatherList: MutableList<WeatherResponse>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherResponse: WeatherResponse) {
            //Cargamos los datos
            binding.textViewTemperature.text = "${weatherResponse.main.temp} °C"
            binding.textViewDescription.text = weatherResponse.weather[0].description
            binding.textViewHumidity.text = "Humedad: ${weatherResponse.main.humidity}%"
            binding.textViewPressure.text = "Presion viento: ${weatherResponse.main.pressure} hPa"
            binding.textViewCity.text = weatherResponse.name


            // Cargar el ícono del clima usando Picasso
            val iconCode = weatherResponse.weather[0].icon
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"

            // Cargar la imagen en el ImageView
            Picasso.get()
                .load(iconUrl)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageViewWeather)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }
}
