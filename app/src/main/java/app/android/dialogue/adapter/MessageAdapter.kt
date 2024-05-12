package app.android.dialogue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import app.android.dialogue.R
import app.android.dialogue.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_received = 1
    val item_sent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1){
            val view: View =  LayoutInflater.from(context).inflate(R.layout.received_message, parent, false)
            return receivedViewHolder(view)
        } else {
            val view: View =  LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
            return sentViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val newMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(newMessage.senderId)) {
            return item_sent
        } else {
            return item_received
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newMessage = messageList[position]

        if (holder.javaClass == sentViewHolder::class.java) {
            val viewHolder = holder as sentViewHolder
            holder.sentMessage.text = newMessage.message
        } else {
            val viewHolder = holder as receivedViewHolder
            holder.receivedMessage.text = newMessage.message
        }
    }

    class sentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_message)
    }

    class receivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedMessage = itemView.findViewById<TextView>(R.id.received_message)
    }
}