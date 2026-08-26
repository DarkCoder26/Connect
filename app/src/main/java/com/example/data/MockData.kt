package com.example.data

object MockData {
    val currentUser = User(
        id = "user_me",
        name = "Satish Chaudhary",
        handle = "@satishchaudhary6336",
        email = "satishchaudhary6336@gmail.com",
        avatarColorIndex = 0,
        bio = "Crafting high-impact mobile experiences & design systems 🚀 | Mobile Architect | Open Source Contributor",
        role = "Lead Mobile Architect",
        location = "San Francisco, CA",
        website = "connectx.app/satishchaudhary6336",
        followersCount = 3420,
        followingCount = 528,
        friendsCount = 892,
        postsCount = 48,
        isOnline = true,
        isVerified = true,
        mutualFriendsCount = 0,
        isFriend = false
    )

    val userPriyansh = User(
        id = "user_priyansh",
        name = "Priyansh Sharma",
        handle = "@priyansh_ui",
        avatarColorIndex = 1,
        bio = "Staff Product Designer @ Figma. Building next-gen design tokens ✨",
        role = "Product Designer",
        location = "Bengaluru, India",
        followersCount = 8940,
        followingCount = 412,
        friendsCount = 640,
        postsCount = 112,
        isOnline = true,
        isVerified = true,
        mutualFriendsCount = 18,
        isFriend = true
    )

    val userMuskan = User(
        id = "user_muskan",
        name = "Muskan Patel",
        handle = "@muskan_dev",
        avatarColorIndex = 2,
        bio = "Mobile Engineering Lead 📱 Kotlin & React Native aficionado | Tech Speaker",
        role = "Lead Engineer",
        location = "Seattle, WA",
        followersCount = 5120,
        followingCount = 310,
        friendsCount = 480,
        postsCount = 84,
        isOnline = true,
        isVerified = true,
        mutualFriendsCount = 24,
        isFriend = true
    )

    val userNaveen = User(
        id = "user_naveen",
        name = "Naveen Kumar",
        handle = "@naveen_k",
        avatarColorIndex = 3,
        bio = "Full-stack cloud architect & AI researcher. Coffee + Clean code ☕️",
        role = "Cloud Architect",
        location = "Austin, TX",
        followersCount = 3100,
        followingCount = 290,
        friendsCount = 312,
        postsCount = 62,
        isOnline = false,
        isVerified = false,
        mutualFriendsCount = 14,
        isFriend = true
    )

    val userRaman = User(
        id = "user_raman",
        name = "Raman Verma",
        handle = "@raman_v",
        avatarColorIndex = 4,
        bio = "Founder @ DevStudio. Building tools for 10M+ developers worldwide 🌐",
        role = "Tech Founder",
        location = "London, UK",
        followersCount = 14200,
        followingCount = 620,
        friendsCount = 950,
        postsCount = 230,
        isOnline = true,
        isVerified = true,
        mutualFriendsCount = 32,
        isFriend = true
    )

    val userAman = User(
        id = "user_aman",
        name = "Aman Gupta",
        handle = "@aman_builds",
        avatarColorIndex = 5,
        bio = "UI/UX & Motion Designer. Crafting micro-interactions that spark joy ⚡️",
        role = "Motion Designer",
        location = "Toronto, Canada",
        followersCount = 6700,
        followingCount = 440,
        friendsCount = 510,
        postsCount = 95,
        isOnline = true,
        isVerified = false,
        mutualFriendsCount = 19,
        isFriend = true
    )

    val userSofia = User(
        id = "user_sofia",
        name = "Sofia Chen",
        handle = "@sofia_design",
        avatarColorIndex = 6,
        bio = "Design Director & Creative Technologist. Exploring spatial interfaces 🕶️",
        role = "Design Director",
        location = "New York, NY",
        followersCount = 9400,
        followingCount = 510,
        friendsCount = 780,
        postsCount = 140,
        isOnline = false,
        isVerified = true,
        mutualFriendsCount = 27,
        isFriend = false
    )

    val userLiam = User(
        id = "user_liam",
        name = "Liam Miller",
        handle = "@liam_tech",
        avatarColorIndex = 7,
        bio = "Systems Engineer & Open Source Advocate. Rust + Kotlin lover 🦀",
        role = "Systems Engineer",
        location = "Berlin, Germany",
        followersCount = 2800,
        followingCount = 180,
        friendsCount = 230,
        postsCount = 41,
        isOnline = true,
        isVerified = false,
        mutualFriendsCount = 8,
        isFriend = false
    )

