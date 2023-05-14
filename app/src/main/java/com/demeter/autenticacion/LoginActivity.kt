package com.demeter.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.demeter.R
import com.demeter.inicio.InicioActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputCorreo = findViewById<TextInputEditText>(R.id.inputCorreoE)
        val inputContrasena = findViewById<TextInputEditText>(R.id.inputContrasena)

        verificarSesion()

        //[Valida los campos y luego inicia sesión]
        findViewById<Button>(R.id.btnIniciarSesion).setOnClickListener {
            validaciones(inputCorreo, inputContrasena)
        }


        findViewById<TextView>(R.id.textView3).setOnClickListener{
            startActivity(Intent(this, RegistroActivity::class.java))
        }


    }
    private fun validaciones(inputCorreo: TextInputEditText, inputContrasena: TextInputEditText) {
        if(inputCorreo.text!!.isNotEmpty() && inputContrasena.text!!.isNotEmpty()){
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
        ).addOnCompleteListener{
            if (it.isSuccessful){
                startActivity(Intent(this, InicioActivity::class.java))
                Toast.makeText(
                    applicationContext,
                    "Bienvenido",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                Toast.makeText(
                    applicationContext,
                    "No se pudo iniciar sesión",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun verificarSesion(){
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this, InicioActivity::class.java))
            Toast.makeText(
                applicationContext,
                "Bienvenido",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}