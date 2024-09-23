package edu.udb.investigacion02dsm

import edu.udb.investigacion02dsm.ejercicio1_clima.WeatherResponse
import edu.udb.investigacion02dsm.ejercicio2_jsonplaceholder.PostRequest
import edu.udb.investigacion02dsm.ejercicio2_jsonplaceholder.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "es"
    ): Call<WeatherResponse>

    @GET("posts")
    fun getPosts(): Call<List<PostResponse>>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<PostResponse>

    @POST("posts")
    fun createPost(@Body postRequest: PostRequest): Call<PostResponse>
}

