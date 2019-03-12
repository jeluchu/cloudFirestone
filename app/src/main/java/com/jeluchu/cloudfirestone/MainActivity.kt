package com.jeluchu.cloudfirestone

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jeluchu.cloudfirestone.adapter.NoteRecyclerViewAdapter
import com.jeluchu.cloudfirestone.model.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    private var mAdapter: NoteRecyclerViewAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        firestoreDB = FirebaseFirestore.getInstance()

        loadNotesList()

        firestoreListener = firestoreDB!!.collection("notes")
                .addSnapshotListener(EventListener { documentSnapshots, e ->
                    if (e != null) {
                        Log.e(tag, "Listen failed!", e)
                        return@EventListener
                    }

                    val notesList = mutableListOf<Note>()

                    if (documentSnapshots != null) {
                        for (doc in documentSnapshots) {
                            val note = doc.toObject(Note::class.java)
                            note.id = doc.id
                            notesList.add(note)
                        }
                    }

                    mAdapter = NoteRecyclerViewAdapter(notesList, applicationContext, firestoreDB!!)
                    rvNoteList.adapter = mAdapter
                })
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }


    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadNotesList() {
        firestoreDB!!.collection("notes")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val notesList = mutableListOf<Note>()

                        for (doc in task.result!!) {
                            val note = doc.toObject<Note>(Note::class.java)
                            note.id = doc.id
                            notesList.add(note)
                        }

                        mAdapter = NoteRecyclerViewAdapter(notesList, applicationContext, firestoreDB!!)
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        rvNoteList.layoutManager = mLayoutManager
                        rvNoteList.itemAnimator = DefaultItemAnimator()
                        rvNoteList.adapter = mAdapter
                    } else {
                        Log.d(tag, "Error getting documents: ", task.exception)
                    }
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == R.id.addNote) {
                val intent = Intent(this, NoteActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
