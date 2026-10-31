package com.ai.safe.agent.api.chat.llmprovider

import com.ai.safe.agent.data.model.ApiProviderType
import org.junit.Assert.assertEquals
import org.junit.Test

class EndpointCompleterAnthropicPathTest {

    @Test fun anthropicPathEndingInAnthropicWithSlash_appendsMessages() {
        assertEquals(
            "https://proxy.example.com/anthropic/v1/messages",
            EndpointCompleter.completeEndpoint("https://proxy.example.com/anthropic/", ApiProviderType.ANTHROPIC)
        )
    }

    @Test fun anthropicExistingMessagesPath_staysUnchanged() {
        val endpoint = "https://proxy.example.com/v1/messages"
        assertEquals(endpoint, EndpointCompleter.completeEndpoint(endpoint, ApiProviderType.ANTHROPIC))
    }
}
