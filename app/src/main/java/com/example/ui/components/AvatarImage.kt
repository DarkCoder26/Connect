package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.User
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.IndigoLight
import com.example.ui.theme.IndigoPrimary

// Distinct beautiful gradient palettes for avatar placeholders
val AvatarGradients = listOf(
    listOf(Color(0xFF4F46E5), Color(0xFF7C3AED)), // 0: Indigo - Purple (Alex)
    listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)), // 1: Cyan - Blue (Priyansh)
    listOf(Color(0xFFEC4899), Color(0xFFF43F5E)), // 2: Pink - Rose (Muskan)
    listOf(Color(0xFF10B981), Color(0xFF059669)), // 3: Emerald - Teal (Naveen)
    listOf(Color(0xFFF59E0B), Color(0xFFD97706)), // 4: Amber - Orange (Raman)
    listOf(Color(0xFF8B5CF6), Color(0xFF6366F1)), // 5: Purple - Indigo (Aman)
    listOf(Color(0xFFFF007A), Color(0xFF7928CA)), // 6: Magenta - Violet (Sofia)
    listOf(Color(0xFF14B8A6), Color(0xFF0D9488)), // 7: Teal - DarkTeal (Liam)
    listOf(Color(0xFFF97316), Color(0xFFEA580C)), // 8: Orange - Red (Kavya)
    listOf(Color(0xFF6366F1), Color(0xFF4338CA)), // 9: Indigo (David)
    listOf(Color(0xFFD946EF), Color(0xFFC026D3)), // 10: Fuchsia (Ananya)
    listOf(Color(0xFF0EA5E9), Color(0xFF0284C7)), // 11: Sky (Marcus)
    listOf(Color(0xFF84CC16), Color(0xFF65A30D))  // 12: Lime (Elena)
)

@Composable
fun AvatarImage(
    user: User,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    showOnlineBadge: Boolean = false,
    showVerifiedBadge: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val gradientColors = AvatarGradients.getOrElse(user.avatarColorIndex % AvatarGradients.size) {
        AvatarGradients[0]
    }

    val initials = user.name
        .split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .ifEmpty { "U" }

    Box(
        modifier = modifier
            .size(size)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        // Main Avatar Circle with Gradient & Initials
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Brush.linearGradient(gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = Color.White,
                fontSize = (size.value * 0.38f).sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
        }

        // Online Status Dot
        if (showOnlineBadge && user.isOnline) {
            val badgeSize = (size * 0.28f).coerceAtLeast(10.dp)
            Box(
                modifier = Modifier
                    .size(badgeSize)
                    .align(Alignment.BottomEnd)
                    .offset(x = 1.dp, y = 1.dp)
                    .clip(CircleShape)
                    .background(EmeraldSuccess)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
            )
        }

        // Verified Badge
        if (showVerifiedBadge && user.isVerified) {
            val badgeSize = (size * 0.32f).coerceAtLeast(12.dp)
            Box(
                modifier = Modifier
                    .size(badgeSize)
                    .align(Alignment.BottomEnd)
                    .offset(x = 2.dp, y = 2.dp)
                    .clip(CircleShape)
                    .background(IndigoPrimary)
                    .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Verified",
                    tint = Color.White,
                    modifier = Modifier.size(badgeSize * 0.7f)
                )
            }
        }
    }
}
