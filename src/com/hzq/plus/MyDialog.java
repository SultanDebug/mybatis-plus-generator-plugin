
package com.hzq.plus;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 功能说明
 *
 * @author 黄震强
 * @version 1.0.0
 * @date 2020/7/10 14:41
 */
public class MyDialog extends DialogWrapper {
    private Project project ;

    //swing样式类，定义在4.3.2
    private MyFormSwing formSwing = new MyFormSwing();

    public MyDialog(@Nullable Project project) {
        super(project);
        setTitle("天虹代码生成器"); // 设置会话框标题
        this.project = project;
        init(); //触发一下init方法，否则swing样式将无法展示在会话框
    }

    // 重写下面的方法，返回一个自定义的swing样式，该样式会展示在会话框的最上方的位置
    @Override
    protected JComponent createNorthPanel() {
        return formSwing.initNorth();
    }

    // 重写下面的方法，返回一个自定义的swing样式，该样式会展示在会话框的最下方的位置
    @Override
    protected JComponent createSouthPanel() {
        return formSwing.initSouth(project,this);
    }

    @Override
    protected JComponent createCenterPanel() {
        return formSwing.initCenter();
    }
}
