
package com.hzq.plus;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.jps.incremental.ModuleLevelBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * 功能说明
 *
 * @author 黄震强
 * @version 1.0.0
 * @date 2020/7/10 14:44
 */
public class MyFormSwing {
    private JPanel north = new JPanel();

    private JPanel center = new JPanel();

    private JPanel south = new JPanel();

    //为了让位于底部的按钮可以拿到组件内容，这里把表单组件做成类属性
    /*private JLabel r1 = new JLabel("输出：");
    private JLabel r2 = new JLabel("NULL");*/

    private JLabel ip = new JLabel("mysql IP地址（如127.0.0.1:3306/my_db）：");
    private JTextField ipContent = new JTextField("127.0.0.1:3306/my_db");

    private JLabel userName = new JLabel("mysql用户名：");
    private JTextField userNameContent = new JTextField("root");

    private JLabel pass = new JLabel("mysql密码：");
    private JTextField passContent = new JTextField("123456");

    private JLabel name = new JLabel("代码注释作者：");
    private JTextField nameContent = new JTextField("huangzq");

    private JLabel module = new JLabel("maven模块路径（如 /mall-store-backend）：");
    private JTextField moduleContent = new JTextField("/mall-store-backend");

    private JLabel mainPkg = new JLabel("代码输出主包名（如com.lingzhi.saas）：");
    private JTextField mainPkgContent = new JTextField("com.lingzhi.saas");

    private JLabel secPkg = new JLabel("代码输出子包名（如module）：");
    private JTextField secPkgContent = new JTextField("module");

    private JLabel table = new JLabel("表名（如module_config）：");
    private JTextField tableContent = new JTextField("module_config");

    public JPanel initNorth() {

        //定义表单的标题部分，放置到IDEA会话框的顶部位置

        JLabel title = new JLabel("代码生成参数设置");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 26)); //字体样式
        title.setHorizontalAlignment(SwingConstants.CENTER); //水平居中
        title.setVerticalAlignment(SwingConstants.CENTER); //垂直居中
        north.add(title);

        return north;
    }

    public JPanel initCenter() {

        //定义表单的主体部分，放置到IDEA会话框的中央位置

        //一个简单的3行2列的表格布局
        center.setLayout(new GridLayout(8, 2));

        //row1：按钮事件触发后将结果打印在这里
        /*r1.setForeground(new Color(255, 47, 93)); //设置字体颜色
        center.add(r1);
        r2.setForeground(new Color(139, 181, 20)); //设置字体颜色
        center.add(r2);*/

        //row2：作者
        center.add(name);
        center.add(nameContent);

        //row2：ip
        center.add(ip);
        center.add(ipContent);

        //row2：作者
        center.add(userName);
        center.add(userNameContent);

        //row2：作者
        center.add(pass);
        center.add(passContent);

        //row2：作者
        center.add(module);
        center.add(moduleContent);

        //row3：主包
        center.add(mainPkg);
        center.add(mainPkgContent);

        //row3：子包
        center.add(secPkg);
        center.add(secPkgContent);

        //row3：表名
        center.add(table);
        center.add(tableContent);

        return center;
    }

    public JPanel initSouth(Project project,MyDialog myDialog) {

        //定义表单的提交按钮，放置到IDEA会话框的底部位置

        JButton submit = new JButton("提交");
        submit.setHorizontalAlignment(SwingConstants.CENTER); //水平居中
        submit.setVerticalAlignment(SwingConstants.CENTER); //垂直居中
        south.add(submit);

        //按钮事件绑定
        submit.addActionListener(e -> {
            //获取到name和age
            String name = nameContent.getText();
            String ip = ipContent.getText();
            String user = userNameContent.getText();
            String pass = passContent.getText();

            String main = mainPkgContent.getText();
            String sec = secPkgContent.getText();
            String table = tableContent.getText();
            String module = moduleContent.getText();
            //刷新r2标签里的内容，替换为name和age
//            r2.setText(String.format("name:%s, age:%s", name, age));

            String path = project.getBasePath();
            try {
//                THCodeGenerator.init(path,"黄震强" ,"com.hzq.demo","elevator","module_elevator_data");
                THCodeGenerator.init(path,ip,user,pass,name ,main,sec,table,module);
//            THCodeGenerator.init(path);
            }catch (Exception ex){
                Messages.showMessageDialog(project, ex.getMessage(), "Error", Messages.getErrorIcon());
            }

            Messages.showMessageDialog(project, "生成完毕", "info", Messages.getInformationIcon());
            //关闭对话框
//            myDialog.doCancelAction();

//            myDialog.close(1);

        });

        return south;
    }
}
