package com.example.messenger.view.fragment.createAcount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.lifecycle.LifecycleObserver
import com.example.messenger.R
import com.example.messenger.SendAndReceive
import com.example.messenger.contract.account.CreateContract
import com.example.messenger.databinding.FragmentCrSexBinding


class CrSexFragment : Fragment(R.layout.fragment_cr_sex), CreateContract {
    private lateinit var rdiGroup: RadioGroup
    private lateinit var btnConfirm: Button
    private lateinit var binding:  FragmentCrSexBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCrSexBinding.bind(view)
        initView()
        viewLifecycleOwner.lifecycle.addObserver(object :  LifecycleObserver {

        })
        btnConfirm.setOnClickListener {
            SendAndReceive.sendDataArgument(SendAndReceive.getDataArgument(this).apply {
                rdiGroup.apply {
                    sex = if (checkedRadioButtonId == R.id.rdiNam) "Nam" else "Nữ"
                }
            }, this)
        }
    }

    override fun initView() {
        rdiGroup = binding.rdiGroup
        btnConfirm = binding.btnConfirm
    }
}