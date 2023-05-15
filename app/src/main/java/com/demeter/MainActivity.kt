package com.demeter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.demeter.autenticacion.LoginActivity
import com.demeter.inicio.InicioActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verificarSesion()
    }

    private fun verificarSesion(){
        if(FirebaseAuth.getInstance().currentUser != null){
                startActivity(Intent(this, InicioActivity::class.java))
            Toast.makeText(
                applicationContext,
                "Bienvenido!",
                Toast.LENGTH_SHORT
            ).show()
        } else{
                startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}