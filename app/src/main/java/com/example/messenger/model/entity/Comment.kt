package com.example.messenger.model.entity

data class Comment(
    var uid: String = "",
    var feedbackUid: String = "",
    var feeling: Feeling = Feeling ()
)