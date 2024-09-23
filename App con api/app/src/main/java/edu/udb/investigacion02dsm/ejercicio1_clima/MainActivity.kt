package edu.udb.investigacion02dsm.ejercicio1_clima

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.udb.investigacion02dsm.ApiService
import edu.udb.investigacion02dsm.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherAdapter
    private val weatherList = mutableListOf<WeatherResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        // Configurar el SearchView para buscar ciudades
        binding.svPais.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchByName(it)
                    binding.svPais.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        // Configurar botón de refrescar
        binding.btnRefresh.setOnClickListener {
            val city = binding.svPais.query.toString()
            if (city.isNotEmpty()) {
                searchByName(city)
            } else {
                Toast.makeText(this, "Por favor, ingrese una ciudad", Toast.LENGTH_SHORT).show()
            }
        }

    }
    // Función para inicializar el RecyclerView
    private fun initRecyclerView() {
        weatherAdapter = WeatherAdapter(weatherList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = weatherAdapter
    }

    // Función para configurar Retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchByName(city: String) {
        val apiKey = "4303a31ee6a45d8b573977a3d38b49be"

        getRetrofit().create(ApiService::class.java).getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        weatherList.clear()
                        weatherList.add(weatherResponse)
                        weatherAdapter.notifyDataSetChanged()

                        // Mostrar un mensaje de actualización solo si hay datos
                        Toast.makeText(this@MainActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    } ?: run {
                        // Si no hay cuerpo en la respuesta, muestra un mensaje
                        Toast.makeText(this@MainActivity, "No se encontraron datos para la ciudad.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Ciudad no encontrada. Por favor verifica el nombre.", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("MainActivity", "Failure: ${t.message}")
                Toast.makeText(this@MainActivity, "Error en la conexión. Por favor intenta de nuevo.", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
