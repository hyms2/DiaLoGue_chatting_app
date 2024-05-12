package app.android.dialogue

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.fragment.app.Fragment
import app.android.dialogue.databinding.ActivityHomeBinding
import app.android.dialogue.fragment.chat
import app.android.dialogue.fragment.group
import app.android.dialogue.fragment.setting
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
//    private lateinit var auth: FirebaseAuth
//    val user = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(chat())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.chat -> replaceFragment(chat())
//                R.id.group -> replaceFragment(group())
                R.id.setting -> replaceFragment(setting())

                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}