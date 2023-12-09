package com.example.sql_crud_with_image

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DisplayActivity : AppCompatActivity(),PersonAdapter.OnItemClickListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DbHelper
    private lateinit var persons: List<Person>
    private lateinit var selectedPerson: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        dbHelper = DbHelper(this)
        persons = dbHelper.getAllPersons()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PersonAdapter(persons,this)
        recyclerView.adapter = adapter


    }
    override fun onItemClick(position: Int) {
        if (position >= 0 && position < persons.size) {

            selectedPerson= persons[position]
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("personId", selectedPerson.id.toString())
            startActivity(intent)
        }
    }


}