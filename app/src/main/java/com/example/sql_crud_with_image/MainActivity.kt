package com.example.sql_crud_with_image

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = android.os.Handler()
        handler.postDelayed({
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        },1200)
    }
}