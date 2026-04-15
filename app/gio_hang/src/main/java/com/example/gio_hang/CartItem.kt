package com.example.gio_hang

import java.io.Serializable

data class CartItem(
    val name: String,
    val price: Long,
    val quantity: Int = 1
) : Serializable
