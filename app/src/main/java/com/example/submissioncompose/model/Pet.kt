package com.example.submissioncompose.model

data class Pet(
    val id: Int,
    val name: String,
    val breed: String,
    val image: Int,
    val description: String,
    var isFavorite: Boolean = false
)