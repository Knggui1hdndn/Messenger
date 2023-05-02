package com.example.messenger.presenter.activity.account

import com.example.messenger.contract.account.ForgotContract
import com.example.messenger.interaction.activity.ForgotActivityInteraction

class ForgotActivityPresenter(
    private val view: ForgotContract.View.ViewForgot,
    private val interaction: ForgotActivityInteraction
) : ForgotContract.Presenter {
    override fun forgotPassword(email: String) {
    }


}