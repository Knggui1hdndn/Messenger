package com.example.messenger.view.fragment.createAcount

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.messenger.R
import com.example.messenger.SendAndReceive
import com.example.messenger.contract.account.OTPContract
import com.example.messenger.databinding.FragmentCROtpBinding
import com.example.messenger.interaction.fragment.OTPFragmentInteraction
import com.example.messenger.listens.TextChangeListener
import com.example.messenger.presenter.fragment.account.OTPFragmentPresenter
import com.google.android.material.textfield.TextInputLayout

class CROtp : Fragment(R.layout.fragment_c_r__otp), OTPContract.View {
    private lateinit var number1: TextInputLayout
    private lateinit var number2: TextInputLayout
    private lateinit var number3: TextInputLayout
    private lateinit var number4: TextInputLayout
    private lateinit var number5: TextInputLayout
    private lateinit var number6: TextInputLayout
    private lateinit var btnConfirm: Button
    private lateinit var txtResend: TextView
    private lateinit var txtChangeSendOTP: TextView
    private lateinit var binding: FragmentCROtpBinding
    private lateinit var presenter: OTPFragmentPresenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCROtpBinding.bind(view)
        initView()

        val change = TextChangeListener()
        number1.requestFocus()
        change.onTextChange(number1, number2, number3, number4, number5, number6) { txt, id ->
            if (txt.length == 1) {
                when (id) {
                    number1.id -> {
                        number2.requestFocus();
                    }
                    number2.id -> {
                        number3.requestFocus();
                    }
                    number3.id -> {
                        number4.requestFocus();
                    }
                    number4.id -> {
                        number5.requestFocus();
                    }
                    number5.id -> {
                        number6.requestFocus();
                    }
                }
            }
        }
//Khởi tạo presnter
        presenter = OTPFragmentPresenter(
            OTPFragmentInteraction(requireActivity()), this, SendAndReceive.getDataArgument(this)
        )

        sendVerification()

        btnConfirm.setOnClickListener {
            presenter.verifyOTP(
                convertText(number1) + convertText(number2) + convertText(number3) + convertText(
                    number4
                ) + convertText(number5) + convertText(number6), txtResend.text.toString()
            )
        }

        txtChangeSendOTP.setOnClickListener {
            showDialog()
        }

        txtResend.setOnClickListener {
            sendVerification()
        }
    }

    private fun sendVerification() {
        txtChangeSendOTP.isEnabled = false
        if (txtChangeSendOTP.equals("Mã OTP được gửi qua SĐT.")) {
            presenter.verifyPhoneNumber()
        } else {
            presenter.verifyEmail()
        }
        OTPSendingCountdown()

    }

    private fun OTPSendingCountdown() {
        val countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                val secondsLeft = p0 / 1000
                txtResend.text = "Gửi lại mã sau $secondsLeft s."
            }

            override fun onFinish() {
                txtChangeSendOTP.isEnabled = true
                txtResend.text = "Gửi"
            }

        }
        countDownTimer.start()
    }


    @SuppressLint("SetTextI18n")
    private fun showDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Choose an Option")

// Set the radio buttons
        val options = arrayOf("Email", "SĐT")
        var checkedItem = 0 // The default selected item
        builder.setSingleChoiceItems(options, checkedItem) { dialog, which ->
            txtChangeSendOTP.text =
                "Mã OTP được gửi qua " + options[which] + "."
            Toast.makeText(requireActivity(), "$which+sss", Toast.LENGTH_LONG).show()
        }

// Set the OK button
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }

// Set the Cancel button
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

// Create and show the dialog
        val dialog = builder.create()
        dialog.show()

    }

    private fun convertText(text: TextInputLayout) = text.editText?.text.toString()

    private fun initView() {
        txtChangeSendOTP = binding.txtChangeSendOTP
        btnConfirm = binding.btnConfirm
        txtResend = binding.txtResend
        number1 = binding.number1
        number2 = binding.number2
        number3 = binding.number3
        number4 = binding.number4
        number5 = binding.number5
        number6 = binding.number6
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onVerifyPhoneNumberSuccess() {
        showToast("Đã gửi")
    }

    override fun onVerifyPhoneNumberSuccessFailure(message: String) {
        showToast("Lỗi:$message")
    }

    override fun onVerifyEmailSuccess() {
        showToast("Đã gửi")
    }

    override fun onVerifyEmailSuccessFailure(message: String) {
        showToast("Lỗi:$message")
    }

    override fun createSuccess() {
        showToast("Tạo tài khoản thành công")
        requireActivity().finish()
    }

    override fun createFailure(message: String) {
        showToast("Lỗi:$message")
        requireActivity().finish()

    }

    override fun onOTPVerificationSuccess() {
        showToast("Xác thực OTP thành công")
    }

    override fun onOTPVerificationFailure(s: String) {
        showToast("Lỗi:$s")

    }
}