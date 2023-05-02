package com.example.messenger.view.activity.account

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.messenger.contract.account.LoginContract
import com.example.messenger.databinding.ActivityMainBinding
import com.example.messenger.interaction.activity.LoginActivityInteraction
import com.example.messenger.listens.WifiStateListener
import com.example.messenger.presenter.activity.account.LoginActivityPresenter
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.sql.Timestamp
import java.util.*


open class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var edtAccount: TextInputLayout
    private lateinit var edtPassword: TextInputLayout
    private lateinit var btnLogin: Button
    private lateinit var txtForgotPassword: TextView
    private lateinit var btnCreateAccount: Button
    private lateinit var binding: ActivityMainBinding
    private val myLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // handle the result

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)  // Khởi tạo biến binding
        setContentView(binding.root)
        initView()


        textChangedListener(edtAccount);
        textChangedListener(edtPassword);
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
      //  myLauncher.launch(intent)

        val loginPresenter = LoginActivityPresenter(this, LoginActivityInteraction())
        btnLogin.setOnClickListener {
            loginPresenter.login(
                edtAccount.editText?.text.toString(),
                edtPassword.editText?.text.toString()
            )
        }
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, CreateAccountAcitivity::class.java)
            startActivity(intent)
        }
        txtForgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        val user = FirebaseAuth.getInstance().currentUser
        val newEmail = "newemail@example.com"
        val broadcastReceiver = WifiStateListener()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadcastReceiver, intentFilter)

 FirebaseDatabase.getInstance().reference.child("Messenger/Group").push().setValue( Timestamp(Date(System.currentTimeMillis()).time ).time)
        //  sendEmail("khangndph20612@fpt.edu.vn","2992003")
    }

    private fun textChangedListener(edtAccount: TextInputLayout) {
        edtAccount.editText?.addTextChangedListener {
            edtAccount.isErrorEnabled = false
        }

    }

    override fun showLoginError(message: String) {
        when (message) {
            "all" -> {
                edtAccount.error = "Không bỏ trống email hoặc SĐT"
                edtPassword.error = "Không bỏ trống mật khẩu"
                edtAccount.isErrorEnabled = true
                edtPassword.isErrorEnabled = true
            }
            "account" -> {
                edtAccount.error = "Không bỏ trống email hoặc SĐT"
                edtAccount.isErrorEnabled = true

            }
            "password" -> {
                edtPassword.error = "Không bỏ trống mật khẩu"
                edtPassword.isErrorEnabled = true

            }
            "Email hoặc SĐT không đúng định dạng" -> {
                edtAccount.error = message
                edtAccount.isErrorEnabled = true

            }
            "Sai mật khẩu hoặc tài khoản" -> {
                edtAccount.error = message
                edtPassword.error = message
                edtAccount.isErrorEnabled = true
                edtPassword.isErrorEnabled = true
                Toast.makeText(
                    this@LoginActivity,
                    "Sai mật khẩu hoặc tài khoản",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun showLoginSuccess() {
    }


    private fun initView() {
        edtAccount = binding.edtAccount
        edtPassword = binding.edtPassword
        btnLogin = binding.btnLogin
        txtForgotPassword = binding.txtForgotPassword
        btnCreateAccount = binding.btnCreateAccount
    }

}