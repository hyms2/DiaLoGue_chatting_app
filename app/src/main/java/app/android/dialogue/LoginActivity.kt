package app.android.dialogue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailField = findViewById<EditText>(R.id.emailLoginField)
        val passwordField = findViewById<EditText>(R.id.passwordLoginField)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        fun loginUser(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "The email or password is wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val buttonClick = findViewById<Button>(R.id.button_login)
        buttonClick.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "The email or password field is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        val linkClick = findViewById<TextView>(R.id.signup_link)
        linkClick.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}