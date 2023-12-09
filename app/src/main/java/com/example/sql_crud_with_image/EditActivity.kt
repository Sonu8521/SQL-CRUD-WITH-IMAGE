package com.example.sql_crud_with_image

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide

class EditActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private lateinit var currentPersonId: String
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var imageView: ImageView
    private var imageUri: String? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    imageUri = it.toString()
                    Glide.with(this).load(uri).into(imageView)
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        dbHelper = DbHelper(this)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        imageView = findViewById(R.id.imageView)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            updatePerson()
        }

        val btnDelete = findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            deletePerson()
        }

        val btnPickImage = findViewById<Button>(R.id.btnPickImage)
        btnPickImage.setOnClickListener {
            pickImage()
        }

        // Retrieve the person ID from the intent
        currentPersonId = intent.getStringExtra("personId") ?: ""

        // Populate the EditText fields with existing data
        val currentPerson = dbHelper.getPersonById(currentPersonId.toInt())
        etName.setText(currentPerson.name)
        etEmail.setText(currentPerson.email)

        // Load image using Picasso or any other image loading library
        // For simplicity, we use setImageURI, you can use your preferred image loading library here
        Glide.with(this).load(currentPerson.imageUri).into(imageView)

    }
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun updatePerson() {
        val updatedName = etName.text.toString()
        val updatedEmail = etEmail.text.toString()

        val updatedPerson = Person(
            id= currentPersonId.toInt(),
            name=updatedName,
            email = updatedEmail,
            imageUri=imageUri ?: "")

        dbHelper.updatePerson(updatedPerson)

        // Return to DisplayActivity after updating
        val intent = Intent(this, DisplayActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun deletePerson() {
        dbHelper.deletePerson(currentPersonId.toInt())

        // Return to DisplayActivity after deleting
        val intent = Intent(this, DisplayActivity::class.java)
        startActivity(intent)
        finish()
    }

}
