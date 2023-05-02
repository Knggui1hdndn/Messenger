package com.example.messenger.contract.account

interface LoginContract {
    interface View {
        fun showLoginSuccess()
        fun showLoginError(message: String)
    }


    interface Presenter {
        fun login(account: String, password: String)

    }

    interface Interaction {
        fun login(email: String, password: String, callback: (Boolean, String?) -> Unit)

    }
}