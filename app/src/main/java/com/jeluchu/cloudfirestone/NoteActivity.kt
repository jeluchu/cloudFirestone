package com.jeluchu.cloudfirestone

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.firestore.FirebaseFirestore
import com.jeluchu.cloudfirestone.model.Note

import kotlinx.android.synthetic.main.activity_note.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NoteActivity : AppCompatActivity() {

    private val tag = "AddNoteActivity"

    private var firestoreDB: FirebaseFirestore? = null
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        firestoreDB = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateNoteId")

            edtTitle.setText(bundle.getString("UpdateNoteTitle"))
            edtContent.setText(bundle.getString("UpdateNoteContent"))
        }

        btAdd.setOnClickListener {
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()

            if (title.isNotEmpty()) {
                if (id.isNotEmpty()) {
                    updateNote(id, title, content)
                } else {
                    addNote(title, content)
                }
            }

            finish()
        }
    }

    private fun updateNote(id: String, title: String, content: String) {
        val note = Note(id, title, content).toMap()

        firestoreDB!!.collection("notes")
                .document(id)
                .set(note)
                .addOnSuccessListener {
                    Log.e(tag, "Note document update successful!")
                    Toast.makeText(applicationContext, "¡Datos actualizados!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e(tag, "Error adding Note document", e)
                    Toast.makeText(applicationContext, "Los datos no han sido actualizados", Toast.LENGTH_SHORT).show()
                }
    }

    private fun addNote(title: String, content: String) {
        val note = Note(title, content).toMap()

        firestoreDB!!.collection("notes")
                .add(note)
                .addOnSuccessListener { documentReference ->
                    Log.e(tag, "DocumentSnapshot written with ID: " + documentReference.id)
                    Toast.makeText(applicationContext, "¡Datos añadidos!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e(tag, "Error adding Note document", e)
                    Toast.makeText(applicationContext, "Los datos no han sido añadidos", Toast.LENGTH_SHORT).show()
                }
    }
}
