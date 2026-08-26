package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppUiState
import com.example.data.ConnectXViewModel
import com.example.data.ScreenNav
import com.example.ui.components.FriendListItemCard
import com.example.ui.components.FriendRequestCard
import com.example.ui.components.SuggestedFriendCard
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.PinkAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    state: AppUiState,
    viewModel: ConnectXViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf("All Friends (${state.friendsList.size})", "Requests (${state.pendingRequestsCount})", "Suggestions")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search friends by name or role...", fontSize = 14.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(18.dp))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = IndigoPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Tabs
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = IndigoPrimary,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTab),
                    color = IndigoPrimary,
                    width = 48.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> {
                // All Friends List
                val filteredFriends = state.friendsList.filter {
                    it.name.contains(searchQuery, ignoreCase = true) || it.role.contains(searchQuery, ignoreCase = true)
                }

                if (filteredFriends.isEmpty()) {
                    EmptyFriendsState(message = if (searchQuery.isEmpty()) "No friends yet. Add suggestions below!" else "No friends matching '$searchQuery'")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredFriends, key = { it.id }) { friend ->
                            FriendListItemCard(
                                user = friend,
                                onMessageClick = {
                                    // Find or create conversation
                                    val conv = state.conversations.find { it.participant.id == friend.id }
                                    if (conv != null) {
                                        viewModel.openChat(conv.id)
                                    } else {
                                        viewModel.navigateTo(ScreenNav.Messages)
                                    }
                                },
                                onRemoveClick = { viewModel.removeFriend(friend.id) },
                                onClick = { viewModel.navigateTo(ScreenNav.Profile) }
                            )
                        }
                    }
                }
            }
            1 -> {
                // Friend Requests
                val pendingRequests = state.friendRequests.filter { it.status == com.example.data.RequestStatus.PENDING }

                if (pendingRequests.isEmpty()) {
                    EmptyFriendsState(message = "No pending friend requests at this time ✨")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.friendRequests, key = { it.id }) { req ->
                            FriendRequestCard(
                                request = req,
                                onAccept = { viewModel.acceptFriendRequest(req.id) },
                                onDecline = { viewModel.declineFriendRequest(req.id) },
                                onClick = { viewModel.navigateTo(ScreenNav.Profile) }
                            )
                        }
                    }
                }
            }
            2 -> {
                // Suggestions Grid / List
                val filteredSuggestions = state.suggestedFriends.filter {
                    it.name.contains(searchQuery, ignoreCase = true) || it.role.contains(searchQuery, ignoreCase = true)
                }

                if (filteredSuggestions.isEmpty()) {
                    EmptyFriendsState(message = "All suggestions caught up! Check back later.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredSuggestions, key = { it.id }) { user ->
                            FriendListItemCard(
                                user = user,
                                onMessageClick = { viewModel.addSuggestedFriend(user) },
                                onRemoveClick = {},
                                onClick = { viewModel.navigateTo(ScreenNav.Profile) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyFriendsState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = null,
                tint = IndigoPrimary.copy(alpha = 0.5f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
