package com.example.messenger.model.entity

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.Timestamp
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*


//type txt ìmg file ghiam
open class Message @RequiresApi(Build.VERSION_CODES.O) constructor(
    val senderUid: String = "",
    val timeSend: Long,
    val uidReading: MutableList<String> = mutableListOf(),
    val body: String = "",
    val deleteBy: MutableList<String> = mutableListOf(),
    val removeBy: MutableList<String> = mutableListOf(),
    val type: String = ""
){
    init {
    }
}
