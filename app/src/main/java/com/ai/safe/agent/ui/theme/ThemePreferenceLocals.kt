package com.ai.safe.agent.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ai.safe.agent.data.preferences.ActivePromptManager
import com.ai.safe.agent.data.preferences.CharacterCardManager
import com.ai.safe.agent.data.preferences.ThemePreferenceSnapshot
import com.ai.safe.agent.data.preferences.ThemePreferenceValues

val LocalThemePreferenceSnapshot =
    compositionLocalOf<ThemePreferenceSnapshot> {
        error("LocalThemePreferenceSnapshot is not provided.")
    }

@Composable
fun rememberActiveThemePreferenceSnapshot(): ThemePreferenceSnapshot {
    val context = LocalContext.current
    val activePromptManager = remember(context) { ActivePromptManager.getInstance(context) }
    val themeSnapshot by activePromptManager.activeThemePreferenceSnapshotFlow.collectAsState(
        initial =
            ThemePreferenceSnapshot(
                source = "character_card",
                sourceId = CharacterCardManager.DEFAULT_CHARACTER_CARD_ID,
                values = ThemePreferenceValues.defaultVisual(),
            ),
    )
    return themeSnapshot
}
