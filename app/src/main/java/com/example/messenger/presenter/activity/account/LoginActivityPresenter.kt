package com.example.messenger.presenter.activity.account

import android.util.Patterns
import com.example.messenger.contract.account.LoginContract
import com.example.messenger.interaction.activity.LoginActivityInteraction

class LoginActivityPresenter(
    private val view: LoginContract.View,
    private val interaction: LoginActivityInteraction
) : LoginContract.Presenter {

    override fun login(account: String, password: String) {
        val isPhone = Patterns.PHONE.matcher(account).matches()
        val isEmail = Patterns.EMAIL_ADDRESS.matcher(account).matches()
        when {
            password.isEmpty() && account.isEmpty() -> view.showLoginError("all")
            account.isEmpty() -> view.showLoginError("account")
            password.isEmpty() -> view.showLoginError("password")
            !isPhone || !isEmail -> view.showLoginError("Email hoặc SĐT không đúng định dạng")
            else -> {
                interaction.login(account, password) { boolean, error ->
                    if (boolean) {
                        view.showLoginSuccess()
                    } else {
                        error?.let { view.showLoginError(it) }
                    }
                }
            }
        }
    }


}