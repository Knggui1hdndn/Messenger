package com.example.messenger.view.fragment.createAcount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.example.messenger.listens.TextChangeListener
import com.example.messenger.R
import com.example.messenger.SendAndReceive
import com.example.messenger.contract.account.CrPhoneNumberContract
import com.example.messenger.contract.account.CreateContract
import com.example.messenger.databinding.FragmentCrPhoneNumberBinding
import com.example.messenger.interaction.fragment.CrPhoneNumberFragmentInteraction
import com.example.messenger.presenter.fragment.account.CrPhoneNumberFragmentPresenter
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputLayout


class CrPhoneNumberFragment : Fragment(R.layout.fragment_cr_phone_number), CreateContract,
    CrPhoneNumberContract.View {
    private lateinit var tLayoutEmail: TextInputLayout
    private lateinit var tLayoutPhoneNumber: TextInputLayout
    private lateinit var btnConfirm: Button
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var binding: FragmentCrPhoneNumberBinding
    private var change = TextChangeListener()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCrPhoneNumberBinding.bind(view)
        initView()
        shimmer.hideShimmer()

        change.onTextChange(tLayoutEmail, tLayoutPhoneNumber) { _, id ->
            if (id == R.id.tLayoutEmail) {
                setErrorEditText(tLayoutEmail, null, false)
            } else {
                setErrorEditText(tLayoutPhoneNumber, null, false)
            }
        }
        val presenter = CrPhoneNumberFragmentPresenter(this, CrPhoneNumberFragmentInteraction())
        btnConfirm.setOnClickListener {
            shimmer.showShimmer(true)
            shimmer.startShimmer()
             presenter.checkEmailAndPhoneExist(
                tLayoutEmail.editText?.text.toString(),
                tLayoutPhoneNumber.editText?.text.toString()
            )
        }
    }

    private fun setErrorEditText(editText: TextInputLayout, error: String?, enableError: Boolean) {
        editText.error = error
        editText.isErrorEnabled = enableError
    }

    override fun initView() {
        tLayoutEmail = binding.tLayoutEmail
        btnConfirm = binding.btnConfirm
        tLayoutPhoneNumber = binding.tLayoutPhoneNumber
        shimmer = binding.shimer

    }

    override fun success() {
        SendAndReceive.sendDataArgument(SendAndReceive.getDataArgument(this).apply {
            email = tLayoutEmail.editText?.text.toString()
            phone = tLayoutPhoneNumber.editText?.text.toString()
        }, this)
    }

    override fun failure(message: String) {
        shimmer.hideShimmer()
        when (message) {
            getString(R.string.suggest_phone_error) -> {
                setErrorEditText(tLayoutPhoneNumber, "", true)
                setErrorEditText(tLayoutEmail, "", true)
            }
            "Không bỏ trống Email" -> {
                setErrorEditText(tLayoutEmail, message, true)

            }
            "Không bỏ trống SĐT" -> {
                setErrorEditText(tLayoutPhoneNumber, message, true)
            }
            "Email đã được đăng kí" -> {
                setErrorEditText(tLayoutEmail, message, true)
            }
            "SĐT đã được đăng kí" -> {
                setErrorEditText(tLayoutPhoneNumber, message, true)
            }
            "Email và SĐT đã được đăng kí" -> {
                setErrorEditText(tLayoutEmail, message, true)
                setErrorEditText(tLayoutPhoneNumber, message, true)
            }
        }
    }
}