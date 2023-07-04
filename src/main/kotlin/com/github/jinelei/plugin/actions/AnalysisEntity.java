package com.github.jinelei.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @Author: jinelei
 * @Description:
 * @Date: 2023/7/4 17:41
 * @Version: 1.0.0
 */
public class AnalysisEntity extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        System.out.println(e);
    }
}
