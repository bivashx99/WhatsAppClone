package com.bivashmallick.whatsappclone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bivashmallick.whatsappclone.data.model.Message
import com.bivashmallick.whatsappclone.data.model.SampleData
import com.bivashmallick.whatsappclone.presentation.components.ChatDetailTopBar
import com.bivashmallick.whatsappclone.presentation.components.ChatInputField
import com.bivashmallick.whatsappclone.presentation.components.MessageItem
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppChatBackground
import java.util.Date
import java.util.UUID

@Composable
fun ChatDetailScreen(
    chatId: String,
    navController: NavController
) {
    val chat = remember(chatId) { SampleData.getChatById(chatId) }
    val messages = remember(chatId) { SampleData.getMessagesForChat(chatId).toMutableList() }
    var currentMessages by remember { mutableStateOf(messages) }
    
    chat?.let {
        val user = if (it.isGroup) {
            SampleData.getUserById("user6")?.copy(
                name = it.groupName ?: "Group",
                profileImage = it.groupImage
            )
        } else {
            SampleData.getOtherUserForChat(it)
        } ?: return@let
        
        val listState = rememberLazyListState()
        
        // Scroll to bottom when opening chat or when new message is added
        LaunchedEffect(currentMessages.size) {
            if (currentMessages.isNotEmpty()) {
                listState.animateScrollToItem(currentMessages.size - 1)
            }
        }
        
        Scaffold(
            topBar = {
                ChatDetailTopBar(
                    user = user,
                    onBackClick = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(WhatsAppChatBackground)
            ) {
                // Chat background with faint pattern
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://i.pinimg.com/originals/ab/ab/60/abab60f06ab52fa7846593e6ae0b9a96.png"
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.2f),
                    contentScale = ContentScale.FillBounds
                )
                
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Messages
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        items(currentMessages) { message ->
                            val isFromCurrentUser = message.senderId == SampleData.currentUser.id
                            MessageItem(
                                message = message,
                                isFromCurrentUser = isFromCurrentUser
                            )
                        }
                    }
                    
                    // Chat input field
                    ChatInputField(
                        onSendMessage = { content ->
                            val newMessage = Message(
                                id = UUID.randomUUID().toString(),
                                senderId = SampleData.currentUser.id,
                                receiverId = user.id,
                                content = content,
                                timestamp = Date(),
                                isRead = false
                            )
                            currentMessages = (currentMessages + newMessage).toMutableList()
                        }
                    )
                }
            }
        }
    } ?: run {
        // If chat is not found
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Chat not found!")
        }
    }
} 