package com.example.messenger.contract.account

import com.example.messenger.model.entity.Group
import com.example.messenger.model.entity.PrivateMessage
import com.example.messenger.model.entity.User

interface ListChatFragmentContract {
    interface View {}
    interface Presenter {

        fun getInfo(uid: String, call: (User?) -> Unit)
        fun getChats(call: (any: List<Any>, error: String) -> Unit)
        fun getListChat(
            id: String,
            call: (privateMessage: List<PrivateMessage>, error: String) -> Unit
        )

        fun getListGroup(idGroup: String, call: (group: List<Group>, error: String) -> Unit)
    }

    interface Interaction {
        fun getListChatsRcy(call: (any: List<Any>, error: String?) -> Unit)
        fun getListUnknownRcy(call: (any: List<Any>, error: String?) -> Unit)
        fun getListLimitsRcy(call: (any: List<Any>, error: String?) -> Unit)
        fun getListStorageRcy(call: (any: List<Any>, error: String?) -> Unit)
        fun getInfo(uid: String, call: (User?) -> Unit)
        fun getListChat(
            list: MutableList<String>,
            call: (privateMessage: List<PrivateMessage>, error: String?) -> Unit
        )

        fun getListGroup(
            list: MutableList<String>,
            call: (group: List<Group>, error: String?) -> Unit
        )
    }
}