package dev.ch8n.instastories.ui.features.storiesPreview.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.ch8n.instastories.R
import dev.ch8n.instastories.utils.noRippleClick

@Composable
fun CrossIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_close_24),
        contentDescription = null,
        modifier = modifier
            .noRippleClick(onClick),
        tint = Color.Gray
    )
}