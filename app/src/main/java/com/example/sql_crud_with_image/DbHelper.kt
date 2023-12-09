package com.example.sql_crud_with_image

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT NOT NULL, " +
                    "$COLUMN_EMAIL TEXT UNIQUE NOT NULL, " +
                    "$COLUMN_IMAGE_URI TEXT NOT NULL)"
        db!!.execSQL(createTableQuery)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertPerson(person: Person) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, person.name)
        contentValues.put(COLUMN_EMAIL, person.email)
        contentValues.put(COLUMN_IMAGE_URI, person.imageUri)

        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllPersons(): List<Person> {
        val personList = mutableListOf<Person>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                val imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI))
                val id=cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

                val person = Person(id,name, email, imageUri)
                personList.add(person)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return personList
    }
    @SuppressLint("Range")
    fun getPersonById(personId: Int): Person {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(personId.toString()),
            null,
            null,
            null
        )

        var person = Person(-1, "", "", "") // Default values in case person is not found

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            val imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI))

            person = Person(id, name, email, imageUri)
        }

        cursor.close()
        db.close()

        return person
    }
    fun updatePerson(person: Person) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, person.name)
        contentValues.put(COLUMN_EMAIL, person.email)
        contentValues.put(COLUMN_IMAGE_URI, person.imageUri)

        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(person.id.toString()))
        db.close()
    }
    fun deletePerson(personId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(personId.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "PersonDatabase"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "persons"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_IMAGE_URI = "image_uri"
    }

}