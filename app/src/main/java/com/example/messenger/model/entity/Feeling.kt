package com.example.messenger.model.entity

//Thích: Like
//Thả tim: Heart/react/like
//Thương thương: Love/care
//Haha: Haha/laugh
//Ngạc nhiên: Surprised
//Buồn: Sad
//Phẫn nộ: Angry
data class Feeling(
    var like: MutableList<String>? =  mutableListOf(),
    var heart: MutableList<String>? =  mutableListOf(),
    var love: MutableList<String>? =  mutableListOf(),
    var laugh: MutableList<String>? =  mutableListOf(),
    var surprised: MutableList<String>? =  mutableListOf(),
    var sad: MutableList<String>? =  mutableListOf(),
    var angry: MutableList<String>? =  mutableListOf(),
) {
 }
