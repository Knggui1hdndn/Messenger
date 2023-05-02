package com.example.messenger.listens

import android.annotation.SuppressLint
import android.app.usage.NetworkStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class WifiStateListener : BroadcastReceiver() {
    private val TAG: String="ffffffffff"

    companion object {
        var state: Boolean = false
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //cm.activateNetwork  là phương thức trả về đối tượng mạng hiện tại đang được sử dụng.
        // Nó trả về một đối tượng Network hoặc null nếu không có mạng nào được kích hoạt.
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        // là phương thức của đối tượng ConnectivityManager, nó được sử dụng để
        // truy xuất thông tin về khả năng kết nối mạng của thiết bị, bao gồm cả khả năng kết nối internet.
        if (capabilities != null) {
            state = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (state) {
                Toast.makeText(context, "Đã có mạng trở lại", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Mạng không truy cập được Internet", Toast.LENGTH_LONG).show()
            }
        } else {
            state = false
            Toast.makeText(context, "Vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_LONG).show()
        }

    }
}