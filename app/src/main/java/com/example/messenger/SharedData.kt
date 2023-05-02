package com.example.messenger

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SharedData {
    companion object{
        val auth_uid : String? =  if (Firebase.auth.currentUser!=null) Firebase.auth.uid.toString() else null
    }
}