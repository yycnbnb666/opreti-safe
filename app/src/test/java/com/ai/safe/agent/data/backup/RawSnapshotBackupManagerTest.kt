package com.ai.safe.agent.data.backup

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RawSnapshotBackupManagerTest {

    @Test
    fun snapshotPackageName_acceptsOperitPackagePrefix() {
        assertTrue(isSupportedSnapshotPackageName("com.ai.safe.agent"))
        assertTrue(isSupportedSnapshotPackageName("com.ai.safe.agent.debug"))
        assertTrue(isSupportedSnapshotPackageName("com.ai.safe.agent.clone"))
    }

    @Test
    fun snapshotPackageName_rejectsDifferentPackagePrefix() {
        assertFalse(isSupportedSnapshotPackageName("com.ai.assistance.other"))
        assertFalse(isSupportedSnapshotPackageName("com.example.operit"))
    }
}
