package com.example.notes

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INotesRVAdapter {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            Log.d("Testing", "Testing")
            submitData()
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        viewModel.allNotes.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }
    }

    override fun onItemClicked(note: Note) {
//        if (note.text.equals("anupam chutiya", ignoreCase = true)) {
//            val dialog = AlertDialog.Builder(this)
//                .setTitle("Universal Truth hai bskd")
//                .setMessage("Ye baaten chupai nahi jaa skti hain.")
//                .create()
//            dialog.apply {
//                val view = LinearLayout(this@MainActivity).apply {
//                    addView(TextView(this@MainActivity).apply {
//                        setText("Anupam Randi")
//                    })
                    /*layoutParams = LinearLayout.LayoutParams(
                        this@MainActivity,

                    ).apply {
                        width = LinearLayout.LayoutParams.WRAP_CONTENT
                        height = LinearLayout.LayoutParams.WRAP_CONTENT
                    }*/
//                }
//                setView(view)
//            }
//            dialog.show()
//            return
//        }
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text}Deleted", Toast.LENGTH_LONG).show()
    }

    fun submitData() {
        val input = binding.input
        val noteText = input.text.toString().trim()
        if (noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this, "${noteText}Inserted", Toast.LENGTH_LONG).show()
        }
    }


}