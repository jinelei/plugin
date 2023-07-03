package com.github.jinelei.plugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.jinelei.plugin.MyBundle

@Service(Service.Level.PROJECT)
class SpiritProjectService(project: Project) {

    init {
        thisLogger().warn(MyBundle.message("projectService", project.name))
    }

    fun getRandomNumber() = (1..100).random()
}
