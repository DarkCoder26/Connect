package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppUiState
import com.example.data.ConnectXViewModel
import com.example.data.ScreenNav
import com.example.ui.components.FriendListItemCard
import com.example.ui.components.GroupCard
import com.example.ui.components.PostCard
import com.example.ui.theme.IndigoPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    state: AppUiState,
    viewModel: ConnectXViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf(state.searchQuery) }
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filters = listOf("All", "Posts", "People", "Groups", "Tags")

    val trendingTags = listOf("#DesignTokens", "#AndroidDev", "#ConnectX", "#StartupSprint", "#UIUX", "#KotlinCompose", "#MobileArchitecture")

    // Results matching
    val matchingPosts = state.posts.filter {
        it.text.contains(searchQuery, ignoreCase = true) ||
                it.author.name.contains(searchQuery, ignoreCase = true) ||
                it.tags.any { tag -> tag.contains(searchQuery, ignoreCase = true) }
    }

    val matchingPeople = (state.friendsList + state.suggestedFriends).distinctBy { it.id }.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.role.contains(searchQuery, ignoreCase = true) ||
                it.handle.contains(searchQuery, ignoreCase = true)
    }

    val matchingGroups = state.groups.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search Input Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.updateSearchQuery(it)
                    },
                    placeholder = { Text("Search ConnectX...", fontSize = 14.sp) },
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
                            IconButton(onClick = {
                                searchQuery = ""
                                viewModel.updateSearchQuery("")
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .testTag("search_bar_input"),
                    shape = RoundedCornerShape(22.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndigoPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }

        // Filter Pills
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEachIndexed { index, filter ->
                FilterChip(
                    selected = selectedFilterIndex == index,
                    onClick = { selectedFilterIndex = index },
                    label = { Text(filter, fontSize = 12.sp, fontWeight = if (selectedFilterIndex == index) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = IndigoPrimary,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
            }
        }

        if (searchQuery.isBlank()) {
            // Trending Tags & Discovery Hub
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(20.dp))
                    Text("Trending Topics", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    trendingTags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface,
                            shadowElevation = 0.5.dp,
                            modifier = Modifier.clickable {
                                searchQuery = tag
                                viewModel.updateSearchQuery(tag)
                            }
                        ) {
                            Text(
                                text = tag,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = IndigoPrimary,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // Search Results
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // People results
                if (selectedFilterIndex == 0 || selectedFilterIndex == 2) {
                    if (matchingPeople.isNotEmpty()) {
                        item {
                            Text(
                                text = "People (${matchingPeople.size})",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 8.dp)
                            )
                        }
                        items(matchingPeople, key = { "person_${it.id}" }) { person ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                FriendListItemCard(
                                    user = person,
                                    onMessageClick = {
                                        val conv = state.conversations.find { it.participant.id == person.id }
                                        if (conv != null) viewModel.openChat(conv.id)
                                    },
                                    onRemoveClick = {},
                                    onClick = { viewModel.navigateTo(ScreenNav.Profile) }
                                )
                            }
                        }
                    }
                }

                // Group results
                if (selectedFilterIndex == 0 || selectedFilterIndex == 3) {
                    if (matchingGroups.isNotEmpty()) {
                        item {
                            Text(
                                text = "Groups (${matchingGroups.size})",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 8.dp)
                            )
                        }
                        items(matchingGroups, key = { "group_${it.id}" }) { group ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                GroupCard(
                                    group = group,
                                    onToggleJoin = { viewModel.toggleGroupMembership(group.id) },
                                    onClick = {}
                                )
                            }
                        }
                    }
                }

                // Post results
                if (selectedFilterIndex == 0 || selectedFilterIndex == 1 || selectedFilterIndex == 4) {
                    if (matchingPosts.isNotEmpty()) {
                        item {
                            Text(
                                text = "Posts (${matchingPosts.size})",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 8.dp)
                            )
                        }
                        items(matchingPosts, key = { "post_${it.id}" }) { post ->
                            PostCard(
                                post = post,
                                onLikeClick = { viewModel.toggleLike(post.id) },
                                onCommentClick = { viewModel.openComments(post.id) },
                                onShareClick = { viewModel.openShare(post) },
                                onSaveClick = { viewModel.toggleSave(post.id) },
                                onPollVote = { opt -> viewModel.votePoll(post.id, opt) },
                                onAuthorClick = { viewModel.navigateTo(ScreenNav.Profile) }
                            )
                        }
                    }
                }

                if (matchingPosts.isEmpty() && matchingPeople.isEmpty() && matchingGroups.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No results found for '$searchQuery'",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
