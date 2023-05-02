package com.example.messenger.contract.account

interface CrPasswordContract {
    interface View {
        fun success()
        fun failed(message: String)
    }

    interface Presenter {
        fun confirm(password: String)
    }
}