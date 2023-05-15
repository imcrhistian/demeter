package com.demeter.autenticacion

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.demeter.R
import com.demeter.inicio.InicioActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputCorreo = findViewById<TextInputEditText>(R.id.inputCorreoE)
        val inputContrasena = findViewById<TextInputEditText>(R.id.inputContrasena)


        //[Valida los campos y luego inicia sesión]
        findViewById<Button>(R.id.btnIniciarSesion).setOnClickListener {
            ocultarTeclado()
            validaciones(inputCorreo, inputContrasena)
        }


        findViewById<TextView>(R.id.textView3).setOnClickListener{
            startActivity(Intent(this, RegistroActivity::class.java))
        }

    }
    private fun validaciones(inputCorreo: TextInputEditText, inputContrasena: TextInputEditText) {
        if(inputCorreo.text.toString().trim().isNotEmpty() && inputContrasena.text.toString().trim().isNotEmpty()){
            iniciarSesion(inputCorreo,inputContrasena)
        }
        else{
            Toast.makeText(
                applicationContext,
                "Campos vacios",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun iniciarSesion(inputCorreo: TextInputEditText, inputContrasena: TextInputEditText) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            inputCorreo.text.toString(),
            inputContrasena.text.toString()
        ).addOnCompleteListener{authTask ->
            if (authTask.isSuccessful){
                    FirebaseFirestore.getInstance().collection("usuario")
                        .document(FirebaseAuth.getInstance().currentUser?.email!!).get()
                        .addOnSuccessListener { usuarioDoc ->
                    val nombre = usuarioDoc.getString("nombres")
                    Toast.makeText(
                        applicationContext,
                        "Bienvenido $nombre!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                startActivity(Intent(this, InicioActivity::class.java))
            } else{
                Toast.makeText(
                    applicationContext,
                    "No se pudo iniciar sesión",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun ocultarTeclado(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}