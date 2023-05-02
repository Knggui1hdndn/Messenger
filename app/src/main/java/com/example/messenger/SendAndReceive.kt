package com.example.messenger

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.messenger.model.entity.User
 import com.example.messenger.view.fragment.createAcount.*

class SendAndReceive {
    companion object {
        fun sendDataArgument(user: User, fragment: Fragment) {
            var data: NavDirections? = null
            when (fragment) {
                is CRBrithday -> {
                    data = CRBrithdayDirections.actionCRBrithdayToCrPhoneNumber(user)
                }
                is CRName -> {
                    data = CRNameDirections.actionCRNameToCrSex(user)
                }
                is CrPasswordFragment -> {
                    data = CrPasswordFragmentDirections.actionCrPasswordToCROtp(user)
                }
                is CrPhoneNumberFragment -> {
                    data = CrPhoneNumberFragmentDirections.actionCrPhoneNumberToCrPassword(user)
                }
                is CrSexFragment -> {
                    data = CrSexFragmentDirections.actionCrSexToCRBrithday(user)
                }
            }
            data?.let {
                fragment.findNavController().navigate(it
                    )

            }
        }

        fun getDataArgument(fragment: Fragment): User {
            when (fragment) {
                is CRBrithday -> {
                    val args: CRBrithdayArgs by fragment.navArgs()
                    Log.d("sssssssa", args.user.toString())
                    return args.user
                }
                is CrPasswordFragment -> {
                    val args: CrPasswordFragmentArgs by fragment.navArgs()
                    Log.d("sssssssa", args.user.toString())

                    return args.user
                }
                is CrPhoneNumberFragment -> {
                    val args: CrPhoneNumberFragmentArgs by fragment.navArgs()
                    Log.d("sssssssa", args.user.toString())
                    return args.user
                }
                is CrSexFragment -> {
                    val args: CrSexFragmentArgs by fragment.navArgs()
                    Log.d("sssssssa", args.user.toString())
                    return args.user
                }
                is CROtp -> {
                    val args: CROtpArgs by fragment.navArgs()
                     return args.user
                }
            }
            return User()

        }
    }
}