package com.example.messenger.model.entity


data class Post(
    var title: String = "",
    var message: String = "",
    var timestamp: String = "",
    var content: String = "",
    var feeling: Feeling = Feeling(),
    var comments: Map<String, Comment> = emptyMap()
)