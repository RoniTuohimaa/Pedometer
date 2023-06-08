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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val registertext: TextView = findViewById(R.id.goregister)

        registertext.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.btnlogin)

        loginButton.setOnClickListener {
            performLogin()
        }
    }


    private fun performLogin() {

        val email: EditText = findViewById(R.id.emailkirjatuminen)
        val password: EditText = findViewById(R.id.salasanakirjautuminen)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Täytä tekstikentät", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Kirjautuminen onnistui

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Kirjautuminen onnistui",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                } else {
                    // Rekisteröinti epäonnistui

                    Toast.makeText(
                        baseContext, "Kirjautuminen epäonnistui",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occured ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }


    }
    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already logged in
            // Proceed to the main activity or perform other actions
            navigateToMain()
        } else {
            // User is not logged in
            // Redirect to the login activity


        }
    }
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }



}





