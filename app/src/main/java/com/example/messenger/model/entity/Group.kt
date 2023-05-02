package com.example.messenger.model.entity

class Group(
    val id: String = "",
    val groupName: String = "",
    val name: HashMap<String,String> = hashMapOf(),
    val avatar: String = "",
    val leader: String = "",
    val deleteChatsBy: MutableList<String> = mutableListOf(),
    val messenger: List<Message> = listOf()
) :java.io.Serializable{

}
