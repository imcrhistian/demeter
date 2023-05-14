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

        val correo = findViewById<TextInputEditText>(R.id.inputCorreoR)
        val contrasena = findViewById<TextInputEditText>(R.id.inputContrasenaR)
        val nombres = findViewById<TextInputEditText>(R.id.inputNombresR)
        val paterno = findViewById<TextInputEditText>(R.id.inputPaternoR)
        val materno = findViewById<TextInputEditText>(R.id.inputMaternoR)
        val dni = findViewById<TextInputEditText>(R.id.inputDniR)


        findViewById<Button>(R.id.btnRegistrar).setOnClickListener {
            validaciones(correo, contrasena, nombres, paterno, materno, dni)
        }

        findViewById<TextView>(R.id.textView2).setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun validaciones(
        correo: TextInputEditText,
        contrasena: TextInputEditText,
        nombres: TextInputEditText,
        paterno: TextInputEditText,
        materno: TextInputEditText,
        dni: TextInputEditText
    ) {
        if(correo.text!!.isNotEmpty() && contrasena.text!!.isNotEmpty() &&
            nombres.text!!.isNotEmpty() && materno.text!!.isNotEmpty() &&
            paterno.text!!.isNotEmpty() && dni.text!!.isNotEmpty()){
            registrarUsuario(correo,contrasena, nombres, paterno, materno, dni)
        }
        else{
            Toast.makeText(
                applicationContext,
                "Campos vacios",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registrarUsuario(
        correo: TextInputEditText,
        contrasena: TextInputEditText,
        nombres: TextInputEditText,
        paterno: TextInputEditText,
        materno: TextInputEditText,
        dni: TextInputEditText
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo.text.toString(), contrasena.text.toString()).addOnCompleteListener{
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
                            "nombres" to nombres.text.toString(),
                            "aPaterno" to paterno.text.toString(),
                            "aMaterno" to materno.text.toString(),
                            "dni" to dni.text.toString()
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