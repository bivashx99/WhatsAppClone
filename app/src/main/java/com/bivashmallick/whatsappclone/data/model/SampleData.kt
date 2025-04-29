package com.bivashmallick.whatsappclone.data.model

import java.util.Date
import java.util.UUID
import kotlin.random.Random

object SampleData {
    // Sample users
    val currentUser = User(
        id = "current_user_id",
        name = "You",
        phoneNumber = "+1 234 567 8900",
        profileImage = "https://images.unsplash.com/photo-1599566150163-29194dcaad36"
    )
    
    val users = listOf(
        User(
            id = "user1",
            name = "John Smith",
            phoneNumber = "+1 234 567 8901",
            status = "At work",
            profileImage = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d",
            isOnline = true
        ),
        User(
            id = "user2",
            name = "Maria Garcia",
            phoneNumber = "+1 234 567 8902",
            status = "Can't talk, WhatsApp only",
            profileImage = "https://images.unsplash.com/photo-1494790108377-be9c29b29330",
            isOnline = false,
            lastSeen = System.currentTimeMillis() - 20 * 60 * 1000 // 20 minutes ago
        ),
        User(
            id = "user3",
            name = "David Johnson",
            phoneNumber = "+1 234 567 8903",
            profileImage = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e",
            isOnline = true
        ),
        User(
            id = "user4",
            name = "Sophie Williams",
            phoneNumber = "+1 234 567 8904",
            status = "Living life!",
            profileImage = "https://images.unsplash.com/photo-1544005313-94ddf0286df2",
            isOnline = false,
            lastSeen = System.currentTimeMillis() - 2 * 60 * 60 * 1000 // 2 hours ago
        ),
        User(
            id = "user5",
            name = "Michael Brown",
            phoneNumber = "+1 234 567 8905",
            profileImage = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d",
            isOnline = false,
            lastSeen = System.currentTimeMillis() - 30 * 60 * 1000 // 30 minutes ago
        ),
        User(
            id = "user6",
            name = "Design Team",
            phoneNumber = "",
            status = "Group chat for design updates",
            profileImage = "https://images.unsplash.com/photo-1559127545-95b428994bb5",
        ),
        User(
            id = "user7",
            name = "Family",
            phoneNumber = "",
            status = "Family group chat",
            profileImage = "https://images.unsplash.com/photo-1517705008128-361805f42e86",
        )
    )
    
    // Get a user by ID
    fun getUserById(id: String): User? = users.find { it.id == id }
    
    // Sample messages
    private fun createMessages(chat: Chat): List<Message> {
        val messages = mutableListOf<Message>()
        val otherUserId = if (chat.isGroup) chat.id else chat.participants.first { it != currentUser.id }
        
        // Generate random number of messages between 5 and 15
        val numberOfMessages = Random.nextInt(5, 16)
        
        // Base time for messages (going back a few days)
        var baseTime = System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000 // 4 days ago
        
        for (i in 1..numberOfMessages) {
            // Randomly decide if message is from current user or the other participant
            val isFromCurrentUser = Random.nextBoolean()
            val senderId = if (isFromCurrentUser) currentUser.id else otherUserId
            val receiverId = if (isFromCurrentUser) otherUserId else currentUser.id
            
            // Increment time randomly between 5 minutes and 5 hours
            baseTime += Random.nextLong(5 * 60 * 1000, 5 * 60 * 60 * 1000)
            
            // Create message content based on sample templates
            val content = when (Random.nextInt(7)) {
                0 -> "Hey, how are you doing?"
                1 -> "Did you see the news today?"
                2 -> "Can we meet tomorrow?"
                3 -> "I'll send you the details later."
                4 -> "Thanks for your help!"
                5 -> if (chat.isGroup) "Has everyone seen the latest update?" else "Let me know when you're free."
                else -> "👋 Hello!"
            }
            
            // Determine if message is read (more recent messages may be unread)
            val isRead = i < numberOfMessages - Random.nextInt(0, 3)
            
            // Add message to the list
            messages.add(
                Message(
                    id = UUID.randomUUID().toString(),
                    senderId = senderId,
                    receiverId = receiverId,
                    content = content,
                    timestamp = Date(baseTime),
                    isRead = isRead
                )
            )
        }
        
        // Sort messages by timestamp (oldest first)
        return messages.sortedBy { it.timestamp }
    }
    
    // Sample chats
    val chats = listOf(
        Chat(
            id = "chat1",
            participants = listOf(currentUser.id, "user1"),
            timestamp = Date(System.currentTimeMillis() - 20 * 60 * 1000), // 20 minutes ago
            unreadCount = 3
        ),
        Chat(
            id = "chat2",
            participants = listOf(currentUser.id, "user2"),
            timestamp = Date(System.currentTimeMillis() - 2 * 60 * 60 * 1000), // 2 hours ago
            unreadCount = 0
        ),
        Chat(
            id = "chat3",
            participants = listOf(currentUser.id, "user3"),
            timestamp = Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000), // 1 day ago
            unreadCount = 0
        ),
        Chat(
            id = "chat4",
            participants = listOf(currentUser.id, "user4"),
            timestamp = Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000), // 3 days ago
            unreadCount = 0
        ),
        Chat(
            id = "chat5",
            participants = listOf(currentUser.id, "user5"),
            timestamp = Date(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000), // 5 days ago
            unreadCount = 0
        ),
        Chat(
            id = "group1",
            participants = listOf(currentUser.id, "user1", "user2", "user3"),
            isGroup = true,
            groupName = "Design Team",
            groupImage = "https://images.unsplash.com/photo-1559127545-95b428994bb5",
            timestamp = Date(System.currentTimeMillis() - 12 * 60 * 60 * 1000), // 12 hours ago
            unreadCount = 5
        ),
        Chat(
            id = "group2",
            participants = listOf(currentUser.id, "user1", "user4", "user5"),
            isGroup = true,
            groupName = "Family",
            groupImage = "https://images.unsplash.com/photo-1517705008128-361805f42e86",
            timestamp = Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000), // 2 days ago
            unreadCount = 0
        )
    ).sortedByDescending { it.timestamp }
    
    val chatMessages = chats.associateWith { createMessages(it) }
    
    // Function to get chat details by ID
    fun getChatById(chatId: String): Chat? = chats.find { it.id == chatId }
    
    // Function to get messages for a specific chat
    fun getMessagesForChat(chatId: String): List<Message> = chatMessages[getChatById(chatId)] ?: emptyList()
    
    // Function to get the other user in a one-on-one chat
    fun getOtherUserForChat(chat: Chat): User? {
        if (chat.isGroup) return null
        val otherUserId = chat.participants.first { it != currentUser.id }
        return getUserById(otherUserId)
    }
} 