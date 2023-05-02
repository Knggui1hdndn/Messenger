package com.example.messenger.model.entity

//xóa theo UID
class PrivateMessage(
    val id: String = "",
    val name: HashMap<String, String> = hashMapOf(),
    val deleteChatsBy: MutableList<String> = mutableListOf(),
    val messenger: MutableList<Message> = mutableListOf()
)


