package com.demeter.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.demeter.MainActivity
import com.demeter.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val inputCorreo = findViewById<TextInputEditText>(R.id.inputCorreoR)
        val inputContrasena = findViewById<TextInputEditText>(R.id.inputContrasenaR)

        findViewById<Button>(R.id.btnRegistrar).setOnClickListener {
            validaciones(inputCorreo, inputContrasena)
        }

        findViewById<TextView>(R.id.textView2).setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun validaciones(inputCorreo: TextInputEditText, inputContrasena: TextInputEditText) {
        if(inputCorreo.text!!.isNotEmpty() && inputContrasena.text!!.isNotEmpty()){
            registrarUsuario(inputCorreo,inputContrasena)
        }
        else{
            Toast.makeText(
                applicationContext,
                "Campos vacios",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registrarUsuario(inputCorreo: TextInputEditText, inputContrasena: TextInputEditText) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            inputCorreo.text.toString(),
            inputContrasena.text.toString()
        ).addOnCompleteListener{
            if (it.isSuccessful){
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(
                    applicationContext,
                    "Perfil creado",
                    Toast.LENGTH_SHORT
                ).show()
                FirebaseFirestore.getInstance()
                    .collection("usuario").document(FirebaseAuth.getInstance()
                        .currentUser?.email.toString()).set(
                        hashMapOf(
                            "" to ""
                        )
                    )
                correoVerificacion()
            } else{
                Toast.makeText(
                    applicationContext,
                    "No se pudo registrar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun correoVerificacion(){
        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
    }

}