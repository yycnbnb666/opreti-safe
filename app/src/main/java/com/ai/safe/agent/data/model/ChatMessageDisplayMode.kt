package com.ai.safe.agent.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class ChatMessageDisplayMode {
    NORMAL,
    HIDDEN_PLACEHOLDER
}