    val initialStories = listOf(
        Story(
            id = "story_0",
            user = currentUser,
            caption = "Share an update or photo with friends!",
            gradientIndex = 0,
            timestamp = "Just now",
            isViewed = false,
            isUserStory = true
        ),
        Story(
            id = "story_1",
            user = userPriyansh,
            caption = "Just dropped the new design system tokens update for ConnectX! Clean typography and 8px grid system in full glory 🔥",
            gradientIndex = 1,
            timestamp = "45m ago",
            isViewed = false
        ),
        Story(
            id = "story_2",
            user = userMuskan,
            caption = "Morning keynote at Mobile World Summit! Discussing declarative UI architecture and instant state management 📱",
            gradientIndex = 2,
            timestamp = "1h ago",
            isViewed = false
        ),
        Story(
            id = "story_3",
            user = userNaveen,
            caption = "Weekend hike with the engineering team. Disconnecting to recharge 🌲🌄",
            gradientIndex = 3,
            timestamp = "3h ago",
            isViewed = false
        ),
        Story(
            id = "story_4",
            user = userRaman,
            caption = "Super excited to announce we crossed 500,000 active users on our new mobile client! Massive milestone 🚀",
            gradientIndex = 4,
            timestamp = "5h ago",
            isViewed = true
        ),
        Story(
            id = "story_5",
            user = userAman,
            caption = "Prototyping fluid micro-animations for swipe gestures in ConnectX. Thoughts on this spring damping curve? 💡",
            gradientIndex = 5,
            timestamp = "6h ago",
            isViewed = true
        ),
        Story(
            id = "story_6",
            user = userSofia,
            caption = "Color palettes inspired by brutalist architecture and neon lighting. Exploring dark mode aesthetics 🎨",
            gradientIndex = 6,
            timestamp = "8h ago",
            isViewed = true
        )
    )

    val initialPosts = listOf(
        Post(
            id = "post_1",
            author = userPriyansh,
            text = "Excited to unveil the new design system for our social platform! 🎨 We focused on micro-interactions, responsive 8-point spatial rhythm, and accessible contrast ratios. What do you think of this clean card hierarchy?",
            imageType = "gradient_design",
            timestamp = "20m ago",
            location = "San Francisco Design Lab",
            feeling = "🎨 feeling creative",
            groupName = "UI/UX Designers Guild",
            likesCount = 342,
            commentsCount = 28,
            sharesCount = 14,
            isLiked = false,
            isSaved = false,
            tags = listOf("#DesignSystems", "#UIUX", "#MobileDesign", "#ConnectX"),
            comments = listOf(
                Comment(
                    id = "c_1_1",
                    author = userMuskan,
                    text = "The contrast on the card headers and spacing is immaculate! Kudos Priyansh 👏",
                    timestamp = "15m ago",
                    likesCount = 12,
                    isLiked = true
                ),
                Comment(
                    id = "c_1_2",
                    author = currentUser,
                    text = "The layout looks super crisp on mobile screens. Love the subtle elevation and pill tags!",
                    timestamp = "10m ago",
                    likesCount = 5,
                    isLiked = false
                ),
                Comment(
                    id = "c_1_3",
                    author = userAman,
                    text = "Can't wait to add the fluid gesture transitions onto these cards! ⚡️",
                    timestamp = "5m ago",
                    likesCount = 3,
                    isLiked = false
                )
            )
        ),
        Post(
            id = "post_2",
            author = userMuskan,
            text = "Quick poll for the mobile community! 🚀 When building modern apps, which state management paradigm gives your team the best developer velocity and reliability?",
            imageType = null,
            timestamp = "1h ago",
            location = "Seattle Tech Center",
            feeling = "🤔 curious",
            likesCount = 512,
            commentsCount = 64,
            sharesCount = 31,
            isLiked = true,
            isSaved = true,
            isPoll = true,
            pollOptions = listOf(
                PollOption("po_1", "Reactive StateFlow / MVVM (Kotlin)", 642, true),
                PollOption("po_2", "Redux Toolkit / Zustand", 380, false),
                PollOption("po_3", "Signals & Fine-grained Reactivity", 215, false),
                PollOption("po_4", "MVI & Unidirectional Data Flow", 410, false)
            ),
            tags = listOf("#StateManagement", "#MobileDev", "#Architecture", "#Poll"),
            comments = listOf(
                Comment(
                    id = "c_2_1",
                    author = userNaveen,
                    text = "Unidirectional Data Flow paired with StateFlow is unmatched for testability and predictability!",
                    timestamp = "40m ago",
                    likesCount = 18,
                    isLiked = false
                )
            )
        ),
        Post(
            id = "post_3",
            author = userRaman,
            text = "Proud to share our team's performance benchmark: We reduced cold app launch time by 48% and cut frame drop rates to under 0.2%! ⚡️ Optimizing memory allocations and deferring heavy UI rendering made a night-and-day difference.",
            imageType = "gradient_tech",
            timestamp = "3h ago",
            location = "London Innovation Hub",
            feeling = "🚀 feeling proud",
            groupName = "Mobile Performance & Engineering",
            likesCount = 890,
            commentsCount = 52,
            sharesCount = 68,
            isLiked = false,
            isSaved = false,
            tags = listOf("#AppPerformance", "#Optimization", "#TechLead", "#Engineering"),
            comments = listOf(
                Comment(
                    id = "c_3_1",
                    author = userLiam,
                    text = "Massive achievement Raman! Did you profile with Android Studio Trace or custom perfetto scripts?",
                    timestamp = "2h ago",
                    likesCount = 8,
                    isLiked = false
                )
            )
        ),
        Post(
            id = "post_4",
            author = userAman,
            text = "Golden hour vibes after an intense hackathon sprint! 🌅 Here's a glimpse of the mobile prototype we built in 36 hours. Always remember to take breaks, hydrate, and celebrate team wins.",
            imageType = "gradient_sunset",
            timestamp = "5h ago",
            location = "Vancouver Waterfront",
            feeling = "✨ feeling inspired",
            likesCount = 425,
            commentsCount = 19,
            sharesCount = 9,
            isLiked = true,
            isSaved = false,
            tags = listOf("#Hackathon", "#WorkLifeBalance", "#TechLife"),
            comments = listOf()
        )
    )

