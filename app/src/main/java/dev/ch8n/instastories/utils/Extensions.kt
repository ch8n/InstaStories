package dev.ch8n.instastories.utils

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role

fun randomColor() =
    listOf(Color.Cyan, Color.Magenta, Color.Yellow, Color.LightGray)
        .shuffled()
        .first()

@Composable
fun Modifier.noRippleClick(
    onClick: () -> Unit
) = then(
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
)

sealed class ResultOf<out T> {
    data class Success<T>(val value: T) : ResultOf<T>()
    data class Error(
        val message: String?,
        val throwable: Throwable?
    ) : ResultOf<Nothing>()

    companion object {
        suspend fun <T> build(block: suspend () -> T): ResultOf<T> {
            return try {
                ResultOf.Success(block.invoke())
            } catch (e: Exception) {
                ResultOf.Error(e.message, e.cause)
            }
        }
    }
}