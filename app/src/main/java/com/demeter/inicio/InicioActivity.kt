package com.demeter.inicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.demeter.R
import com.demeter.datos.DireccionActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        findViewById<Button>(R.id.btnInputDireccion).setOnClickListener {
            startActivity(Intent(this, DireccionActivity::class.java))
        }

        findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
    }
}