package com.github.jinelei.plugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.notificationGroup
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType

/**
 * @Author: jinelei
 * @Description:
 * @Date: 2023/7/4 17:41
 * @Version: 1.0.0
 */
class AnalysisEntity : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        notificationGroup.createNotification("analysis entity class", MessageType.INFO).notify(e.project)

        PsiTreeUtil.getChildOfType(e.getData(LangDataKeys.PSI_FILE)!!, PsiJavaFile::class.java)?.let {
            it.classes.forEach { psiClass ->
                notificationGroup.createNotification("psi file child: ${psiClass.name}", MessageType.INFO)
                    .notify(e.project)
                psiClass.fields.forEach { field ->
                    notificationGroup.createNotification("field: ${field.name}", MessageType.INFO)
                        .notify(e.project)
                }
            }
        }
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        if (psiFile is PsiJavaFile) {
            val classes = psiFile.classes
            for (psiClass in classes) {
                val fields = psiClass.fields
                for (field in fields) {
                    // do something with the field
                    thisLogger().warn("Field name: ${field.name}")
                }
                val methods = psiClass.methods
                for (method in methods) {
                    // do something with the method
                    thisLogger().warn("Method name: ${method.name}")
                }
                // add more code for other class elements
            }
        }
        e.dataContext.getData(CommonDataKeys.PSI_FILE)?.let {
            it.children.forEach { child ->
                // 判断当前 PsiElement 是否为 PsiClass
                if (child.elementType.toString() == "CLASS") {
                    // 获取当前 PsiClass 的名称
                    val className = child.text
                    notificationGroup.createNotification("psi file child: $className", MessageType.INFO)
                        .notify(e.project)
                    // 获取当前 PsiClass 的所有字段
                    child.children.forEach { field ->
                        notificationGroup.createNotification("field: ${field.text}", MessageType.INFO)
                            .notify(e.project)
                    }
                }
            }
        }
    }

    override fun update(e: AnActionEvent) {
        // 获取当前选中的文件
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        // 如果选中的是 Java 文件，则启用 Action，否则禁用 Action
        e.presentation.isEnabled = file != null && "java" == file.extension
    }
}