package edu.udb.investigacion02dsm.ejercicio2_jsonplaceholder

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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

class CreatePostActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etBody: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var btnCreatePost: Button
    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<PostResponse>()
    private var selectedCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creacion_post)

        etTitle = findViewById(R.id.etTitle)
        etBody = findViewById(R.id.etBody)
        btnCreatePost = findViewById(R.id.btnCreatePost)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        recyclerViewPosts = findViewById(R.id.recyclerView)

        val categories = listOf("Tecnología", "Ciencia", "Salud", "Educación","UDB", "Otro")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCategory = categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnCreatePost.setOnClickListener { createPost() }

        // Configurar el RecyclerView
        postAdapter = PostAdapter(postList)
        recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        recyclerViewPosts.adapter = postAdapter
    }

    private fun createPost() {
        val title = etTitle.text.toString().trim()
        val body = etBody.text.toString().trim()

        if (title.isEmpty() || body.isEmpty() || selectedCategory.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val newPost = PostRequest(userId = 1, title = title, body = body, category = selectedCategory)

        getPostRetrofit().create(ApiService::class.java).createPost(newPost).enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    val postResponse = response.body()
                    Toast.makeText(this@CreatePostActivity, "Post creado con ID: ${postResponse?.id}", Toast.LENGTH_SHORT).show()

                    // Agregar el nuevo post
                    postResponse?.let {
                        postList.add(it)
                        postAdapter.notifyItemInserted(postList.size - 1)
                    }

                    clearFields()
                } else {
                    Toast.makeText(this@CreatePostActivity, "Error al crear post", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Toast.makeText(this@CreatePostActivity, "Error en la conexión al crear post", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun clearFields() {
        etTitle.text.clear()
        etBody.text.clear()
        spinnerCategory.setSelection(0)
    }

    private fun getPostRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
