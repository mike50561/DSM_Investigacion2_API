package edu.udb.investigacion02dsm


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import edu.udb.investigacion02dsm.ejercicio1_clima.MainActivity
import edu.udb.investigacion02dsm.ejercicio2_jsonplaceholder.CreatePostActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        val btnGoToMain = findViewById<Button>(R.id.btnGo)
        val btnGoToCreatePost = findViewById<Button>(R.id.btnGoToCreatePost)


        btnGoToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnGoToCreatePost.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
    }
}
