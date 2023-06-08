package com.example.askelmittari

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val loginText: TextView = findViewById(R.id.gologinnow)
        loginText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.btnregister)

        registerButton.setOnClickListener {
            performSingUp()
        }

        //haetaan sähköposti ja salasana käyttäjältä



    }

    private fun performSingUp() {
        val email = findViewById<EditText>(R.id.emailrekisteröinti)
        val password = findViewById<EditText>(R.id.salasanarekisteröinti)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Täytä tekstikentät", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    // Rekisteröinti onnistui

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Rekisteröinti onnistui",
                        Toast.LENGTH_SHORT)
                        .show()

                } else {
                    // Rekisteröinti epäonnistui

                    Toast.makeText(baseContext, "Rekisteröinti epäonnistui",
                        Toast.LENGTH_SHORT)
                        .show()

                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error occured ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }



    }
}