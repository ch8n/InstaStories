package dev.ch8n.instastories.ui.features.storiesHome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastJoinToString
import dev.ch8n.instastories.utils.TestTags
import dev.ch8n.instastories.utils.randomColor

@Composable
fun CircularUserIcon(
    userName: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val userInitials = remember(userName) {
        userName.split(" ")
            .map { it.firstOrNull()?.uppercase() ?: "" }
            .fastJoinToString("")
    }

    val randomColor = remember(userName) { randomColor() }
    val backgroundModifier = remember(isLoading) {
        if (isLoading) {
            Modifier
                .padding(4.dp)
                .background(Color.Gray.copy(alpha = 0.8f), CircleShape)
        } else {
            Modifier
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        listOf(Color.Yellow, Color.Red, Color.Magenta),
                        center = Offset.Zero
                    ),
                    shape = CircleShape
                )
                .padding(4.dp)
                .background(randomColor, CircleShape)
        }
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .then(backgroundModifier)
    ) {
        Text(
            text = userInitials,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}