package com.bivashmallick.whatsappclone.data.model

import java.util.Date

data class Chat(
    val id: String,
    val participants: List<String>,
    val lastMessage: Message? = null,
    val unreadCount: Int = 0,
    val timestamp: Date = Date(),
    val isGroup: Boolean = false,
    val groupName: String? = null,
    val groupImage: String? = null
) 