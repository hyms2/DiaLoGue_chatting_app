package app.android.dialogue

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseChat {
    val auth: FirebaseAuth
    val dbRef: DatabaseReference

    init {
        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference
    }

    fun getUsers(listener: ValueEventListener) {
        dbRef.child("user").addValueEventListener(listener)
    }
}