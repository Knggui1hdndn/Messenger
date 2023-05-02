package com.example.messenger.presenter.fragment.account

import android.util.Log
import com.example.messenger.contract.account.OTPContract
import com.example.messenger.interaction.fragment.OTPFragmentInteraction
import com.example.messenger.model.entity.User

class OTPFragmentPresenter(
    val interaction: OTPFragmentInteraction,
    val view: OTPContract.View,
    private val user: User
) :
    OTPContract.Presenter {
    private var code = "OTP"

    override fun createAccount() {
        interaction.createAccount(user.email, user.password) { b, s ->
            if (b) {
                interaction.setAccountFromRealTime(user){b,e->
                    if(b) view.createSuccess() else e?.let { view.createFailure(e) }
                }
            } else {
                s?.let { view.createFailure(s) }
            }
        }

    }


    override fun verifyEmail() {
        interaction.verifyEmail(user.email) { b, s ->
            if (b) {
                code = s.toString()
                view.onVerifyEmailSuccess()
            } else {
                s?.let { view.onVerifyEmailSuccessFailure(it) }
            }
        }
    }

    override fun verifyPhoneNumber() {
        interaction.verifyPhoneNumber("+84867896418") { b, s ->
            if (b) {
                code = s.toString()
                view.onVerifyPhoneNumberSuccess()
            } else {
                s?.let { view.onVerifyPhoneNumberSuccessFailure(it) }
            }
        }
    }

    override fun verifyOTP(otp: String,s :String) {
        // Kiểm tra xem mã OTP có khớp với mã gốc hay không
        if (otp.length != 6) {
            view.onOTPVerificationFailure("Mã OTP gồm có 6 chữ số")
            return
        }
        if(s == "Gửi lại mã sau 0 s."){
            view.onOTPVerificationFailure("Mã OTP hết hạn")
            return
        }
         if (otp == code) {
            view.onOTPVerificationSuccess()
            createAccount()
        } else {
            view.onOTPVerificationFailure("Mã OTP không đúng")
        }
    }
}