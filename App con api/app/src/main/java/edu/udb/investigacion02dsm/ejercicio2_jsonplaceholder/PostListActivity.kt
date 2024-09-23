package edu.udb.investigacion02dsm.ejercicio2_jsonplaceholder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.udb.investigacion02dsm.ApiService
import edu.udb.investigacion02dsm.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<PostResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creacion_post)

        recyclerView = findViewById(R.id.recyclerView)
        postAdapter = PostAdapter(postList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = postAdapter

        fetchPosts()
    }

    private fun fetchPosts() {
        getPostRetrofit().create(ApiService::class.java).getPosts().enqueue(object : Callback<List<PostResponse>> {
            override fun onResponse(call: Call<List<PostResponse>>, response: Response<List<PostResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        postList.clear()
                        postList.addAll(posts)
                        postAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@PostListActivity, "Error al obtener los posts", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                Toast.makeText(this@PostListActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getPostRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
