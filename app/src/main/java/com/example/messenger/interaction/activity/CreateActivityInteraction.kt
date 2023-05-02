package com.example.messenger.interaction.activity

import android.app.Activity
import com.example.messenger.contract.account.CreateContract

class CreateActivityInteraction(private var context: Activity) : CreateContract.Interaction  {


    override fun create(email: String, password: String, callback: (Boolean, String?) -> Unit) {

    }





}