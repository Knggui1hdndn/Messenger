package com.example.messenger.presenter.fragment.account

import com.example.messenger.contract.account.CrPasswordContract

class CrPasswordFragmentPresenter(val view: CrPasswordContract.View) : CrPasswordContract.Presenter {
    override fun confirm(password: String) {
        var x = 0
        var y = 0
        var z = 0
        if (password.length >=6) {
            for (i in password.indices) {
                if ((password[i].code > 'a'.code && password[i].code < 'z'.code) || (password[i].code > 'A'.code && password[i].code < 'z'.code)) {
                    x = 2
                } else if (password[i].code >= '0'.code && password[i].code <= '9'.code) {
                    y = 2
                } else {
                    z = 2
                }
            }
            if (x == 2 && y == 2 && z == 2) {
                view.success()
            }else{
                view.failed("Mật khẩu phải có tối thiểu 6 chữ cái,số và kí tự đặc biệt.")
            }
        } else {
            view.failed("Mật khẩu phải có tối thiểu 6 chữ cái,số và kí tự đặc biệt.")
        }
    }
}