    val initialConversations = listOf(
        Conversation(
            id = "conv_1",
            participant = userPriyansh,
            lastMessage = "I just pushed the revised UI icons to the repository. Take a look!",
            lastMessageTime = "12:45 PM",
            unreadCount = 2,
            isOnline = true,
            messages = listOf(
                DirectMessage("m_1_1", "user_priyansh", "Hey Satish! How's the new navigation setup coming along?", "12:30 PM", false),
                DirectMessage("m_1_2", "user_me", "Hey Priyansh! Just finished the bottom navigation bar and active tab indicators. Looks super sleek!", "12:35 PM", true),
                DirectMessage("m_1_3", "user_priyansh", "Awesome! Did you get a chance to check the color tokens?", "12:40 PM", false),
                DirectMessage("m_1_4", "user_priyansh", "I just pushed the revised UI icons to the repository. Take a look!", "12:45 PM", false)
            )
        ),
        Conversation(
            id = "conv_2",
            participant = userMuskan,
            lastMessage = "Let's sync up for the mobile design review tomorrow at 10 AM.",
            lastMessageTime = "11:20 AM",
            unreadCount = 0,
            isOnline = true,
            messages = listOf(
                DirectMessage("m_2_1", "user_me", "Hey Muskan, loved your talk at the mobile summit!", "10:15 AM", true),
                DirectMessage("m_2_2", "user_muskan", "Thanks Satish! Appreciate the support 🙌", "10:45 AM", false),
                DirectMessage("m_2_3", "user_muskan", "Let's sync up for the mobile design review tomorrow at 10 AM.", "11:20 AM", false)
            )
        ),
        Conversation(
            id = "conv_3",
            participant = userRaman,
            lastMessage = "Those benchmark numbers are incredible. Thanks for sharing!",
            lastMessageTime = "Yesterday",
            unreadCount = 0,
            isOnline = true,
            messages = listOf(
                DirectMessage("m_3_1", "user_raman", "Hey Satish, saw your comment on the performance thread.", "Yesterday", false),
                DirectMessage("m_3_2", "user_me", "Those benchmark numbers are incredible. Thanks for sharing!", "Yesterday", true)
            )
        ),
        Conversation(
            id = "conv_4",
            participant = userAman,
            lastMessage = "Sent you the Figma prototype link for the story viewer transitions.",
            lastMessageTime = "Yesterday",
            unreadCount = 1,
            isOnline = true,
            messages = listOf(
                DirectMessage("m_4_1", "user_aman", "Sent you the Figma prototype link for the story viewer transitions.", "Yesterday", false)
            )
        ),
        Conversation(
            id = "conv_5",
            participant = userNaveen,
            lastMessage = "Are you joining the AI Developers Meetup this Friday?",
            lastMessageTime = "2 days ago",
            unreadCount = 0,
            isOnline = false,
            messages = listOf(
                DirectMessage("m_5_1", "user_naveen", "Are you joining the AI Developers Meetup this Friday?", "2 days ago", false)
            )
        ),
        Conversation(
            id = "conv_6",
            participant = userSofia,
            lastMessage = "Great presentation on Jetpack Compose design architecture!",
            lastMessageTime = "3 days ago",
            unreadCount = 0,
            isOnline = false,
            messages = listOf(
                DirectMessage("m_6_1", "user_sofia", "Great presentation on Jetpack Compose design architecture!", "3 days ago", false)
            )
        )
    )

