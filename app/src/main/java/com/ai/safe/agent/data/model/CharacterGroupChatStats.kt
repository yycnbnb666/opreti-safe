package com.ai.safe.agent.data.model

/**
 * 角色群组聊天统计结果
 */
data class CharacterGroupChatStats(
    val characterGroupId: String?,
    val chatCount: Int,
    val messageCount: Int
)
