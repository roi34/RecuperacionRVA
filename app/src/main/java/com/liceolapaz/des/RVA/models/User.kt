package com.liceolapaz.des.RVA.models

data class User(
    val email: String,
    val password: String,
    val language: String,
    val age: Int,
    val name: String
)