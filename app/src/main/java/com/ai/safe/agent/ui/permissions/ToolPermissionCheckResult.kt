package com.ai.safe.agent.ui.permissions

import androidx.annotation.StringRes
import com.ai.safe.agent.R

/** Result of the permission gate before a tool starts executing. */
enum class ToolPermissionCheckResult(
    val isGranted: Boolean,
    @StringRes val errorMessageResId: Int? = null,
) {
    GRANTED(isGranted = true),
    DENIED(
        isGranted = false,
        errorMessageResId = R.string.tool_permission_execution_denied,
    ),
    OVERLAY_PERMISSION_REQUIRED(
        isGranted = false,
        errorMessageResId = R.string.tool_permission_overlay_required_for_confirmation,
    ),
    CONFIRMATION_TIMEOUT(
        isGranted = false,
        errorMessageResId = R.string.tool_permission_confirmation_timeout,
    ),
}
