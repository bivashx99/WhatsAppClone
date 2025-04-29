package com.bivashmallick.whatsappclone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bivashmallick.whatsappclone.data.model.Message
import com.bivashmallick.whatsappclone.data.model.SampleData
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppIconGray
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGreen
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppMessageReceived
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppMessageSent
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppTextPrimary
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppTextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MessageItem(
    message: Message,
    isFromCurrentUser: Boolean
) {
    val configuration = LocalConfiguration.current
    val maxWidth = configuration.screenWidthDp * 0.75f
    
    val backgroundColor = if (isFromCurrentUser) WhatsAppMessageSent else WhatsAppMessageReceived
    val alignment = if (isFromCurrentUser) Alignment.End else Alignment.Start
    val shape = when {
        isFromCurrentUser -> RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
        else -> RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
    }

    Column(
        modifier = Modifier.padding(
            start = if (isFromCurrentUser) 64.dp else 8.dp,
            end = if (isFromCurrentUser) 8.dp else 64.dp,
            top = 4.dp,
            bottom = 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .align(alignment)
                .widthIn(max = maxWidth.dp)
                .clip(shape)
                .background(backgroundColor)
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = WhatsAppTextPrimary
                )
                
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatMessageTime(message.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = WhatsAppTextSecondary,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(end = if (isFromCurrentUser) 4.dp else 0.dp)
                    )
                    
                    if (isFromCurrentUser) {
                        Icon(
                            imageVector = if (message.isRead) Icons.Default.DoneAll else Icons.Default.Done,
                            contentDescription = if (message.isRead) "Read" else "Delivered",
                            modifier = Modifier.size(16.dp),
                            tint = if (message.isRead) WhatsAppLightGreen else WhatsAppIconGray
                        )
                    }
                }
            }
        }
    }
}

fun formatMessageTime(date: Date): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
} 