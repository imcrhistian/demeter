package com.demeter.inicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.demeter.R
import com.demeter.autenticacion.LoginActivity
import com.demeter.datos.DireccionActivity
import com.demeter.datos.MetodoPagoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        findViewById<Button>(R.id.btnInputTarjeta).setOnClickListener {
            startActivity(Intent(this, MetodoPagoActivity::class.java))
        }

        findViewById<Button>(R.id.btnInputDireccion).setOnClickListener {
            startActivity(Intent(this, DireccionActivity::class.java))
        }

        findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.btnEliminarCuenta).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            eliminarCuenta()
        }
    }

    private fun eliminarCuenta(){
        FirebaseFirestore.getInstance().collection("usuario")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .delete()
        FirebaseAuth.getInstance().currentUser?.delete()
    }
}