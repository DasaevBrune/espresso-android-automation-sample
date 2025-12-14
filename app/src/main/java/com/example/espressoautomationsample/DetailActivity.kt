package com.example.espressoautomationsample

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM_TITLE = "extra_item_title"
        private const val PREFS_NAME = "item_prefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra(EXTRA_ITEM_TITLE) ?: "Unknown Item"
        
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val cbFavorite = findViewById<CheckBox>(R.id.cbFavorite)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnSave = findViewById<Button>(R.id.btnSave)

        tvTitle.text = title

        // Load saved state
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFavorite = prefs.getBoolean("fav_$title", false)
        val notes = prefs.getString("note_$title", "")

        cbFavorite.isChecked = isFavorite
        etNotes.setText(notes)

        btnSave.setOnClickListener {
            prefs.edit().apply {
                putBoolean("fav_$title", cbFavorite.isChecked)
                putString("note_$title", etNotes.text.toString())
                apply()
            }
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
