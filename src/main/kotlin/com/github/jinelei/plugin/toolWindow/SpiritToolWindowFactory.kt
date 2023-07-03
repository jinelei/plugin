package com.github.jinelei.plugin.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.jinelei.plugin.services.SpiritProjectService
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import java.awt.Dimension


class SpiritToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("SpiritToolWindowFactory init")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val window = SpiritToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(window.getContent(), null, true)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class SpiritToolWindow(toolWindow: ToolWindow) {
        private val minWidth: Int = 100
        private val minHeight: Int = 100

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val box = JBBox.createVerticalBox()
            val jbTabbedPane = JBTabbedPane()

            jbTabbedPane.addTab("tab1", JBScrollPane(JBLabel("this is tab1")))
            jbTabbedPane.addTab("tab2", JBScrollPane(JBLabel("this is tab2")))
            jbTabbedPane.addTab("tab3", JBScrollPane(JBLabel("this is tab3")))
            jbTabbedPane.addTab("tab4", JBScrollPane(JBLabel("this is tab4")))
            jbTabbedPane.addTab("tab5", JBScrollPane(JBLabel("this is tab5")))

            box.add(jbTabbedPane)
            add(box)

            addComponentListener(object : java.awt.event.ComponentAdapter() {
                override fun componentResized(e: java.awt.event.ComponentEvent?) {
                    super.componentResized(e)
                    box.preferredSize = Dimension(e?.component?.width ?: minWidth, e?.component?.height ?: minHeight)
                }
            })
        }
    }
}
