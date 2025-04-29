package com.bivashmallick.whatsappclone.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bivashmallick.whatsappclone.data.model.SampleData
import com.bivashmallick.whatsappclone.presentation.components.ChatItem
import com.bivashmallick.whatsappclone.presentation.navigation.navigateToChatDetail
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGray

@Composable
fun ChatsTabScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(SampleData.chats) { chat ->
                ChatItem(
                    chat = chat,
                    onClick = {
                        navController.navigateToChatDetail(chat.id)
                    }
                )
                
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 16.dp),
                    color = WhatsAppLightGray.copy(alpha = 0.5f),
                    thickness = 0.5.dp
                )
            }
        }
    }
} 