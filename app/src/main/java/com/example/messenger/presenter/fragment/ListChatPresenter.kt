package com.example.messenger.presenter.fragment

import com.example.messenger.contract.account.ListChatFragmentContract
import com.example.messenger.model.entity.Group
import com.example.messenger.model.entity.PrivateMessage
import com.example.messenger.model.entity.User
import java.util.*

class ListChatPresenter :ListChatFragmentContract.Presenter{
    override fun getInfo(uid: String, call: (User?) -> Unit) {

    }

    override fun getChats(call: (any: List<Any>, error: String) -> Unit) {

    }

    override fun getListChat(
        id: String,
        call: (privateMessage: List<PrivateMessage>, error: String) -> Unit
    ) {

    }

    override fun getListGroup(idGroup: String, call: (group: List<Group>, error: String) -> Unit) {

    }


}