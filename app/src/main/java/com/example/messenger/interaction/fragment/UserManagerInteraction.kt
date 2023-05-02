package com.example.messenger.interaction.fragment

import android.net.Uri
import com.example.messenger.SharedData
import com.example.messenger.contract.account.UsersManager
import com.example.messenger.model.entity.Message
import com.example.messenger.model.entity.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

class UserManagerInteraction : UsersManager.Interaction {
    private val ref = Firebase.database.reference

    override fun addLimits(uid: String, call: (Boolean, String?) -> Unit) {
        add("Messenger/users/" + SharedData.auth_uid + "/limits", uid, call)

    }

    override fun addBlocks(uid: String, call: (Boolean, String?) -> Unit) {
        add("Messenger/users/" + SharedData.auth_uid + "/blocks", uid, call)

    }

    override fun addStorage(uid: String, call: (Boolean, String?) -> Unit) {
        add("Messenger/users/" + SharedData.auth_uid + "/storage", uid, call)
    }

    override fun addUnknown(uid: String, call: (Boolean, String?) -> Unit) {
        add("Messenger/users/" + SharedData.auth_uid + "/unknown", uid, call)
     }

    override fun deleteLimits(uid: String, call: (Boolean, String?) -> Unit) {
        delete("Messenger/users/" + SharedData.auth_uid + "/limits", uid, call)

    }

    override fun deleteUnknown(uid: String, call: (Boolean, String?) -> Unit) {
        delete("Messenger/users/" + SharedData.auth_uid + "/unknown", uid, call)
    }

    override fun deleteBlocks(uid: String, call: (Boolean, String?) -> Unit) {
        delete("Messenger/users/" + SharedData.auth_uid + "/blocks", uid, call)
    }

    override fun deleteStorage(uid: String, call: (Boolean, String?) -> Unit) {
        delete("Messenger/users/" + SharedData.auth_uid + "/storage", uid, call)
    }


    override fun deleteChat(uid: String, call: (Boolean, String?) -> Unit) {
        delete("Messenger/users/" + SharedData.auth_uid + "/chats", uid, call)

    }


    override fun updateUser(user: User?, call: (Boolean, String?) -> Unit) {

    }

    override fun updateImage(uri: Uri,  call: (Boolean, String?) -> Unit) {
        Firebase.storage.reference.child("Messenger/users/"+SharedData.auth_uid+"/avatar").putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                call(true, null)
            } else {
                call(false, it.exception.toString())
            }
        }
    }

    override fun checkFriends(uid: String, call: (Boolean, String?) -> Unit) {
        ref.child("Messenger/users").orderByKey().equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) call(true, null) else call(false, null)
                }

                override fun onCancelled(error: DatabaseError) {
                    call(false, error.toException().toString())
                }
            })
    }

    fun delete(path: String, uid: String, call: (Boolean, String?) -> Unit) {
        ref.child(path).orderByValue().equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.ref.removeValue().addOnCompleteListener {
                        if (it.isSuccessful) {
                            call(true, null)
                        } else {
                            call(false, it.exception.toString())
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    call(false, error.toException().toString())
                }
            })

    }

    private fun add(path: String, uid: String, call: (Boolean, String?) -> Unit) {
        ref.child(path).push().setValue(uid)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    call(true, null)
                } else {
                    call(false, it.exception.toString())
                }
            }
    }

}