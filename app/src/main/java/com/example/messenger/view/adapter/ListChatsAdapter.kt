package com.example.messenger.view.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.messenger.R
import com.example.messenger.SharedData
import com.example.messenger.databinding.ItemListChatBinding
import com.example.messenger.interaction.fragment.UserManagerInteraction
import com.example.messenger.model.entity.Group
import com.example.messenger.model.entity.PrivateMessage
import com.example.messenger.model.entity.User
import com.example.messenger.presenter.fragment.ListChatPresenter
import com.sun.mail.imap.protocol.FetchResponse.getItem

class ListChatsAdapter(
    private val listChatPresenter: ListChatPresenter,

) :
    PagingDataAdapter<Any, ListChatsAdapter.ListChatsAdapterViewHolder>(COMPARATOR) {
    inner class ListChatsAdapterViewHolder(private val binding: ItemListChatBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(any: Any) {
            binding.apply {
                val defaultDrawable = imgAvatar.drawable
                when (any) {
                    (any is PrivateMessage) -> {
                        val privateMessage = any as PrivateMessage

                        //set hình ảnh và tên
                        privateMessage.name.filterKeys { it != SharedData.auth_uid }
                            .forEach { (id, name) ->
                                listChatPresenter.getInfo(id) { user ->
                                    if (imgAvatar.drawable == defaultDrawable) {
                                        Glide.with(binding.root)
                                            .load(user?.avatar)
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .error(R.drawable.ic_launcher_background)
                                            .centerCrop()
                                            .into(imgAvatar)
                                    }
                                    txtName.text = name
                                }
                            }

                        //set tin nhắn được gửi cuối cùng

                        privateMessage.messenger.lastOrNull()?.let { message ->
                                val senderId =
                                    privateMessage.name.filterKeys { it != SharedData.auth_uid }.keys.first()
                                if (senderId == SharedData.auth_uid) {
                                    txtMess.text = "Bạn: ${message.body} +${message.timeSend}"
                                } else {
                                    txtMess.setTypeface(null, if(message.uidReading.contains(SharedData.auth_uid)) Typeface.NORMAL else Typeface.BOLD)
                                    val lastName = txtName.text.toString().split(" ")
                                    txtMess.text = "${if (lastName.isNotEmpty()) lastName[lastName.size - 1] else txtName.text.toString()}: ${message.body} +${message.timeSend}"
                                }
                                if (imgAvatar1.drawable == defaultDrawable && message.uidReading.contains(SharedData.auth_uid).not()) {
                                    listChatPresenter.getInfo(senderId) { user ->
                                        Glide.with(binding.root)
                                            .load(user?.avatar)
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .error(R.drawable.ic_launcher_background)
                                            .centerCrop()
                                            .into(imgAvatar1)
                                    }
                                }

                        }
                    }

                    (any is Group) -> {
                        val group = (any as Group)
                         //set tên và avatar
                        txtName.text = group.groupName
                        Glide.with(binding.root)
                            .load(group.avatar)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .centerCrop()
                            .into(imgAvatar)
                        imgAvatar1.isVisible = false

                        //set tin nhắn cuối cùng

                            group.messenger.lastOrNull()?.let {message->
                                val senderId = group.name.filterKeys { it != SharedData.auth_uid }.keys.first()
                                if (senderId == SharedData.auth_uid) {
                                    txtMess.text = "Bạn: ${message.body} +${message.timeSend}"
                                } else {
                                    txtMess.setTypeface(null, if(message.uidReading.contains(SharedData.auth_uid)) Typeface.NORMAL else Typeface.BOLD)
                                    val lastName = txtName.text.toString().split(" ")
                                    txtMess.text = "${if (lastName.isNotEmpty()) lastName[lastName.size - 1] else txtName.text.toString()}: ${message.body} +${message.timeSend}"
                                }
                            }
                }
            }
        }
    }
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListChatsAdapterViewHolder {
    return ListChatsAdapterViewHolder(
        ItemListChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}

@RequiresApi(Build.VERSION_CODES.N)
override fun onBindViewHolder(holder: ListChatsAdapterViewHolder, position: Int) {
    getItem(position)?.let { holder.bind(it) }
}


companion object {

    private val COMPARATOR = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is Group && newItem is Group) {
                return oldItem.id == newItem.id
            }
            if (oldItem is PrivateMessage && newItem is PrivateMessage) {
                return oldItem.id == newItem.id
            }
            return false
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }
    }
}


}