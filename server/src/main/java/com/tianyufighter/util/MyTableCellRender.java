package com.tianyufighter.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 设置服务端界面中用户状态的颜色
 */
public class MyTableCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if("未登录".equals(value)) {
            setForeground(Color.GRAY);
        } else if("已登录".equals(value)){
            setForeground(Color.GREEN);
        } else if("已提交".equals(value)) {
            setForeground(Color.CYAN);
        } else {
            setForeground(Color.RED);
        }
        return c;
    }
}