    val initialFriendRequests = listOf(
        FriendRequest(
            id = "fr_1",
            user = userSofia,
            mutualFriends = 27,
            timeAgo = "10m ago",
            status = RequestStatus.PENDING
        ),
        FriendRequest(
            id = "fr_2",
            user = userLiam,
            mutualFriends = 8,
            timeAgo = "1h ago",
            status = RequestStatus.PENDING
        ),
        FriendRequest(
            id = "fr_3",
            user = User(
                id = "user_kavya",
                name = "Kavya Reddy",
                handle = "@kavya_codes",
                avatarColorIndex = 8,
                bio = "AI/ML Engineer & Open Source Contributor. PyTorch & Kotlin enthusiast.",
                role = "ML Engineer",
                location = "Hyderabad, India",
                followersCount = 4200,
                followingCount = 310,
                friendsCount = 450,
                postsCount = 38,
                isOnline = true,
                isVerified = false,
                mutualFriendsCount = 14,
                isFriend = false
            ),
            mutualFriends = 14,
            timeAgo = "3h ago",
            status = RequestStatus.PENDING
        )
    )

    val suggestedFriends = listOf(
        User(
            id = "sug_1",
            name = "David Miller",
            handle = "@david_ux",
            avatarColorIndex = 9,
            bio = "Product Designer @ Stripe. Obsessed with microcopy and accessible components.",
            role = "Lead Product Designer",
            location = "San Francisco, CA",
            followersCount = 7800,
            followingCount = 410,
            friendsCount = 590,
            postsCount = 88,
            isOnline = true,
            isVerified = true,
            mutualFriendsCount = 34,
            isFriend = false
        ),
        User(
            id = "sug_2",
            name = "Ananya Sen",
            handle = "@ananya_tech",
            avatarColorIndex = 10,
            bio = "Android Google Developer Expert (GDE) 🌟 Speaker & Tech Blogger",
            role = "Senior Android Dev",
            location = "Bengaluru, India",
            followersCount = 12500,
            followingCount = 320,
            friendsCount = 810,
            postsCount = 175,
            isOnline = false,
            isVerified = true,
            mutualFriendsCount = 42,
            isFriend = false
        ),
        User(
            id = "sug_3",
            name = "Marcus Vance",
            handle = "@marcus_v",
            avatarColorIndex = 11,
            bio = "Co-founder @ NextWave. Angel Investor & Tech Enthusiast 🚀",
            role = "Founder & Investor",
            location = "New York, NY",
            followersCount = 18900,
            followingCount = 800,
            friendsCount = 1200,
            postsCount = 320,
            isOnline = true,
            isVerified = true,
            mutualFriendsCount = 16,
            isFriend = false
        ),
        User(
            id = "sug_4",
            name = "Elena Rostova",
            handle = "@elena_motion",
            avatarColorIndex = 12,
            bio = "3D & Motion Specialist. Bringing UI elements to life with fluid shaders ✨",
            role = "3D Motion Artist",
            location = "Prague, Czechia",
            followersCount = 6100,
            followingCount = 290,
            friendsCount = 340,
            postsCount = 54,
            isOnline = true,
            isVerified = false,
            mutualFriendsCount = 11,
            isFriend = false
        )
    )

