package app.android.dialogue

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class EditProfileActivity : AppCompatActivity() {
    private lateinit var usernameUpdateField: EditText
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var deleteUserButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        usernameUpdateField = findViewById(R.id.usernameUpdateField)
        val updateButton: Button = findViewById(R.id.button_update)
        deleteUserButton = findViewById(R.id.button_delete_account)
        auth = FirebaseAuth.getInstance()

        updateButton.setOnClickListener {
            updateUsername()
        }

        deleteUserButton.setOnClickListener {
            alertDeleteDialog()
        }
    }


    private fun updateUsername() {
        val newUsername = usernameUpdateField.text.toString().trim()
        val user = FirebaseAuth.getInstance().currentUser

        if (newUsername.isEmpty()) {
            usernameUpdateField.error = "Email cannot be empty"
            return
        } else {
            updateUsername(newUsername, user!!.uid)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUsername(newEmail: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("USERS").child(uid)
        dbRef.child("username").setValue(newEmail)
    }

    private fun deleteUser(){
        val user = FirebaseAuth.getInstance().currentUser

        user!!.delete().addOnCompleteListener {task ->
            if (task.isSuccessful){
                val uid = user.uid // Get the UID before user object is deleted
                dbRef = FirebaseDatabase.getInstance().getReference("USERS").child(uid)
                dbRef.removeValue().addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("EditProfileActivity", "Failed to delete user data from Realtime Database")
                    }
                }
            } else {
                Log.w("EditProfileActivity", "Failed to delete user data from Authentication")
            }
        }
    }

    private fun alertDeleteDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("WARNING")
        builder.setMessage("Do you want to delete your account")
            .setPositiveButton("YES") { dialog, id ->
                deleteUser()
                dialog.dismiss()
            }
            .setNegativeButton("NO") {dialog, id ->
                dialog.dismiss()
            }
        builder.create()
        builder.show()
    }
}
