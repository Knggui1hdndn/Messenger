package com.example.messenger.interaction.fragment

import android.app.Activity
import android.util.Log
import com.example.messenger.contract.account.OTPContract
import com.example.messenger.model.entity.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class OTPFragmentInteraction(private val context: Activity) : OTPContract.Interaction {
    override fun createAccount(
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, p0.exception?.message)
                }
            }
    }

    override fun setAccountFromRealTime(user: User, callback: (Boolean, String?) -> Unit) {
        FirebaseDatabase.getInstance().reference.child("Messenger/users/").push().setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) callback(true, null) else callback(
                    false,
                    it.exception.toString()
                )
            }
    }

    override fun verifyEmail(email: String, callback: (Boolean, String?) -> Unit) {
        val random = Random.nextFloat() * 1000000
        val client = OkHttpClient()
        val mediaType: MediaType? =
            "application/x-www-form-urlencoded".toMediaTypeOrNull()
        val body: RequestBody = "email=$email&code=${random.toInt()}".toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url("https://3002992121.000webhostapp.com/send_otp.php")
            .post(body)
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ssssssssss", "${e.message}")
                    context.runOnUiThread {
                        callback(false, e.message)
                    }
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
                    val responseString = response.body?.string()
                    if (response.isSuccessful) {
                        context.runOnUiThread {
                            callback(true, random.toInt().toString())
                        }

                    } else {
                        context.runOnUiThread {
                            callback(false, responseString)
                        }


                    }
                }

            })
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    override fun verifyPhoneNumber(phone: String, callback: (Boolean, String?) -> Unit) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    callback(true, credential.smsCode)

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    callback(false, e.message)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {

                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}