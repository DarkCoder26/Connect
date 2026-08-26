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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppUiState
import com.example.data.ConnectXViewModel
import com.example.data.ScreenNav
import com.example.ui.components.CreatePostCard
import com.example.ui.components.EventCard
import com.example.ui.components.GroupCard
import com.example.ui.components.PostCard
import com.example.ui.components.StoryTray
import com.example.ui.components.SuggestedFriendCard
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.PinkAccent

@Composable
fun HomeScreen(
    state: AppUiState,
    viewModel: ConnectXViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // 1. Stories Horizontal Tray
        item {
            StoryTray(
                stories = state.stories,
                currentUser = state.currentUser,
                onStoryClick = { viewModel.openStory(it) },
                onCreateStoryClick = { viewModel.setCreatePostOpen(true) }
            )
        }

        // 2. Create Post Quick Card
        item {
            CreatePostCard(
                currentUser = state.currentUser,
                onClick = { viewModel.setCreatePostOpen(true) }
            )
        }

        // 3. Feed Posts & Inline Widgets
        itemsIndexed(state.posts, key = { _, post -> post.id }) { index, post ->
            PostCard(
                post = post,
                onLikeClick = { viewModel.toggleLike(post.id) },
                onCommentClick = { viewModel.openComments(post.id) },
                onShareClick = { viewModel.openShare(post) },
                onSaveClick = { viewModel.toggleSave(post.id) },
                onPollVote = { optionId -> viewModel.votePoll(post.id, optionId) },
                onAuthorClick = { viewModel.navigateTo(ScreenNav.Profile) }
            )

            // Inline Suggested Friends widget after 1st post
            if (index == 0 && state.suggestedFriends.isNotEmpty()) {
                SuggestedFriendsFeedWidget(
                    suggestedFriends = state.suggestedFriends,
                    onAddFriend = { viewModel.addSuggestedFriend(it) },
                    onSeeAll = { viewModel.navigateTo(ScreenNav.Friends) }
                )
            }

            // Inline Trending Groups widget after 2nd post
            if (index == 1 && state.groups.isNotEmpty()) {
                TrendingGroupsFeedWidget(
                    groups = state.groups.take(3),
                    onToggleJoin = { viewModel.toggleGroupMembership(it) },
                    onSeeAll = { viewModel.navigateTo(ScreenNav.Groups) }
                )
            }

            // Inline Upcoming Events widget after 3rd post
            if (index == 2 && state.events.isNotEmpty()) {
                UpcomingEventsFeedWidget(
                    events = state.events.take(2),
                    onToggleRSVP = { viewModel.toggleEventAttendance(it) }
                )
            }
        }
    }
}

@Composable
fun SuggestedFriendsFeedWidget(
    suggestedFriends: List<com.example.data.User>,
    onAddFriend: (com.example.data.User) -> Unit,
    onSeeAll: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 14.dp)) {
            SectionHeader(
                icon = Icons.Default.People,
                title = "People You May Know",
                actionText = "See All",
                onActionClick = onSeeAll
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(suggestedFriends, key = { it.id }) { user ->
                    SuggestedFriendCard(
                        user = user,
                        onAddFriend = { onAddFriend(user) },
                        onClick = onSeeAll
                    )
                }
            }
        }
    }
}

@Composable
fun TrendingGroupsFeedWidget(
    groups: List<com.example.data.Group>,
    onToggleJoin: (String) -> Unit,
    onSeeAll: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            SectionHeader(
                icon = Icons.Default.TrendingUp,
                title = "Trending Communities",
                actionText = "Explore",
                onActionClick = onSeeAll
            )

            Spacer(modifier = Modifier.height(8.dp))

            groups.forEach { group ->
                GroupCard(
                    group = group,
                    onToggleJoin = { onToggleJoin(group.id) },
                    onClick = onSeeAll,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun UpcomingEventsFeedWidget(
    events: List<com.example.data.Event>,
    onToggleRSVP: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            SectionHeader(
                icon = Icons.Default.Event,
                title = "Upcoming Events for You",
                actionText = null,
                onActionClick = {}
            )

            Spacer(modifier = Modifier.height(8.dp))

            events.forEach { event ->
                EventCard(
                    event = event,
                    onToggleRSVP = { onToggleRSVP(event.id) },
                    onClick = {},
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    icon: ImageVector,
    title: String,
    actionText: String?,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = IndigoPrimary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (actionText != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onActionClick)
            ) {
                Text(
                    text = actionText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = IndigoPrimary
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = IndigoPrimary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
