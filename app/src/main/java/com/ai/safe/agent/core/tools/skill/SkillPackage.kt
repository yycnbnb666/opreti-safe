package com.ai.safe.agent.core.tools.skill

import java.io.File

data class SkillPackage(
    val name: String,
    val description: String,
    val directory: File,
    val skillFile: File
)
