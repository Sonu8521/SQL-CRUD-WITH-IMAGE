package com.example.sql_crud_with_image

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class SignUpActivity : AppCompatActivity()  {
    private val editId by lazy { findViewById<EditText>(R.id.editId) }
    private val imageView by lazy { findViewById<ImageView>(R.id.imageView) }
    private val editTextName by lazy { findViewById<EditText>(R.id.editTextName) }
    private val editTextEmail by lazy { findViewById<EditText>(R.id.editTextEmail) }

    private var imageUri: String? = null
    private lateinit var dbHelper: DbHelper


    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    imageUri = it.toString()
                    imageView.setImageURI(uri)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbHelper = DbHelper(this)

        val btnPickImage = findViewById<Button>(R.id.btnPickImage)
        btnPickImage.setOnClickListener {
            pickImage()
        }

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            signUp()
        }
    }
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        getContent.launch(intent)
    }

    private fun signUp() {
        val id:Int=editId.id
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()

        if (name.isNotBlank() && email.isNotBlank() && imageUri != null) {
            val person = Person(id,name, email, imageUri!!)
            dbHelper.insertPerson(person)
            navigateToDisplayData()
        } else {
            // Handle invalid input
        }
    }

    private fun navigateToDisplayData() {
        val intent = Intent(this, DisplayActivity::class.java)
        startActivity(intent)
    }
}
