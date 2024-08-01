package dev.ch8n.instastories.utils

import androidx.compose.ui.graphics.Color

fun randomColor() =
    listOf(Color.Cyan, Color.Magenta, Color.Yellow, Color.LightGray)
        .shuffled()
        .first()