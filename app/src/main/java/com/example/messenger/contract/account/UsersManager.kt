package com.example.messenger.contract.account

import android.net.Uri
import com.example.messenger.model.entity.User

interface UsersManager {
    interface View {

    }

    interface Interaction {
        fun addLimits(uid: String, call: (Boolean, String?) -> Unit)
        fun addBlocks(uid: String, call: (Boolean, String?) -> Unit)
        fun addStorage(uid: String, call: (Boolean, String?) -> Unit)
        fun addUnknown(uid: String, call: (Boolean, String?) -> Unit)
        fun deleteLimits(uid: String, call: (Boolean, String?) -> Unit)
        fun deleteUnknown(uid: String, call: (Boolean, String?) -> Unit)
        fun deleteBlocks(uid: String, call: (Boolean, String?) -> Unit)
        fun deleteStorage(uid: String, call: (Boolean, String?) -> Unit)
        fun deleteChat(uid: String, call: (Boolean, String?) -> Unit)
        fun updateUser(user: User? , call: (Boolean, String?) -> Unit)
        fun updateImage(uri: Uri, call: (Boolean, String?) -> Unit)
        fun checkFriends(uid: String, call: (Boolean, String?) -> Unit)

    }
}