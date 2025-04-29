package com.bivashmallick.whatsappclone.data.model

data class User(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val status: String = "Hey there! I am using WhatsApp.",
    val profileImage: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0
) 