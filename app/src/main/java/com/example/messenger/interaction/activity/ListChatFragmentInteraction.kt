package com.example.messenger.interaction.activity

import android.annotation.SuppressLint
import com.example.messenger.SharedData
import com.example.messenger.contract.account.ListChatFragmentContract
import com.example.messenger.model.entity.Group
import com.example.messenger.model.entity.PrivateMessage
import com.example.messenger.model.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import okhttp3.internal.filterList
import okhttp3.internal.wait
import java.util.*

class ListChatFragmentInteraction : ListChatFragmentContract.Interaction {
    private val ref = Firebase.database.reference


    override fun getListChatsRcy(call: (any: List<Any>, error: String?) -> Unit) {
        SharedData.auth_uid?.let {
            getInfo(it){
                it?.chats?.let { it1 -> getList(it1,call) }
            }
        }


    }

    @SuppressLint("SuspiciousIndentation")
    private fun getList(list: MutableList<String>, call: (any: List<Any>, error: String?) -> Unit) {
        val mutableList = mutableListOf<Any>()
            list.let { it1 ->
                getListChat(it1) { list, error ->
                    if (error == null) {
                        mutableList.addAll(list)
                        getListGroup(it1) { list, error ->
                            if (error == null) {
                                mutableList.addAll(list)
                                Collections.sort(mutableList, compareByDescending {
                                    when (it) {
                                        is Group -> it.messenger.lastOrNull()?.timeSend
                                        is PrivateMessage -> it.messenger.lastOrNull()?.timeSend
                                        else -> 0
                                    }
                                })
                                call(mutableList.toList(), null)
                            } else {
                                call(emptyList(), error)
                            }
                        }
                    } else {
                        call(emptyList(), error)
                }
            }
        }
    }

    override fun getListUnknownRcy(call: (any: List<Any>, error: String?) -> Unit) {
        SharedData.auth_uid?.let {
            getInfo(it){
                it?.unknown?.let { it1 -> getList(it1,call) }
            }
        }
    }

    override fun getListLimitsRcy(call: (any: List<Any>, error: String?) -> Unit) {
        SharedData.auth_uid?.let {
            getInfo(it){
                it?.limits?.let { it1 -> getList(it1,call) }
            }
        }
    }

    override fun getListStorageRcy(call: (any: List<Any>, error: String?) -> Unit) {
        SharedData.auth_uid?.let {
            getInfo(it){
                it?.storage?.let { it1 -> getList(it1,call) }
            }
        }
    }

    override fun getListGroup(
        list: MutableList<String>,
        call: (group: List<Group>, error: String?) -> Unit
    ) {
        var late: String? = null
        ref.child("Messenger/Group").limitToFirst(10).startAt(late).orderByChild("id")
        list.forEach {
            ref.equalTo(it)
        }
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.map { it.getValue(Group::class.java) }.toList()
                late = list.lastOrNull().toString()
                call(list.filterNotNull(), null)
            }

            override fun onCancelled(error: DatabaseError) {
                call(emptyList(), null)
            }

        })

    }


    override fun getListChat(
        list: MutableList<String>,

        call: (privateMessage: List<PrivateMessage>, error: String?) -> Unit
    ) {
        var late: String? = null
        ref.child("Messenger/PrivateMessage").limitToFirst(10).startAt(late)
            .orderByChild("id")
        list.forEach {
            ref.equalTo(it)
        }
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list =
                    snapshot.children.map { it.getValue(PrivateMessage::class.java) }.toList()
                late = list.lastOrNull().toString()
                call(list.filterNotNull(), null)
            }

            override fun onCancelled(error: DatabaseError) {
                call(emptyList(), null)
            }

        })

    }

    override fun getInfo(uid: String, call: (User?) -> Unit) {
        ref.child("Messenger/users").orderByKey().equalTo(uid).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    call(it.result.value as User)
                } else {
                    call(null)
                }
            }
    }

}