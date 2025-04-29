package com.bivashmallick.whatsappclone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bivashmallick.whatsappclone.data.model.SampleData
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppGreen
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppIconGray
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGray
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppTextSecondary
import kotlin.random.Random

data class CallLog(
    val userId: String,
    val isVideo: Boolean,
    val callDirection: CallDirection,
    val timestamp: String
)

enum class CallDirection {
    INCOMING,
    OUTGOING,
    MISSED
}

@Composable
fun CallsTabScreen() {
    // Generate some sample call logs
    val callLogs = List(10) { index ->
        val userId = "user${(index % 5) + 1}"
        val isVideo = Random.nextBoolean()
        val direction = CallDirection.values()[Random.nextInt(CallDirection.values().size)]
        val timestamp = when {
            index < 3 -> "Today, ${10 + index}:30"
            index < 6 -> "Yesterday, ${14 + index % 3}:45"
            else -> "${index - 5} days ago"
        }
        
        CallLog(userId, isVideo, direction, timestamp)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(callLogs) { callLog ->
                val user = SampleData.getUserById(callLog.userId) ?: return@items
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // User profile image
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = user.profileImage
                        ),
                        contentDescription = "Contact Image",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    // Call details
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(2.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Call direction icon
                            val (icon, iconTint) = when (callLog.callDirection) {
                                CallDirection.INCOMING -> Icons.Default.CallReceived to WhatsAppGreen
                                CallDirection.OUTGOING -> Icons.Default.CallMade to WhatsAppGreen
                                CallDirection.MISSED -> Icons.Default.CallMissed to MaterialTheme.colorScheme.error
                            }
                            
                            Icon(
                                imageVector = icon,
                                contentDescription = callLog.callDirection.name,
                                tint = iconTint,
                                modifier = Modifier.size(14.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(4.dp))
                            
                            Text(
                                text = callLog.timestamp,
                                style = MaterialTheme.typography.bodyMedium,
                                color = WhatsAppTextSecondary
                            )
                        }
                    }
                    
                    // Call Button
                    Icon(
                        imageVector = if (callLog.isVideo) Icons.Default.Videocam else Icons.Default.Phone,
                        contentDescription = if (callLog.isVideo) "Video Call" else "Voice Call",
                        tint = WhatsAppGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 88.dp, end = 16.dp),
                    color = WhatsAppLightGray.copy(alpha = 0.5f),
                    thickness = 0.5.dp
                )
            }
        }
    }
} 