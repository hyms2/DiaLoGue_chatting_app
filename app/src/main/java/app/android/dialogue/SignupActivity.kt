package app.android.dialogue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import app.android.dialogue.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val emailField = findViewById<EditText>(R.id.emailSignupField)
        val usernamField = findViewById<EditText>(R.id.usernameSignupField)
        val passwordField = findViewById<EditText>(R.id.passwordSignupField)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("USERS")

        fun registerUser(email: String, username: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        addUserToDatabase(username, email, auth.currentUser!!.uid)
                    } else {
                        val errorMessage = (task.exception as? FirebaseAuthException)?.message?: "Failed to make an account!"

                        val weakPasswordError = "The given password is invalid. \\[ (.+)]".toRegex().find(errorMessage)?.groupValues?.getOrNull(1)
                        val finalErrorMessage = weakPasswordError ?: errorMessage

                        Log.w("Registration", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, finalErrorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun inputData() {
            val email = emailField.text.toString().trim()
            val username = usernamField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                registerUser(email, username, password)
            } else {
                Toast.makeText(this, "The email or password field is empty!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val buttonClick = findViewById<Button>(R.id.button_signup)
        buttonClick.setOnClickListener {
            inputData()
        }

        val textClick = findViewById<TextView>(R.id.login_link)
        textClick.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun addUserToDatabase(name: String, email: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference()

        dbRef.child("USERS").child(uid).setValue(User(name,email,uid))
    }
}