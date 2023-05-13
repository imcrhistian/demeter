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

        val nombresInput = findViewById<TextInputEditText>(R.id.inputNombres)
        val apellidosInput = findViewById<TextInputEditText>(R.id.inputApellidos)
        val distritoInput = findViewById<AutoCompleteTextView>(R.id.inputDistrito)
        val telefonoInput = findViewById<TextInputEditText>(R.id.inputTelefono)
        val direccionInput = findViewById<TextInputEditText>(R.id.inputDireccion)
        val dniInput = findViewById<TextInputEditText>(R.id.inputDNI)


        mostrarDistrito(distritoInput)
        recuperarDatos(nombresInput, apellidosInput, telefonoInput, direccionInput, dniInput)
        findViewById<Button>(R.id.btnGuardarDireccion).setOnClickListener {
            guardarDireccion(nombresInput, apellidosInput, distritoInput, telefonoInput, direccionInput, dniInput)
        }
    }

    private fun guardarDireccion(
        nombresInput: TextInputEditText,
        apellidosInput: TextInputEditText,
        distritoInput: AutoCompleteTextView,
        telefonoInput: TextInputEditText,
        direccionInput: TextInputEditText,
        dniInput: TextInputEditText
    ) {
        FirebaseFirestore.getInstance()
            .collection("usuario").document(FirebaseAuth.getInstance()
                .currentUser?.email.toString()).set(
                hashMapOf(
                    "nombres" to nombresInput.text.toString(),
                    "apellidos" to apellidosInput.text.toString(),
                    "distrito" to distritoInput.text.toString(),
                    "telefono" to telefonoInput.text.toString(),
                    "direccion" to direccionInput.text.toString(),
                    "dni" to dniInput.text.toString()
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
        nombresInput: TextInputEditText,
        apellidosInput: TextInputEditText,
        telefonoInput: TextInputEditText,
        direccionInput: TextInputEditText,
        dniInput: TextInputEditText
    ){
        FirebaseFirestore.getInstance()
            .collection("usuario").document(FirebaseAuth.getInstance()
                .currentUser?.email.toString()).get().addOnSuccessListener {
                nombresInput.setText(it.get("nombres") as String?)
                apellidosInput.setText(it.get("apellidos") as String?)
                dniInput.setText(it.get("dni") as String?)
                direccionInput.setText(it.get("direccion") as String?)
                telefonoInput.setText(it.get("telefono") as String?)
            }
    }

}


