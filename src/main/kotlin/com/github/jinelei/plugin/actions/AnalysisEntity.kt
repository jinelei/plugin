package com.github.jinelei.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: jinelei
 * @Description:
 * @Date: 2023/7/4 17:41
 * @Version: 1.0.0
 */
public class AnalysisEntity extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(true);
        update(e);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 获取当前选中的文件
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        // 如果选中的是 Java 文件，则启用 Action，否则禁用 Action
        e.getPresentation().setEnabled(file != null && "java".equals(file.getExtension()));
    }
}
