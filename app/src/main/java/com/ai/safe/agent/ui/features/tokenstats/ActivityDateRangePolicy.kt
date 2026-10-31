package com.ai.safe.agent.ui.features.tokenstats

import com.ai.safe.agent.data.stats.TokenActivityViewMode
import com.ai.safe.agent.data.stats.TokenStatsTimeRange
import com.ai.safe.agent.data.stats.TokenStatsTimeRanges
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

internal fun activityRangeAnchorDate(
    range: TokenStatsTimeRange,
    zone: ZoneId,
): LocalDate = Instant.ofEpochMilli(range.endMs - 1L).atZone(zone).toLocalDate()

internal fun activityRangeForMode(
    mode: TokenActivityViewMode,
    anchorDate: LocalDate,
    historyStartDate: LocalDate?,
    zone: ZoneId,
): TokenStatsTimeRange? {
    val startDate = when (mode) {
        TokenActivityViewMode.DAILY -> anchorDate
        TokenActivityViewMode.WEEKLY -> {
            // Keep the range aligned with TokenActivityAggregator's Sunday-first weeks.
            anchorDate.minusDays((anchorDate.dayOfWeek.value % 7).toLong())
        }
        TokenActivityViewMode.CUMULATIVE -> historyStartDate ?: return null
    }
    if (startDate.isAfter(anchorDate)) return null
    return TokenStatsTimeRanges.customRange(
        startDate.atStartOfDay(zone).toInstant().toEpochMilli(),
        anchorDate.plusDays(1L).atStartOfDay(zone).toInstant().toEpochMilli(),
    )
}
