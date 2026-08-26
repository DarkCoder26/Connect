package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.ConnectXViewModel
import com.example.data.ScreenNav
import com.example.ui.components.AppHeader
import com.example.ui.components.BottomNavBar
import com.example.ui.components.CommentsBottomSheet
import com.example.ui.components.CreatePostDialog
import com.example.ui.components.ShareBottomSheet
import com.example.ui.components.StoryViewerDialog
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.FriendsScreen
import com.example.ui.screens.GroupsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MessagesScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.ConnectXTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appViewModel: ConnectXViewModel = viewModel()
            ConnectXApp(viewModel = appViewModel)
        }
    }
}

@Composable
fun ConnectXApp(viewModel: ConnectXViewModel) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Handle system back navigation
    BackHandler(enabled = state.currentScreen != ScreenNav.Home) {
        when (state.currentScreen) {
            is ScreenNav.ChatDetail -> viewModel.navigateTo(ScreenNav.Messages)
            is ScreenNav.Search -> viewModel.navigateTo(ScreenNav.Home)
            is ScreenNav.Settings -> viewModel.navigateTo(ScreenNav.Profile)
            else -> viewModel.navigateTo(ScreenNav.Home)
        }
    }

    ConnectXTheme(darkTheme = state.isDarkMode) {
        val showMainChrome = state.currentScreen !is ScreenNav.ChatDetail &&
                state.currentScreen !is ScreenNav.Search &&
                state.currentScreen !is ScreenNav.Settings

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                if (showMainChrome) {
                    AppHeader(
                        currentUser = state.currentUser,
                        unreadNotifications = state.unreadNotificationsCount,
                        unreadMessages = state.unreadMessagesCount,
                        isDarkMode = state.isDarkMode,
                        onLogoClick = { viewModel.navigateTo(ScreenNav.Home) },
                        onSearchClick = { viewModel.navigateTo(ScreenNav.Search) },
                        onNotificationsClick = { viewModel.navigateTo(ScreenNav.Notifications) },
                        onMessagesClick = { viewModel.navigateTo(ScreenNav.Messages) },
                        onThemeToggle = { viewModel.toggleDarkMode() },
                        onProfileClick = { viewModel.navigateTo(ScreenNav.Profile) }
                    )
                }
            },
            bottomBar = {
                if (showMainChrome) {
                    BottomNavBar(
                        currentScreen = state.currentScreen,
                        pendingRequestsCount = state.pendingRequestsCount,
                        unreadMessagesCount = state.unreadMessagesCount,
                        onNavigate = { viewModel.navigateTo(it) },
                        onCreatePostClick = { viewModel.setCreatePostOpen(true) }
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = state.currentScreen,
                    transitionSpec = {
                        fadeIn() + slideInHorizontally { width -> width / 4 } togetherWith
                                fadeOut() + slideOutHorizontally { width -> -width / 4 }
                    },
                    label = "ScreenTransition"
                ) { targetScreen ->
                    when (targetScreen) {
                        is ScreenNav.Home -> HomeScreen(state = state, viewModel = viewModel)
                        is ScreenNav.Friends -> FriendsScreen(state = state, viewModel = viewModel)
                        is ScreenNav.Groups -> GroupsScreen(state = state, viewModel = viewModel)
                        is ScreenNav.Messages -> MessagesScreen(state = state, viewModel = viewModel)
                        is ScreenNav.Notifications -> NotificationsScreen(state = state, viewModel = viewModel)
                        is ScreenNav.Profile -> ProfileScreen(state = state, viewModel = viewModel)
                        is ScreenNav.Search -> SearchScreen(
                            state = state,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateTo(ScreenNav.Home) }
                        )
                        is ScreenNav.Settings -> SettingsScreen(
                            state = state,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateTo(ScreenNav.Profile) }
                        )
                        is ScreenNav.ChatDetail -> ChatScreen(
                            conversationId = targetScreen.conversationId,
                            state = state,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateTo(ScreenNav.Messages) }
                        )
                    }
                }
            }
        }

        // Create Post Full Modal
        if (state.isCreatePostOpen) {
            CreatePostDialog(
                currentUser = state.currentUser,
                onDismiss = { viewModel.setCreatePostOpen(false) },
                onPostCreated = { text, location, feeling, imageType, tags ->
                    viewModel.createPost(text, location, feeling, imageType, tags)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Post published to ConnectX! 🚀",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }

        // Fullscreen Story Viewer
        if (state.activeStory != null) {
            StoryViewerDialog(
                story = state.activeStory!!,
                onDismiss = { viewModel.closeStory() },
                onReplySent = { reply ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Reply sent: \"$reply\"",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }

        // Post Comments Bottom Sheet
        if (state.activeCommentsPostId != null) {
            val post = state.posts.find { it.id == state.activeCommentsPostId }
            if (post != null) {
                CommentsBottomSheet(
                    post = post,
                    currentUser = state.currentUser,
                    onDismiss = { viewModel.closeComments() },
                    onAddComment = { commentText ->
                        viewModel.addComment(post.id, commentText)
                    }
                )
            }
        }

        // Share Bottom Sheet
        if (state.activeSharePost != null) {
            ShareBottomSheet(
                post = state.activeSharePost!!,
                onDismiss = { viewModel.closeShare() },
                onDirectShare = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Shared successfully!",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    }
}
