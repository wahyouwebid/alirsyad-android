package com.alirsyad.app.data.model.register

data class RegisterParams(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val passwordConfirmation: String,
    val jenjangId: Int,
)
