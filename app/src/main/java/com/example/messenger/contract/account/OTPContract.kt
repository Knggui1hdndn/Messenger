package com.example.messenger.contract.account

import com.example.messenger.model.entity.User

interface OTPContract {
    interface View {
        fun onVerifyPhoneNumberSuccess()
        fun onVerifyPhoneNumberSuccessFailure(message: String)
        fun onVerifyEmailSuccess()
        fun onVerifyEmailSuccessFailure(message: String)
        fun createSuccess()
        fun createFailure(message: String)
        fun onOTPVerificationSuccess()
          fun onOTPVerificationFailure(s: String)


    }

    interface Presenter {
        fun createAccount()
        fun verifyEmail()
        fun verifyPhoneNumber()
        fun verifyOTP(otp: String,s :String)
    }

    interface Interaction {
        fun createAccount(email: String, password: String, callback: (Boolean, String?) -> Unit)
        fun setAccountFromRealTime(user: User, callback: (Boolean, String?) -> Unit)
        fun verifyEmail(email: String, callback: (Boolean, String?) -> Unit)
        fun verifyPhoneNumber(phone: String, callback: (Boolean, String?) -> Unit)
    }

}