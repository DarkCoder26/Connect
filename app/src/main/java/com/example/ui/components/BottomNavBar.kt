package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ScreenNav
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.LikeRed
import com.example.ui.theme.PinkAccent

@Composable
fun BottomNavBar(
    currentScreen: ScreenNav,
    pendingRequestsCount: Int,
    unreadMessagesCount: Int,
    onNavigate: (ScreenNav) -> Unit,
    onCreatePostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home Tab
                BottomNavItem(
                    label = "Home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    isSelected = currentScreen is ScreenNav.Home,
                    onClick = { onNavigate(ScreenNav.Home) }
                )

                // Friends Tab
                BottomNavItem(
                    label = "Friends",
                    selectedIcon = Icons.Filled.People,
                    unselectedIcon = Icons.Outlined.PeopleOutline,
                    isSelected = currentScreen is ScreenNav.Friends,
                    badgeCount = pendingRequestsCount,
                    onClick = { onNavigate(ScreenNav.Friends) }
                )

                // Prominent Create Post Squircle in Center
                Box(
                    modifier = Modifier
                        .offset(y = (-10).dp)
                        .size(48.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = IndigoPrimary)
                        .clip(RoundedCornerShape(16.dp))
                        .background(IndigoPrimary)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onCreatePostClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Post",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Messages / Chats Tab
                BottomNavItem(
                    label = "Chats",
                    selectedIcon = Icons.Filled.ChatBubble,
                    unselectedIcon = Icons.Outlined.ChatBubbleOutline,
                    isSelected = currentScreen is ScreenNav.Messages,
                    badgeCount = unreadMessagesCount,
                    onClick = { onNavigate(ScreenNav.Messages) }
                )

                // Profile Tab
                BottomNavItem(
                    label = "Profile",
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.PersonOutline,
                    isSelected = currentScreen is ScreenNav.Profile,
                    onClick = { onNavigate(ScreenNav.Profile) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    val activeColor = IndigoPrimary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BadgedBox(
            badge = {
                if (badgeCount > 0) {
                    Badge(
                        containerColor = LikeRed,
                        contentColor = Color.White
                    ) {
                        Text(text = if (badgeCount > 9) "9+" else badgeCount.toString(), fontSize = 9.sp)
                    }
                }
            }
        ) {
            Icon(
                imageVector = if (isSelected) selectedIcon else unselectedIcon,
                contentDescription = label,
                tint = if (isSelected) activeColor else inactiveColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isSelected) activeColor else inactiveColor,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
