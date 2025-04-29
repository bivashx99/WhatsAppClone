package com.bivashmallick.whatsappclone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bivashmallick.whatsappclone.data.model.SampleData
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGray
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppLightGreen
import com.bivashmallick.whatsappclone.ui.theme.WhatsAppTextSecondary

@Composable
fun StatusTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // My Status
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image with Add button
            Box {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = SampleData.currentUser.profileImage
                    ),
                    contentDescription = "My Status",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(2.dp, WhatsAppLightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(WhatsAppLightGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Status",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = "My Status",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = "Tap to add status update",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WhatsAppTextSecondary
                )
            }
        }
        
        Divider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            color = WhatsAppLightGray.copy(alpha = 0.5f)
        )
        
        // Recent Updates Text
        Text(
            text = "Recent updates",
            style = MaterialTheme.typography.bodyMedium,
            color = WhatsAppTextSecondary,
            modifier = Modifier.padding(16.dp)
        )
        
        // Status List (sample status updates from contacts)
        LazyColumn {
            items(SampleData.users.filter { it.id.startsWith("user") }.take(3)) { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Status Circle
                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(model = user.profileImage),
                            contentDescription = "${user.name}'s Status",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(2.dp, WhatsAppLightGreen, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(2.dp))
                        
                        Text(
                            text = "Today, ${(10 + user.id.hashCode() % 12)}:${30 + user.id.hashCode() % 30}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = WhatsAppTextSecondary
                        )
                    }
                }
                
                if (user != SampleData.users.last()) {
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
} 