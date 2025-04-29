package com.bivashmallick.whatsappclone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppGreen
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppIconGray
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightBackground
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGray

@Composable
fun ChatInputField(
    onSendMessage: (String) -> Unit,
    onAttachmentClick: () -> Unit = {},
    onCameraClick: () -> Unit = {}
) {
    var messageText by rememberSaveable { mutableStateOf("") }
    val isMessageEmpty = messageText.isBlank()
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Attachment Button
        IconButton(
            onClick = onAttachmentClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = "Attach",
                tint = WhatsAppIconGray
            )
        }
        
        // Camera Button
        IconButton(
            onClick = onCameraClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Camera,
                contentDescription = "Camera",
                tint = WhatsAppIconGray
            )
        }
        
        // Text Field
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            placeholder = { Text("Message") },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WhatsAppGreen,
                unfocusedBorderColor = WhatsAppLightGray,
                focusedContainerColor = WhatsAppLightBackground,
                unfocusedContainerColor = WhatsAppLightBackground
            ),
            maxLines = 4
        )
        
        // Send/Mic Button
        if (isMessageEmpty) {
            IconButton(
                onClick = { /* Voice recording */ },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(WhatsAppGreen)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Message",
                    tint = WhatsAppLightBackground
                )
            }
        } else {
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        onSendMessage(messageText)
                        messageText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(WhatsAppGreen)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message",
                    tint = WhatsAppLightBackground
                )
            }
        }
    }
} 