package com.github.jinelei.plugin.toolWindow

import com.github.jinelei.plugin.SpiritBundle
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.content.ContentFactory


class SpiritToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("SpiritToolWindowFactory init")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        // 创建UI: EntityList
        val entityList = JBBox.createVerticalBox().apply {
            add(JBLabel("this is entity list"))
            add(JBLabel("entity a"))
            add(JBLabel("entity b"))
            add(JBLabel("entity c"))
        }

        // 创建UI: TabbedPane
        val pane = JBTabbedPane().apply {
            add(SpiritBundle.message("tabbedTitleEntity", project.name), entityList)
            add("tab b", JBLabel("this is tab b"))
        }

        // 追加到toolWindow
        ContentFactory.getInstance().createContent(pane, null, true).apply {
            toolWindow.contentManager.addContent(this)
            toolWindow.hide()
        }
    }

    override fun shouldBeAvailable(project: Project) = true

}
