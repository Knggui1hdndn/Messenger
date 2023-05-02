package com.example.messenger.view.fragment.createAcount

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.messenger.R
import com.example.messenger.SendAndReceive
import com.example.messenger.contract.account.CrPasswordContract
import com.example.messenger.databinding.FragmentCrPasswordBinding
import com.example.messenger.listens.TextChangeListener
import com.example.messenger.presenter.fragment.account.CrPasswordFragmentPresenter
import org.mindrot.jbcrypt.BCrypt


class CrPasswordFragment : Fragment(R.layout.fragment_cr_password), CrPasswordContract.View {
    private lateinit var binfding: FragmentCrPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binfding = FragmentCrPasswordBinding.bind(view)

        TextChangeListener().onTextChange(binfding.edtPassword) { s, id ->
            binfding.txtSuggest.text =
                "Tạo mật khẩu dài tối thiểu 6 kí tự.Đó phải là mật khẩu mà người khác không thể đoán được."
            binfding.txtSuggest.setTextColor(Color.GRAY)

        }
        val presenter = CrPasswordFragmentPresenter(this)
        binfding.btnConfirm.setOnClickListener {
            presenter.confirm(binfding.edtPassword.editText?.text.toString())
        }
    }

    override fun success() {
        // Khai báo salt
        val salt = BCrypt.gensalt()
        // Mã hóa mật khẩu với salt
//        "Tạo mật khẩu dài tối thiểu 6 kí tự.Đó phải là mật khẩu mà người khác không thể đoán được."
        SendAndReceive.sendDataArgument(SendAndReceive.getDataArgument(this).apply {
            password = BCrypt.hashpw(binfding.edtPassword.editText?.text.toString(), salt)
        }, this)
    }

    override fun failed(message: String) {
        binfding.txtSuggest.text = message
        binfding.txtSuggest.setTextColor(Color.RED)
    }
}