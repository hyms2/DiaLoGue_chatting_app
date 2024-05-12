package app.android.dialogue.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.android.dialogue.ChatActivity
import app.android.dialogue.R
import app.android.dialogue.fragment.chat
import app.android.dialogue.model.User
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(val context: Context, val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =  LayoutInflater.from(context).inflate(R.layout.user_list, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.username_list.text = currentUser.username

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("username", currentUser.username)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val user_photo = itemView.findViewById<CircleImageView>(R.id.users_photo_profile)
        var username_list = itemView.findViewById<TextView>(R.id.user_name_list)
    }
}