    val initialGroups = listOf(
        Group(
            id = "grp_1",
            name = "UI/UX Designers Guild",
            description = "A global community of 45k+ designers sharing design systems, UI breakdowns, and critique.",
            category = "Design",
            membersCount = 45200,
            isJoined = true,
            isTrending = true,
            privacy = "Public",
            gradientIndex = 0,
            recentActivity = "18 new posts today"
        ),
        Group(
            id = "grp_2",
            name = "Mobile Architects & Devs",
            description = "Kotlin, Jetpack Compose, React Native, Flutter, Swift, and modern mobile app architecture discussions.",
            category = "Engineering",
            membersCount = 62100,
            isJoined = true,
            isTrending = true,
            privacy = "Public",
            gradientIndex = 1,
            recentActivity = "34 new posts today"
        ),
        Group(
            id = "grp_3",
            name = "AI Pioneers & LLM Builders",
            description = "Exploring generative AI, prompt engineering, agentic workflows, and edge ML models.",
            category = "Artificial Intelligence",
            membersCount = 89400,
            isJoined = false,
            isTrending = true,
            privacy = "Public",
            gradientIndex = 2,
            recentActivity = "56 new posts today"
        ),
        Group(
            id = "grp_4",
            name = "Tech Founders & Startup Hub",
            description = "Zero-to-one startup founders discussing fundraising, product-market fit, and scaling.",
            category = "Entrepreneurship",
            membersCount = 28900,
            isJoined = false,
            isTrending = false,
            privacy = "Private",
            gradientIndex = 3,
            recentActivity = "12 new posts today"
        ),
        Group(
            id = "grp_5",
            name = "Photography & Visual Arts",
            description = "Street, landscape, portrait, and architectural photography with gear reviews.",
            category = "Creativity",
            membersCount = 31700,
            isJoined = false,
            isTrending = false,
            privacy = "Public",
            gradientIndex = 4,
            recentActivity = "9 new posts today"
        )
    )

    val upcomingEvents = listOf(
        Event(
            id = "ev_1",
            title = "Global Mobile Architecture Summit 2026",
            groupName = "Mobile Architects & Devs",
            dateString = "Tomorrow, Aug 23",
            timeString = "10:00 AM - 4:00 PM PST",
            location = "Online Virtual Stage",
            attendeesCount = 3420,
            isAttending = true,
            gradientIndex = 0,
            category = "Tech Summit"
        ),
        Event(
            id = "ev_2",
            title = "Design Tokens & System Workshop",
            groupName = "UI/UX Designers Guild",
            dateString = "Friday, Aug 28",
            timeString = "2:00 PM - 5:30 PM PST",
            location = "San Francisco Civic Center",
            attendeesCount = 850,
            isAttending = false,
            gradientIndex = 1,
            category = "Workshop"
        ),
        Event(
            id = "ev_3",
            title = "AI in Production: Live Demos & QA",
            groupName = "AI Pioneers & LLM Builders",
            dateString = "Sunday, Aug 30",
            timeString = "11:00 AM - 1:00 PM PST",
            location = "Online Stream",
            attendeesCount = 1920,
            isAttending = true,
            gradientIndex = 2,
            category = "Webinar"
        )
    )

    val initialNotifications = listOf(
        NotificationItem(
            id = "notif_1",
            type = NotificationType.FRIEND_REQUEST,
            actor = userSofia,
            text = "sent you a friend request.",
            timeAgo = "10m ago",
            isRead = false
        ),
        NotificationItem(
            id = "notif_2",
            type = NotificationType.LIKE,
            actor = userMuskan,
            text = "liked your post about 'Declarative UI architecture'.",
            timeAgo = "25m ago",
            isRead = false,
            targetPostId = "post_1"
        ),
        NotificationItem(
            id = "notif_3",
            type = NotificationType.COMMENT,
            actor = userPriyansh,
            text = "commented: 'The contrast on the card headers is immaculate!'",
            timeAgo = "1h ago",
            isRead = false,
            targetPostId = "post_1"
        ),
        NotificationItem(
            id = "notif_4",
            type = NotificationType.GROUP_INVITE,
            actor = userRaman,
            text = "invited you to join 'Tech Founders & Startup Hub'.",
            timeAgo = "3h ago",
            isRead = true,
            targetGroup = "grp_4"
        ),
        NotificationItem(
            id = "notif_5",
            type = NotificationType.EVENT,
            actor = userNaveen,
            text = "is attending 'Global Mobile Architecture Summit 2026'.",
            timeAgo = "5h ago",
            isRead = true
        ),
        NotificationItem(
            id = "notif_6",
            type = NotificationType.SHARE,
            actor = userAman,
            text = "shared your latest design tokens guide with 510 followers.",
            timeAgo = "1d ago",
            isRead = true,
            targetPostId = "post_1"
        )
    )
}
