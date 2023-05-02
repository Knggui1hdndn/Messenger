package com.example.messenger.presenter.fragment.account

import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.contract.account.CrPhoneNumberContract
import com.example.messenger.interaction.fragment.CrPhoneNumberFragmentInteraction
import com.example.messenger.model.entity.User

class CrPhoneNumberFragmentPresenter(
    val view: CrPhoneNumberContract.View,
    val interaction: CrPhoneNumberFragmentInteraction
) : CrPhoneNumberContract.Presenter {
    override fun checkEmailAndPhoneExist(email: String, phone: String) {
        val isPhone = Patterns.PHONE.matcher(phone).matches()
        val isEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        when {
            email.isEmpty() && phone.isEmpty() -> view.failure("Không bỏ trống Email và SĐT")
            email.isEmpty() -> view.failure("Không bỏ trống Email")
            phone.isEmpty() -> view.failure("Không bỏ trống SĐT")
             !isPhone && !isEmail -> view.failure("Email và SĐT không đúng định dạng")
            !isPhone -> view.failure("SĐT không đúng định dạng")
            !isEmail -> view.failure("Email không đúng định dạng")
            else -> {
                interaction.checkEmailExist(email) { boolean, error ->
                    var i: Int = 0
                    var i1: Int = 0
                    if (boolean) {
                        i = 1
                        i1++
                    } else {
                        interaction.checkPhoneNumberExist(phone) { boolean, error ->
                            if (boolean) {
                                i = 2
                                i1++
                            } else {
                                view.success()
                            }
                            if (i1 == 2) {
                                i = 0
                            }
                            when (i) {
                                0 -> view.failure("Email và SĐT đã được đăng kí")
                                1 -> view.failure("Email đã được đăng kí")
                                2 -> view.failure("SĐT đã được đăng kí")
                            }
                        }
                    }


                }
            }
        }
    }


}