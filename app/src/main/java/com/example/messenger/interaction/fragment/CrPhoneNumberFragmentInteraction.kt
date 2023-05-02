package com.example.messenger.interaction.fragment

import android.util.Log
import com.example.messenger.contract.account.CrPhoneNumberContract
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CrPhoneNumberFragmentInteraction : CrPhoneNumberContract.Interaction {


    override fun checkEmailExist(email: String, callback: (Boolean, String?) -> Unit) {

        Firebase.database.reference.child("Messenger/users").orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        callback(true, "Email đã tồn tại")
                    } else {
                        callback(false, null)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false, error.message)
                    Log.d("ddddd",error.message)
                }
            })
    }

    override fun checkPhoneNumberExist(phone: String, callback: (Boolean, String?) -> Unit) {
        Firebase.database.reference.child("Messenger/users").orderByChild("phone").equalTo(phone)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        callback(true, "Phone đã tồn tại")
                    } else {
                        callback(false, null)
                    }
                    Log.d("ddddd", snapshot.exists().toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false, error.message)
                    Log.d("ddddd",error.message)

                }
            })
    }
}