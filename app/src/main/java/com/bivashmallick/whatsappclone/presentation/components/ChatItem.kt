package com.bivashmallick.whatsappclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bivashmallick.whatsappclone.data.model.Chat
import com.bivashmallick.whatsappclone.data.model.Message
import com.bivashmallick.whatsappclone.data.model.SampleData
import com.bivashmallick.whatsappclone.data.model.User
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppIconGray
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGreen
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppTextSecondary
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppUnreadBadge
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {
    val lastMessage = chat.lastMessage ?: SampleData.getMessagesForChat(chat.id).lastOrNull()
    val otherUser = if (chat.isGroup) null else SampleData.getOtherUserForChat(chat)
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            ProfileImage(
                imageUrl = if (chat.isGroup) chat.groupImage else otherUser?.profileImage,
                isOnline = otherUser?.isOnline == true
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Chat Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Chat Name
                    Text(
                        text = if (chat.isGroup) chat.groupName ?: "Group" else otherUser?.name ?: "Unknown",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    // Timestamp
                    Text(
                        text = lastMessage?.let { formatChatTimestamp(it.timestamp) } ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (chat.unreadCount > 0) WhatsAppLightGreen else WhatsAppTextSecondary,
                        fontSize = 12.sp
                    )
                }
                
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // If message is from current user, show the delivery status icon
                    if (lastMessage?.senderId == SampleData.currentUser.id) {
                        Icon(
                            imageVector = if (lastMessage.isRead) Icons.Default.DoneAll else Icons.Default.Done,
                            contentDescription = if (lastMessage.isRead) "Read" else "Delivered",
                            modifier = Modifier.size(16.dp),
                            tint = if (lastMessage.isRead) WhatsAppLightGreen else WhatsAppIconGray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    
                    // Last Message
                    Text(
                        text = lastMessage?.content ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = WhatsAppTextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Unread Count Badge
                    if (chat.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(WhatsAppUnreadBadge),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chat.unreadCount.toString(),
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileImage(
    imageUrl: String?,
    isOnline: Boolean = false,
    size: Int = 56
) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl ?: "https://via.placeholder.com/150"
            ),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        if (isOnline) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp, end = 4.dp)
                    .size(13.dp)
                    .clip(CircleShape)
                    .background(WhatsAppLightGreen)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

fun formatChatTimestamp(date: Date): String {
    val calendar = java.util.Calendar.getInstance()
    val now = calendar.time
    
    calendar.time = date
    val messageYear = calendar.get(java.util.Calendar.YEAR)
    val messageDay = calendar.get(java.util.Calendar.DAY_OF_YEAR)
    
    calendar.time = now
    val currentYear = calendar.get(java.util.Calendar.YEAR)
    val currentDay = calendar.get(java.util.Calendar.DAY_OF_YEAR)
    
    return when {
        // Today
        currentYear == messageYear && currentDay == messageDay -> {
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            format.format(date)
        }
        // Yesterday
        currentYear == messageYear && currentDay - messageDay == 1 -> {
            "Yesterday"
        }
        // This week
        currentYear == messageYear && currentDay - messageDay < 7 -> {
            val format = SimpleDateFormat("EEE", Locale.getDefault())
            format.format(date)
        }
        // Same year
        currentYear == messageYear -> {
            val format = SimpleDateFormat("dd/MM", Locale.getDefault())
            format.format(date)
        }
        // Different year
        else -> {
            val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            format.format(date)
        }
    }
} 