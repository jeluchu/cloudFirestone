package com.jeluchu.cloudfirestone

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var db: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance().document("users/data")

        val store = findViewById<View>(R.id.button) as Button

        store.setOnClickListener {
            store ()
        }

    }

    private fun store () {

        val names = name.text.toString().trim()
        val users = user.text.toString().trim()
        val years = years.text.toString().trim()

        if (!names.isEmpty() && !users.isEmpty() && !years.isEmpty()) {
            try {
                val items = HashMap<String, Any>()
                items["users"] = users
                items["years"] = years
                db.collection(names).document("profile").set(items).addOnSuccessListener {
                    Toast.makeText(this, "Subido con éxito", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "Rellena los campos", Toast.LENGTH_LONG).show()
        }

    }
}
