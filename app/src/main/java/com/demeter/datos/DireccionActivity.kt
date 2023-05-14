package com.demeter.datos

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direccion)

        val nombres = findViewById<TextInputEditText>(R.id.inputNombresD)
        val paterno = findViewById<TextInputEditText>(R.id.inputPaternoD)
        val materno = findViewById<TextInputEditText>(R.id.inputMaternoD)
        val distrito = findViewById<AutoCompleteTextView>(R.id.inputDistritoD)
        val telefono = findViewById<TextInputEditText>(R.id.inputTelefonoD)
        val direccion = findViewById<TextInputEditText>(R.id.inputDireccionD)
        val dni = findViewById<TextInputEditText>(R.id.inputDniD)


        mostrarDistrito(distrito)
        recuperarDatos(nombres, paterno, materno, dni, direccion,  telefono)
        findViewById<Button>(R.id.btnGuardarDireccion).setOnClickListener {
            guardarDireccion(nombres, paterno, materno, dni, distrito, direccion, telefono)
        }
    }

    private fun guardarDireccion(
        nombres: TextInputEditText,
        paterno: TextInputEditText,
        materno: TextInputEditText,
        dni: TextInputEditText,
        distrito: AutoCompleteTextView,
        direccion: TextInputEditText,
        telefono: TextInputEditText
    ) {
        FirebaseFirestore.getInstance()
            .collection("usuario").document(FirebaseAuth.getInstance()
                .currentUser?.email.toString()).set(
                hashMapOf(
                    "nombres" to nombres.text.toString(),
                    "aPaterno" to paterno.text.toString(),
                    "aMaterno" to materno.text.toString(),
                    "dni" to dni.text.toString(),
                    "distrito" to distrito.text.toString(),
                    "telefono" to telefono.text.toString(),
                    "direccion" to direccion.text.toString()
                )
            )
        Toast.makeText(
            applicationContext,
            "Datos guardados",
            Toast.LENGTH_SHORT
        ).show()
        Thread.sleep(4000)
        startActivity(Intent(this, InicioActivity::class.java))
    }

    private fun mostrarDistrito(distritoInput: AutoCompleteTextView) {
        val distritos = mutableListOf<String>()
        FirebaseFirestore.getInstance()
            .collection("distrito")
            .get().addOnSuccessListener { result ->
                for(document in result){
                    distritos.add(document.id)
                    Toast.makeText(
                        applicationContext,
                        document.id,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        distritoInput.setAdapter(ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, distritos))
    }

    private fun recuperarDatos(
        nombres: TextInputEditText,
        paterno: TextInputEditText,
        materno: TextInputEditText,
        dni: TextInputEditText,
        direccion: TextInputEditText,
        telefono: TextInputEditText
    ) {
        FirebaseFirestore.getInstance()
            .collection("usuario").document(FirebaseAuth.getInstance()
                .currentUser?.email.toString()).get().addOnSuccessListener {
                nombres.setText(it.get("nombres") as String?)
                paterno.setText(it.get("aPaterno") as String?)
                materno.setText(it.get("aMaterno") as String?)
                dni.setText(it.get("dni") as String?)
                direccion.setText(it.get("direccion") as String?)
                telefono.setText(it.get("telefono") as String?)
            }
    }

}


