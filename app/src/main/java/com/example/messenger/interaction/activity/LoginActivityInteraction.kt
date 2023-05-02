package com.example.messenger.interaction.activity

import android.util.Patterns
import com.example.messenger.contract.account.LoginContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class LoginActivityInteraction : LoginContract.Interaction {
    override fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        if (Patterns.PHONE.matcher(email).matches()) {
            Firebase.database.reference.child("Messenger/User").orderByChild("phone").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val phone = snapshot.child("phone").getValue<String>()
                            loginAuth(phone.toString(), password, callback)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        } else {
            loginAuth(email, password, callback)

        }

    }

    private fun loginAuth(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, "Sai mật khẩu hoặc tài khoản")
                }
            }
    }

}