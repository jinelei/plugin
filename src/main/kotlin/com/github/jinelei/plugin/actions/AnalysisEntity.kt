package com.github.jinelei.plugin.actions

import com.intellij.ide.util.PackageChooserDialog
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.notificationGroup
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiKeyword
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * @Author: jinelei
 * @Description:
 * @Date: 2023/7/4 17:41
 * @Version: 1.0.0
 */
class AnalysisEntity : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        notificationGroup.createNotification("Analysis entity class", MessageType.INFO).notify(e.project)

        e.getData(CommonDataKeys.PSI_FILE)?.takeIf { it is PsiJavaFile }.let {
            // 获取当前的 PsiJavaFile
            val psiJavaFile = it as PsiJavaFile
            // 获取当前 PsiJavaFile 中的所有 PsiClass
            psiJavaFile.classes.forEach { psiClass ->
//                // 获取当前 PsiClass 的包路径
//                val packageName = triggerUserSelectPackage(e)
//
//                notificationGroup.createNotification("package: $packageName", MessageType.WARNING).notify(e.project)

                // 获取当前 PsiClass 的字段定义
                psiClass.fields.forEach { field ->
                    val fieldModifier =
                        field.modifierList?.children?.filterIsInstance<PsiKeyword>()
                            ?.getOrNull(0)?.text
                    val fieldType = field.typeElement?.text
                    val fieldName = field.name
                    notificationGroup.createNotification(
                        "field: $fieldModifier $fieldType $fieldName",
                        MessageType.WARNING
                    ).notify(e.project)
                }

                buildOperationPanel(e)
            }
        }
    }

    override fun update(e: AnActionEvent) {
        // 获取当前选中的文件
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        // 如果选中的是 Java 文件，则启用 Action，否则禁用 Action
        e.presentation.isEnabled = file != null && "java" == file.extension
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return super.getActionUpdateThread()
    }

    fun triggerUserSelectPackage(e: AnActionEvent): String {
        val module = ModuleUtilCore.findModuleForFile(e.getData(CommonDataKeys.VIRTUAL_FILE)!!, e.project!!)
        val packageChooser = PackageChooserDialog("Please select package path", module!!)
        packageChooser.show()
        val selectedPackage = packageChooser.selectedPackage
        return selectedPackage.qualifiedName
    }

    fun buildOperationPanel(e: AnActionEvent) {
        // 获取当前项目
        val project = e.project ?: return

        // 获取工具窗口管理器和工具窗口
        val toolWindowManager = ToolWindowManager.getInstance(project)
        val toolWindow = toolWindowManager.getToolWindow(toolWindowManager.activeToolWindowId) ?: return
//        val toolWindow = toolWindowManager.getToolWindow("MyToolWindow") ?: return

        // 创建面板
        val panel = SimpleToolWindowPanel(true, true)
        panel.setContent(JPanel().apply {
            add(JLabel("这是一个面板"))
        })

        // 将面板添加到工具窗口
        toolWindow.contentManager.removeAllContents(true)
        toolWindow.contentManager.addContent(toolWindow.contentManager.factory.createContent(panel, null, false))

        // 显示工具窗口
        toolWindow.activate(null)
        toolWindow.show()
    }
}