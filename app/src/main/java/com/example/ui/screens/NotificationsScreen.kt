package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppUiState
import com.example.data.ConnectXViewModel
import com.example.data.NotificationItem
import com.example.data.NotificationType
import com.example.data.ScreenNav
import com.example.ui.components.AvatarImage
import com.example.ui.theme.CommentBlue
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.LikeRed
import com.example.ui.theme.PinkAccent
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.ShareGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    state: AppUiState,
    viewModel: ConnectXViewModel,
    modifier: Modifier = Modifier
) {
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filters = listOf("All", "Requests", "Likes", "Comments", "Groups")

    val filteredNotifications = state.notifications.filter { notif ->
        when (selectedFilterIndex) {
            1 -> notif.type == NotificationType.FRIEND_REQUEST
            2 -> notif.type == NotificationType.LIKE
            3 -> notif.type == NotificationType.COMMENT
            4 -> notif.type == NotificationType.GROUP_INVITE || notif.type == NotificationType.EVENT
            else -> true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with Mark all as read
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (state.unreadNotificationsCount > 0) {
                OutlinedButton(
                    onClick = { viewModel.markAllNotificationsAsRead() },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Mark all read", fontSize = 12.sp, color = IndigoPrimary, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Filter Chips Row
        PrimaryTabRow(
            selectedTabIndex = selectedFilterIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = IndigoPrimary,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedFilterIndex),
                    color = IndigoPrimary,
                    width = 36.dp
                )
            }
        ) {
            filters.forEachIndexed { index, filter ->
                Tab(
                    selected = selectedFilterIndex == index,
                    onClick = { selectedFilterIndex = index },
                    text = {
                        Text(
                            text = filter,
                            fontSize = 12.sp,
                            fontWeight = if (selectedFilterIndex == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedFilterIndex == index) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        // Notifications List
        if (filteredNotifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = IndigoPrimary.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No notifications in this tab",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredNotifications, key = { it.id }) { notif ->
                    NotificationItemCard(
                        item = notif,
                        onClick = {
                            viewModel.markNotificationRead(notif.id)
                            when (notif.type) {
                                NotificationType.FRIEND_REQUEST -> viewModel.navigateTo(ScreenNav.Friends)
                                NotificationType.GROUP_INVITE -> viewModel.navigateTo(ScreenNav.Groups)
                                NotificationType.LIKE, NotificationType.COMMENT -> {
                                    if (notif.targetPostId != null) {
                                        viewModel.openComments(notif.targetPostId)
                                    }
                                }
                                else -> {}
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationItemCard(
    item: NotificationItem,
    onClick: () -> Unit
) {
    val (typeIcon, iconColor, iconBg) = when (item.type) {
        NotificationType.LIKE -> Triple(Icons.Default.Favorite, Color.White, LikeRed)
        NotificationType.COMMENT -> Triple(Icons.Default.ChatBubble, Color.White, CommentBlue)
        NotificationType.FRIEND_REQUEST -> Triple(Icons.Default.PersonAdd, Color.White, IndigoPrimary)
        NotificationType.GROUP_INVITE -> Triple(Icons.Default.Group, Color.White, PurpleAccent)
        NotificationType.EVENT -> Triple(Icons.Default.CalendarToday, Color.White, CyanAccent)
        NotificationType.SHARE -> Triple(Icons.Default.Repeat, Color.White, ShareGreen)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!item.isRead) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with Type Icon Badge Overlay
            Box(modifier = Modifier.size(52.dp)) {
                AvatarImage(user = item.actor, size = 48.dp)

                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 2.dp, y = 2.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = typeIcon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(11.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)) {
                            append(item.actor.name)
                        }
                        append(" ")
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                            append(item.text)
                        }
                    },
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Text(
                    text = item.timeAgo,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            if (!item.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(IndigoPrimary)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}
