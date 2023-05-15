package com.demeter.datos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demeter.R
import com.demeter.inicio.InicioActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DireccionActivity : AppCompatActivity() {

    private lateinit var nombres: TextInputEditText
    private lateinit var paterno: TextInputEditText
    private lateinit var materno: TextInputEditText
    private lateinit var distrito: AutoCompleteTextView
    private lateinit var telefono: TextInputEditText
    private lateinit var direccion: TextInputEditText
    private lateinit var dni: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direccion)

        nombres = findViewById(R.id.inputNombresD)
        paterno = findViewById(R.id.inputPaternoD)
        materno = findViewById(R.id.inputMaternoD)
        distrito = findViewById(R.id.inputDistritoD)
        telefono = findViewById(R.id.inputTelefonoD)
        direccion = findViewById(R.id.inputDireccionD)
        dni = findViewById(R.id.inputDniD)

        arrayDistrito()
        recuperarDatos()
        findViewById<Button>(R.id.btnGuardarDireccion).setOnClickListener {
            guardarDireccion()
        }
    }

    private fun guardarDireccion() {
        FirebaseFirestore.getInstance().collection("usuario")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .set(
                hashMapOf(
                "nombres" to nombres.text.toString(),
                "aPaterno" to paterno.text.toString(),
                "aMaterno" to materno.text.toString(),
                "dni" to dni.text.toString(),
                "distrito" to distrito.text.toString(),
                "telefono" to telefono.text.toString(),
                "direccion" to direccion.text.toString()
            ))
            .addOnSuccessListener {
                Toast.makeText(
                    applicationContext,
                    "Datos guardados",
                    Toast.LENGTH_SHORT
                ).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, InicioActivity::class.java))
                    finish()
                }, 2000)
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Error al guardar los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun mostrarDistrito(distritos: MutableList<String>) {
        distrito.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                distritos
            )
        )
    }

    private fun arrayDistrito() {
        val distritos = mutableListOf<String>()
        FirebaseFirestore.getInstance().collection("distrito")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    distritos.add(document.id)
                }
                mostrarDistrito(distritos)
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Error al obtener los distritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun recuperarDatos() {
        FirebaseFirestore.getInstance()
            .collection("usuario")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString()).get()
            .addOnSuccessListener { document ->
                nombres.setText(document.getString("nombres"))
                paterno.setText(document.getString("aPaterno"))
                materno.setText(document.getString("aMaterno"))
                dni.setText(document.getString("dni"))
                direccion.setText(document.getString("direccion"))
                telefono.setText(document.getString("telefono"))
                distrito.setText(document.getString("distrito"))
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Error al recuperar los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

