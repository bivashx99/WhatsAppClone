package com.bivashmallick.whatsappclone.data.model

import java.util.Date

data class Message(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Date,
    val isRead: Boolean = false,
    val messageType: MessageType = MessageType.TEXT,
    val mediaUrl: String? = null
)

enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    AUDIO,
    DOCUMENT,
    LOCATION
} 