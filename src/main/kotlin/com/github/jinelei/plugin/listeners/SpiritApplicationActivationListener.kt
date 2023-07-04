package com.github.jinelei.plugin.listeners

import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.wm.IdeFrame
import java.awt.PopupMenu
import javax.swing.JMenuItem

internal class SpiritApplicationActivationListener : ApplicationActivationListener {

    override fun applicationActivated(ideFrame: IdeFrame) {
        thisLogger().warn("applicationActivated")
        // 注册鼠标右键监听
        // 1. 创建 PopupMenu
        PopupMenu().apply {
            add("test")
        }

    }
}
