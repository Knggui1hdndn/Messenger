package com.example.messenger.contract.account

import com.google.android.material.textfield.TextInputLayout

interface CreateContract {
    fun initView()
    interface View{
        interface CheckData{
            fun  success()
            fun showError(message: String)

        }
        interface ViewCreate {
            fun showCreateSuccess()
            fun showCreateError(message: String)
        }
    }

    interface Presenter {
        fun create(account: String, password: String)
    }

    interface Interaction {
        fun create(email: String, password: String, callback: (Boolean, String?) -> Unit)
    }
}