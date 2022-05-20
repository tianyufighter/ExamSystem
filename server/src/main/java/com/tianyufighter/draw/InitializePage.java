package com.tianyufighter.draw;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class InitializePage extends SwingWorker<Boolean, Integer> {
    // 进度条的最大值和最小值
    private int PROGRESS_MAX;
    private int PROGRESS_MIN = 1;
    // 控制进度条的变量
    public volatile int currentNum;
    // 显示进度条的窗口对象
    private JFrame frame = null;
    // 进度条
    private JProgressBar pro = new JProgressBar();
    // 含有试题的窗口的对象
    private JPanel panel1 = null;
    private JPanel jp = null;
    // 服务端界面的对象
    public HomePage homePage;


    /**
     * 有参构造，创建窗口给相关属性赋初始值
     * @param total 题目总的数量
     */
    public InitializePage(int total) {
        PROGRESS_MAX = total;
        frame = new JFrame();
        // 设置本类窗口的标题
        frame.setTitle("java考试系统服务端");
        frame.setSize(500, 500);
        // 设置窗体相对于另一个组件居中，参数null表示窗体相对于屏幕的中央位置
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentNum = PROGRESS_MIN;
        pro.setMaximum(PROGRESS_MAX);
        pro.setMinimum(PROGRESS_MIN);
        // 在进度条中实时显示百分比
        pro.setStringPainted(true);
        pro.setPreferredSize(new Dimension(500,30));
        // 设置背景图片
//        ImageIcon imageIcon = new ImageIcon("nine.jpg");
        ImageIcon imageIcon = new ImageIcon("D://9.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(500, 470, Image.SCALE_DEFAULT));
        JLabel label2 = new JLabel(imageIcon);
        panel1 = new JPanel();
        panel1.add(label2);
        frame.add(panel1);
        jp = new JPanel();
        jp.add(pro);
        frame.add(jp, BorderLayout.SOUTH);
        frame.setVisible(true);
        // 设置禁止调整窗口的大小
        frame.setResizable(false);
    }

    /**
     * 显示进度条进度,通过execute()方法开启该进度条
     * @return
     * @throws  Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        // TODO Auto-generated method stub
        // 变量currentNum是由DrawPaper类的readFile方法控制
        while (currentNum <= PROGRESS_MAX) {
            Thread.sleep(30);
            currentNum++;
            pro.setValue(currentNum);
            // 在进度条右侧显示消息
            jp.updateUI();
            panel1.updateUI();
        }

        return true;
    }

    /**
     * 进度条加载完成后执行done方法显示日志
     */
    @Override
    protected void done() {
        try {
            Boolean result = get();
            if (result) {
                // 开启服务端的界面
                homePage = new HomePage();
                homePage.launch();
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "哇额，资源加载失败了!", "prompting",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InitializePage initializePage = new InitializePage(100);
        initializePage.execute();
    }
}
