package com.example.messenger.contract.account

interface ForgotContract {
    interface View {
        interface CheckData{
            fun  success()
            fun showError(message: String)

        }
        interface ViewForgot {
            fun showForgotSuccess()
            fun showForgotError(message: String)
        }
    }


    interface Presenter {
        fun forgotPassword(email: String)
    }

    interface Interaction {
        fun forgotPassword(email: String, callback: (Boolean, String?) -> Unit)
    }
}