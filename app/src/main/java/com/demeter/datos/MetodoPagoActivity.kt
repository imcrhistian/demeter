package com.demeter.datos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.demeter.R
import com.demeter.inicio.InicioActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class MetodoPagoActivity : AppCompatActivity() {

    private lateinit var nombre: TextInputEditText
    private lateinit var numero: TextInputEditText
    private lateinit var expiracion: TextInputEditText
    private lateinit var cvv: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metodo_pago)

        nombre = findViewById(R.id.inputNombreTarjeta)
        numero = findViewById(R.id.inputNumeroTarjeta)
        expiracion = findViewById(R.id.inputExpiracionTarjeta)
        cvv = findViewById(R.id.inputCVV)



        recuperarDatos()
        tarjeta(nombre, findViewById(R.id.txtNombre))
        tarjeta(numero, findViewById(R.id.txtNumero))
        tarjeta(expiracion, findViewById(R.id.txtExpiracion))
        tarjeta(cvv, findViewById(R.id.txtCVV))
        findViewById<Button>(R.id.btnGuardarTarjeta).setOnClickListener {
            guardarTarjeta()
        }
    }

    private fun guardarTarjeta() {
        FirebaseFirestore.getInstance().collection("metodoPago")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .set(
                hashMapOf(
                    "nombre" to nombre.text.toString(),
                    "numero" to numero.text.toString(),
                    "expiracion" to expiracion.text.toString(),
                    "cvv" to cvv.text.toString()
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

    private fun recuperarDatos() {
        FirebaseFirestore.getInstance()
            .collection("metodoPago")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                nombre.setText(document.getString("nombre")?: "")
                numero.setText(document.getString("numero")?: "")
                expiracion.setText(document.getString("expiracion")?: "")
                cvv.setText(document.getString("cvv")?: "")
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Error al recuperar los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
    private fun tarjeta(textInputEditText: TextInputEditText, textView: TextView) {
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length == 16){
                    textView.text = s.toString().chunked(4).joinToString(" ")
                }else{
                    textView.text = s.toString().toUpperCase()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

}