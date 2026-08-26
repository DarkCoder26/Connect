package com.example.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ScreenNav {
    object Home : ScreenNav()
    object Friends : ScreenNav()
    object Groups : ScreenNav()
    object Messages : ScreenNav()
    object Notifications : ScreenNav()
    object Profile : ScreenNav()
    object Settings : ScreenNav()
    object Search : ScreenNav()
    data class ChatDetail(val conversationId: String) : ScreenNav()
}

data class AppUiState(
    val currentScreen: ScreenNav = ScreenNav.Home,
    val isDarkMode: Boolean = false,
    val currentUser: User = MockData.currentUser,
    val stories: List<Story> = MockData.initialStories,
    val posts: List<Post> = MockData.initialPosts,
    val conversations: List<Conversation> = MockData.initialConversations,
    val friendRequests: List<FriendRequest> = MockData.initialFriendRequests,
    val suggestedFriends: List<User> = MockData.suggestedFriends,
    val friendsList: List<User> = listOf(
        MockData.userPriyansh,
        MockData.userMuskan,
        MockData.userNaveen,
        MockData.userRaman,
        MockData.userAman
    ),
    val groups: List<Group> = MockData.initialGroups,
    val events: List<Event> = MockData.upcomingEvents,
    val notifications: List<NotificationItem> = MockData.initialNotifications,
    // Active Modals & Dialogs
    val activeStory: Story? = null,
    val isCreatePostOpen: Boolean = false,
    val activeCommentsPostId: String? = null,
    val activeSharePost: Post? = null,
    val isEditProfileOpen: Boolean = false,
    val searchQuery: String = "",
    val activeChatId: String? = null
) {
    val unreadNotificationsCount: Int
        get() = notifications.count { !it.isRead }

    val unreadMessagesCount: Int
        get() = conversations.sumOf { it.unreadCount }

    val pendingRequestsCount: Int
        get() = friendRequests.count { it.status == RequestStatus.PENDING }
}

class ConnectXViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: ScreenNav) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    // Story Actions
    fun openStory(story: Story) {
        // mark story as viewed
        _uiState.update { state ->
            val updated = state.stories.map {
                if (it.id == story.id) it.copy(isViewed = true) else it
            }
            state.copy(activeStory = story.copy(isViewed = true), stories = updated)
        }
    }

    fun closeStory() {
        _uiState.update { it.copy(activeStory = null) }
    }

    // Post Actions
    fun toggleLike(postId: String) {
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->
                if (post.id == postId) {
                    val newIsLiked = !post.isLiked
                    val newLikesCount = if (newIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
                    post.copy(isLiked = newIsLiked, likesCount = newLikesCount)
                } else post
            }
            state.copy(posts = updatedPosts)
        }
    }

    fun toggleSave(postId: String) {
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->
                if (post.id == postId) post.copy(isSaved = !post.isSaved) else post
            }
            state.copy(posts = updatedPosts)
        }
    }

    fun votePoll(postId: String, optionId: String) {
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->
                if (post.id == postId && post.isPoll) {
                    val updatedOptions = post.pollOptions.map { opt ->
                        if (opt.id == optionId) {
                            opt.copy(votes = opt.votes + (if (opt.isSelected) 0 else 1), isSelected = true)
                        } else {
                            opt.copy(votes = if (opt.isSelected) (opt.votes - 1).coerceAtLeast(0) else opt.votes, isSelected = false)
                        }
                    }
                    post.copy(pollOptions = updatedOptions)
                } else post
            }
            state.copy(posts = updatedPosts)
        }
    }

    fun addComment(postId: String, commentText: String) {
        if (commentText.isBlank()) return
        val newComment = Comment(
            id = "c_${System.currentTimeMillis()}",
            author = _uiState.value.currentUser,
            text = commentText.trim(),
            timestamp = "Just now",
            likesCount = 0,
            isLiked = false
        )
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->
                if (post.id == postId) {
                    post.copy(
                        comments = listOf(newComment) + post.comments,
                        commentsCount = post.commentsCount + 1
                    )
                } else post
            }
            state.copy(posts = updatedPosts)
        }
    }

    fun openComments(postId: String) {
        _uiState.update { it.copy(activeCommentsPostId = postId) }
    }

    fun closeComments() {
        _uiState.update { it.copy(activeCommentsPostId = null) }
    }

    fun openShare(post: Post) {
        _uiState.update { it.copy(activeSharePost = post) }
    }

    fun closeShare() {
        _uiState.update { it.copy(activeSharePost = null) }
    }

    fun sharePostDirectly(postId: String) {
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->
                if (post.id == postId) post.copy(sharesCount = post.sharesCount + 1) else post
            }
            state.copy(posts = updatedPosts, activeSharePost = null)
        }
    }

    // Create Post
    fun setCreatePostOpen(open: Boolean) {
        _uiState.update { it.copy(isCreatePostOpen = open) }
    }

    fun createPost(
        text: String,
        location: String?,
        feeling: String?,
        imageType: String?,
        tags: List<String>
    ) {
        if (text.isBlank()) return
        val newPost = Post(
            id = "post_${System.currentTimeMillis()}",
            author = _uiState.value.currentUser,
            text = text.trim(),
            imageType = imageType,
            timestamp = "Just now",
            location = location,
            feeling = feeling,
            likesCount = 0,
            commentsCount = 0,
            sharesCount = 0,
            isLiked = false,
            isSaved = false,
            tags = tags,
            comments = emptyList()
        )
        _uiState.update { state ->
            val updatedCurrentUser = state.currentUser.copy(postsCount = state.currentUser.postsCount + 1)
            state.copy(
                posts = listOf(newPost) + state.posts,
                currentUser = updatedCurrentUser,
                isCreatePostOpen = false
            )
        }
    }

    // Friend Requests
    fun acceptFriendRequest(requestId: String) {
        _uiState.update { state ->
            val req = state.friendRequests.find { it.id == requestId }
            val updatedRequests = state.friendRequests.map {
                if (it.id == requestId) it.copy(status = RequestStatus.ACCEPTED) else it
            }
            val newFriends = if (req != null) {
                state.friendsList + req.user.copy(isFriend = true)
            } else state.friendsList

            state.copy(
                friendRequests = updatedRequests,
                friendsList = newFriends,
                currentUser = state.currentUser.copy(friendsCount = state.currentUser.friendsCount + 1)
            )
        }
    }

    fun declineFriendRequest(requestId: String) {
        _uiState.update { state ->
            val updatedRequests = state.friendRequests.map {
                if (it.id == requestId) it.copy(status = RequestStatus.DECLINED) else it
            }
            state.copy(friendRequests = updatedRequests)
        }
    }

    fun addSuggestedFriend(user: User) {
        _uiState.update { state ->
            val updatedSuggestions = state.suggestedFriends.filter { it.id != user.id }
            val updatedFriends = state.friendsList + user.copy(isFriend = true)
            state.copy(
                suggestedFriends = updatedSuggestions,
                friendsList = updatedFriends,
                currentUser = state.currentUser.copy(friendsCount = state.currentUser.friendsCount + 1)
            )
        }
    }

    fun removeFriend(userId: String) {
        _uiState.update { state ->
            state.copy(
                friendsList = state.friendsList.filter { it.id != userId },
                currentUser = state.currentUser.copy(friendsCount = (state.currentUser.friendsCount - 1).coerceAtLeast(0))
            )
        }
    }

    // Groups
    fun toggleGroupMembership(groupId: String) {
        _uiState.update { state ->
            val updatedGroups = state.groups.map { group ->
                if (group.id == groupId) {
                    val newJoined = !group.isJoined
                    val newCount = if (newJoined) group.membersCount + 1 else (group.membersCount - 1).coerceAtLeast(0)
                    group.copy(isJoined = newJoined, membersCount = newCount)
                } else group
            }
            state.copy(groups = updatedGroups)
        }
    }

    // Events
    fun toggleEventAttendance(eventId: String) {
        _uiState.update { state ->
            val updatedEvents = state.events.map { event ->
                if (event.id == eventId) {
                    val newAttending = !event.isAttending
                    val newCount = if (newAttending) event.attendeesCount + 1 else (event.attendeesCount - 1).coerceAtLeast(0)
                    event.copy(isAttending = newAttending, attendeesCount = newCount)
                } else event
            }
            state.copy(events = updatedEvents)
        }
    }

    // Messages & Chat
    fun openChat(conversationId: String) {
        _uiState.update { state ->
            // Mark unread as 0
            val updatedConvs = state.conversations.map {
                if (it.id == conversationId) it.copy(unreadCount = 0) else it
            }
            state.copy(
                conversations = updatedConvs,
                activeChatId = conversationId,
                currentScreen = ScreenNav.ChatDetail(conversationId)
            )
        }
    }

    fun sendMessage(conversationId: String, text: String, attachmentEmoji: String? = null) {
        if (text.isBlank() && attachmentEmoji == null) return
        val newMsg = DirectMessage(
            id = "m_${System.currentTimeMillis()}",
            senderId = _uiState.value.currentUser.id,
            text = text.trim(),
            timestamp = "Just now",
            isFromMe = true,
            isRead = true,
            attachmentEmoji = attachmentEmoji
        )
        _uiState.update { state ->
            val updatedConvs = state.conversations.map { conv ->
                if (conv.id == conversationId) {
                    conv.copy(
                        lastMessage = if (text.isNotBlank()) text.trim() else "Sent an attachment $attachmentEmoji",
                        lastMessageTime = "Just now",
                        messages = conv.messages + newMsg
                    )
                } else conv
            }
            state.copy(conversations = updatedConvs)
        }
    }

    // Notifications
    fun markAllNotificationsAsRead() {
        _uiState.update { state ->
            val updated = state.notifications.map { it.copy(isRead = true) }
            state.copy(notifications = updated)
        }
    }

    fun markNotificationRead(notifId: String) {
        _uiState.update { state ->
            val updated = state.notifications.map {
                if (it.id == notifId) it.copy(isRead = true) else it
            }
            state.copy(notifications = updated)
        }
    }

    // Profile
    fun setEditProfileOpen(open: Boolean) {
        _uiState.update { it.copy(isEditProfileOpen = open) }
    }

    fun updateProfile(name: String, bio: String, role: String, location: String, website: String) {
        _uiState.update { state ->
            val updatedUser = state.currentUser.copy(
                name = name.ifBlank { state.currentUser.name },
                bio = bio,
                role = role.ifBlank { state.currentUser.role },
                location = location.ifBlank { state.currentUser.location },
                website = website.ifBlank { state.currentUser.website }
            )
            state.copy(currentUser = updatedUser, isEditProfileOpen = false)
        }
    }

    // Search
    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
