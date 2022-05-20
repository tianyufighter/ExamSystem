package com.tianyufighter.util;

import com.tianyufighter.draw.HomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ModelDialog extends JDialog implements ActionListener {
    // 模态框的面板对象
    private Container container;
    // 输入年份的对象
    private JTextField textField1 = null;
    // 输入月份的对象
    private JTextField textField2 = null;
    // 日
    private JTextField textField3 = null;
    // 时
    private JTextField textField4 = null;
    // 分
    private JTextField textField5 = null;
    // 秒
    private JTextField textField6 = null;
    // 考试时长
    private JTextField textField7 = null;
    // 存储设置的时间
    private String res = "";
    // 存储设置的考试时长
    private String longTime = "";



    public ModelDialog() {
        super();
    }

    /**
     * 初始化对象并指定参数
     *
     * @param parent 对话框的拥有者
     * @param title  对话框的标题
     * @param modal  用于控制对话框的工作方式(即: 是否为对话框)
     */
    public ModelDialog(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
    }

    private void drawDialog() {
        Font font = new Font("宋体", Font.BOLD, 20);
        // 得到父窗口以及父窗口的左右坐标以及宽高
        Container parent = getParent();
        int parentLeft = parent.getX();
        int parentTop = parent.getY();
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();
        // 设置自己的模态框的宽高
        int width = 500;
        int height = 240;
        setSize(width, height);
        //  将其放在模态框的中间
        setLocation(parentLeft + (parentWidth - width) /2, parentTop + (parentHeight - height) / 2);
        container = getContentPane();
        // 去掉外面的边框修饰和标题
        setUndecorated(false);

        // 设置模态框的布局为流布局
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        // 设置模态框的大小不可变
        setResizable(false);
        JLabel label = new JLabel("设置考试的时间", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(500, 20));
        label.setFont(font);
        label.setForeground(Color.RED);
        container.add(label);


        // 设定时间的输入框
        textField1 = new JTextField(5);
        container.add(textField1);

        JLabel label1 = new JLabel("年");
        container.add(label1);
        label1.setFont(new Font("宋体", Font.BOLD, 16));

        textField2 = new JTextField(5);
        container.add(textField2);

        JLabel label2 = new JLabel("月");
        container.add(label2);
        label2.setFont(new Font("宋体", Font.BOLD, 16));

        textField3 = new JTextField(5);
        container.add(textField3);

        JLabel label3 = new JLabel("日");
        container.add(label3);
        label3.setFont(new Font("宋体", Font.BOLD, 16));

        JLabel t = new JLabel("            ");
        container.add(t);

        textField4 = new JTextField(4);
        container.add(textField4);

        JLabel label4 = new JLabel("时");
        container.add(label4);
        label4.setFont(new Font("宋体", Font.BOLD, 16));

        textField5 = new JTextField(4);
        container.add(textField5);

        JLabel label5 = new JLabel("分");
        container.add(label5);
        label5.setFont(new Font("宋体", Font.BOLD, 16));

        textField6 = new JTextField(4);
        container.add(textField6);

        JLabel label6 = new JLabel("秒");
        container.add(label6);
        label6.setFont(new Font("宋体", Font.BOLD, 16));

        JLabel label7 = new JLabel("考试时长: ");
        container.add(label7);
        label7.setFont(new Font("宋体", Font.BOLD, 16));

        textField7 = new JTextField(5);
        container.add(textField7);

        JLabel label8 = new JLabel("分钟");
        container.add(label8);
        label8.setFont(new Font("宋体", Font.BOLD, 16));

        JLabel label9 = new JLabel("");
        container.add(label9);
        label9.setPreferredSize(new Dimension(width, 1));

        // 设置提交按钮
        JButton button = new JButton("确定");
        container.add(button);
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(this);
    }

    public static void main(String[] args) {
        ModelDialog modelDialog = new ModelDialog();
        modelDialog.showDialog();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        res = textField1.getText() + "/" + textField2.getText() + "/" + textField3.getText() + " " + textField4.getText() + ":" + textField5.getText() + ":" + textField6.getText();
        longTime = textField7.getText();
        JOptionPane.showMessageDialog(container, "考试时间设置成功。");
        // 当考生时间设置成功后客户端就可以接收连接
        HomePage.isConnect = true;
        dispose();
    }

    /**
     * 显示模态框
     */
    public void showDialog() {
        drawDialog();
        try {
            setVisible(true);
        } catch (Exception e) {

        }


    }

    /**
     * 获取设定的时间字符串
     */
    public String getRes() {
        return this.res;
    }

    public String getLongTime() {
        return this.longTime;
    }
    /**
     * 设置背景图片的标签，且图片大小随窗口动态改变
     */
    //内部类
    private class aLabel extends JLabel {
        private Image image;
        public aLabel(Image image){
            this.image = image;
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            int x = this.getWidth();
            int y = this.getHeight();

            g.drawImage(image, 0, 0, x, y, null);
        }
    }
}
