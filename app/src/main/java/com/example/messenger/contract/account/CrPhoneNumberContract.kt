package com.example.messenger.contract.account

interface CrPhoneNumberContract {
    interface View {
        fun success()
        fun failure(message: String)
    }

    interface Presenter {
        fun checkEmailAndPhoneExist(email: String, phone: String)

    }

    interface Interaction {
        fun checkEmailExist(
            email: String,
            callback: (Boolean,String?) -> Unit
        )
        fun checkPhoneNumberExist(
            phone: String,
            callback: (Boolean,String?) -> Unit
        )
    }
}