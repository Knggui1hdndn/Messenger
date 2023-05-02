package com.example.messenger.view.fragment.createAcount

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.SendAndReceive
import com.example.messenger.contract.account.CreateContract
import com.example.messenger.databinding.FragmentCRNameBinding
import com.example.messenger.listens.TextChangeListener
import com.example.messenger.model.entity.User
import com.google.android.material.textfield.TextInputLayout


class CRName : Fragment(R.layout.fragment_c_r__name), CreateContract {
    private lateinit var binding: FragmentCRNameBinding
    private lateinit var edtFirstName: TextInputLayout
    private lateinit var edtLateName: TextInputLayout
    private lateinit var txtSuggestName: TextView
    val text = TextChangeListener()
private lateinit var recycler: RecyclerView

    private var savedStateHandle: SavedStateHandle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedStateHandle= SavedStateHandle()
        Log.d("sssssss", "onCreate")
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCRNameBinding.bind(view)
        initView()
        Log.d("sssssss", "onViewCreated")
         text.onTextChange(edtFirstName, edtLateName) { text, id ->
            txtSuggestName.text = resources.getString(R.string.suggest_name)
            txtSuggestName.setTextColor(Color.GRAY)
            edtFirstName.isErrorEnabled = false
            edtLateName.isErrorEnabled = false
        }


        binding.btnConfirm.setOnClickListener {
            val firtName = edtFirstName.editText?.text.toString()
            val lateName = edtLateName.editText?.text.toString()
//            savedStateHandle?.set("firtName",firtName)
//            savedStateHandle?.set("lateName",lateName)
            if (firtName.isNotEmpty() && lateName.isNotEmpty()) {
                val user = User().apply {
                    name = "$firtName $lateName"
                }
                SendAndReceive.sendDataArgument(user, this)
            } else {
                txtSuggestName.text = resources.getString(R.string.suggest_name_error)
                txtSuggestName.setTextColor(Color.RED)
                if (firtName.isEmpty()) {
                    edtFirstName.isErrorEnabled = false
                    edtFirstName.error = " "
                }
                if (lateName.isEmpty()) {
                    edtLateName.isErrorEnabled = false
                    edtLateName.error = " "
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        Log.d("sssssss", "onDestroy")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("sssssss", "onDestroyView")

    }

    override fun onResume() {
        super.onResume()
        Log.d("sssssss", "onResume")

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

            Log.d("sssssss", "onViewStateRestored" +savedStateHandle?.get<String>("lateName") )

    }
    override fun initView() {
        edtFirstName = binding.edtFirstName
        edtLateName = binding.edtLateName
        txtSuggestName = binding.txtSuggestName
    }


}