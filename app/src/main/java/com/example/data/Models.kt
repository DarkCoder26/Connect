package com.example.data

enum class RequestStatus {
    PENDING, ACCEPTED, DECLINED
}

enum class NotificationType {
    LIKE, COMMENT, FRIEND_REQUEST, GROUP_INVITE, EVENT, SHARE
}

data class User(
    val id: String,
    val name: String,
    val handle: String,
    val email: String = "",
    val avatarUrl: String? = null,
    val avatarColorIndex: Int = 0,
    val bio: String = "",
    val role: String = "Member",
    val location: String = "San Francisco, CA",
    val website: String = "connectx.app/user",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val friendsCount: Int = 0,
    val postsCount: Int = 0,
    val isOnline: Boolean = false,
    val isVerified: Boolean = false,
    val mutualFriendsCount: Int = 0,
    val isFriend: Boolean = false
)

data class Comment(
    val id: String,
    val author: User,
    val text: String,
    val timestamp: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false
)

data class PollOption(
    val id: String,
    val text: String,
    val votes: Int = 0,
    val isSelected: Boolean = false
)

data class Post(
    val id: String,
    val author: User,
    val text: String,
    val imageUrl: String? = null,
    val imageType: String? = null, // "gradient_tech", "gradient_sunset", "gradient_design", "gradient_nature", "gradient_event"
    val timestamp: String,
    val location: String? = null,
    val feeling: String? = null,
    val groupName: String? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val sharesCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val tags: List<String> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val isPoll: Boolean = false,
    val pollOptions: List<PollOption> = emptyList()
)

data class Story(
    val id: String,
    val user: User,
    val caption: String = "",
    val gradientIndex: Int = 0,
    val timestamp: String = "2h ago",
    val isViewed: Boolean = false,
    val isUserStory: Boolean = false
)

data class DirectMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val isRead: Boolean = true,
    val attachmentEmoji: String? = null
)

data class Conversation(
    val id: String,
    val participant: User,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val messages: List<DirectMessage> = emptyList()
)

data class FriendRequest(
    val id: String,
    val user: User,
    val mutualFriends: Int,
    val timeAgo: String,
    val status: RequestStatus = RequestStatus.PENDING
)

data class Group(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val membersCount: Int,
    val isJoined: Boolean = false,
    val isTrending: Boolean = false,
    val privacy: String = "Public",
    val gradientIndex: Int = 0,
    val recentActivity: String = "Active 10m ago"
)

data class Event(
    val id: String,
    val title: String,
    val groupName: String,
    val dateString: String,
    val timeString: String,
    val location: String,
    val attendeesCount: Int,
    val isAttending: Boolean = false,
    val gradientIndex: Int = 0,
    val category: String = "Tech Meetup"
)

data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val actor: User,
    val text: String,
    val timeAgo: String,
    val isRead: Boolean = false,
    val targetPostId: String? = null,
    val targetGroup: String? = null
)
