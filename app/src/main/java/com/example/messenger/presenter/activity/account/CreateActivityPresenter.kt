package com.example.messenger.presenter.activity.account

import com.example.messenger.contract.account.CreateContract
import com.example.messenger.interaction.activity.CreateActivityInteraction

class CreateActivityPresenter(
    private val view: CreateContract.View.ViewCreate,
    private val interaction: CreateActivityInteraction
) : CreateContract.Presenter {
    override fun create(account: String, password: String) {

        interaction.create(account, password) { b, s ->
            if (b) {
            }
        }
    }


}