package com.github.jinelei.plugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.jinelei.plugin.SpiritBundle

@Service(Service.Level.PROJECT)
class SpiritProjectService(project: Project) {

    init {
        thisLogger().warn(SpiritBundle.message("projectService", project.name))
    }

}
