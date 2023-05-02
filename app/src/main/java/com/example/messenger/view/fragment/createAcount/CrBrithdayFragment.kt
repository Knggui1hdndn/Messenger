package com.example.messenger.view.fragment.createAcount

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColorInt
import com.example.messenger.R
import com.example.messenger.SendAndReceive
import com.example.messenger.databinding.FragmentCRBrithdayBinding
import java.util.*


class CRBrithday : Fragment(R.layout.fragment_c_r_brithday) {
    private lateinit var binding: FragmentCRBrithdayBinding
    private lateinit var txtOld: TextView
    private lateinit var txtSuggest: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var btnConfirm: Button

    @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCRBrithdayBinding.bind(view)
        initView()
        val currentDate = Calendar.getInstance()
        val selectedDate = Calendar.getInstance()
        btnConfirm.setOnClickListener {
            if (txtOld.text.toString().split(" ")[0].toInt() <= 0) {
                showError(getString(R.string.suggest_brithday_error))
            } else {
                SendAndReceive.sendDataArgument(
                    SendAndReceive.getDataArgument(this)
                        .apply { birthday = "${datePicker.dayOfMonth}-${datePicker.month+1}-${datePicker.year}" }, this
                )
            }
        }
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val diffInMillis = currentDate.timeInMillis - selectedDate.timeInMillis
            val diffYears = (diffInMillis / (1000 * 60 * 60 * 24 * 365.25)).toInt()
            txtOld.text = diffYears.toString()
            txtSuggest.text = getString(R.string.suggest_brithday)
            txtSuggest.setTextColor(R.color.black)
        }
    }

    private fun initView() {
        txtOld = binding.txtOld
        datePicker = binding.datePicker
        btnConfirm = binding.btnConfirm
        txtSuggest = binding.txtSuggest
    }


    fun showError(message: String) {
        txtSuggest.text = message
        txtSuggest.setTextColor((0xFFFF0000).toColorInt())
    }


}