package com.example.messenger.view.activity.account


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.messenger.R
import java.io.File


class CreateAccountAcitivity : AppCompatActivity() {
    lateinit var navController: NavController

    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController )


        supportActionBar!!.apply {
            setBackgroundDrawable(
                ColorDrawable(resources.getColor(R.color.white, null))
            )
            elevation = 0F
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            val backArrow =
                resources.getDrawable(R.drawable.ic_baseline_keyboard_backspace_24, null)?.mutate()
            backArrow?.let {
                DrawableCompat.setTint(it, ContextCompat.getColor(this, R.color.black))
            }
            supportActionBar?.setHomeAsUpIndicator(backArrow)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //lắng nghe sự kiện khi back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!findNavController(R.id.fragmentContainerView).navigateUp()) {
                    finish()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Pop back stack to first fragment
                navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun createDialog(): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            title = "Bạn có muốn dừng tạo tài khoản không?"
            setNegativeButton(
                "Dừng tạo tài khoản"
            ) { p0, p1 -> finish() }
            setNegativeButton(
                "Tiếp tục"
            ) { p0, p1 -> p0.dismiss() }
        }
        return dialog;
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp()||super.onSupportNavigateUp()
    }

}