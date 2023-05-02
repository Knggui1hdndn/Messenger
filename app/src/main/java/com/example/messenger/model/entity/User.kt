package com.example.messenger.model.entity

data class User(
    var uid: String,
    var name: String,
    var avatar: String,
    var email: String,
    var phone: String,
    var password: String,
    var address: String,
    var birthday: String,
    var sex: String,
    var habitat: String,
    var career: String,
    var chats: MutableList<String> = mutableListOf(),
    var friends: MutableList<String> = mutableListOf(),
    var blocks: MutableList<String> = mutableListOf(),
    var relationship: String = "",
    var limits: MutableList<String> = mutableListOf(),
    var storage: MutableList<String> = mutableListOf(),
    var unknown: MutableList<String> = mutableListOf(),
    var sendFriend: MutableList<String> = mutableListOf(),
    var friendRequest: MutableList<String> = mutableListOf(),
) : java.io.Serializable {
    constructor() : this(
        "", "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        "",
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )

    override fun toString(): String {
        return "User(name='$name', email='$email', phone='$phone', password='$password', address='$address', birthday='$birthday', sex='$sex', habitat='$habitat', career='$career', friends=$friends, blocks=$blocks, relationship='$relationship', limits=$limits, storage=$storage, unknown=$unknown, sendFriend=$sendFriend, friendRequest=$friendRequest)"
    }

}
