package com.tianyufighter.draw;



import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

public class ProgressWindow extends SwingWorker<Boolean, Integer>{
    // 进度条的最大值和最小值
    private int PROGRESS_MAX;
    private int PROGRESS_MIN = 1;
    // 控制进度条的变量
    public volatile int currentNum;
    // 显示进度条的窗口对象
    private JFrame frame = null;
    // 进度条
    private JProgressBar pro = new JProgressBar();
    // 显示进度条状态的标签
    public JLabel label1 = new JLabel();
    // 含有试题的窗口的对象
    private JPanel panel1 = null;
    private JPanel jp = null;
    // 客户端的输入流
    private DataInputStream in = null;
    // 客户端的输出流
    private DataOutputStream out = null;
    // 客户端含有试题的界面的对象
    private AnswerInterface answerInterface = null;
    // 显示考试时间未到的窗口
    public PopupMenu popupMenu = null;


    /**
     * 有参构造，创建窗口给相关属性赋初始值
     * @param total 题目总的数量
     */
    public ProgressWindow(int total, DataInputStream in, DataOutputStream out, AnswerInterface answerInterface) {
        this.answerInterface = answerInterface;
        this.in = in;
        this.out = out;
        PROGRESS_MAX = total;
        frame = new JFrame();
        // 设置本类窗口的标题
        frame.setTitle("Loading resources");
//        frame.setBounds(270, 10, 950, 850);
        frame.setSize(950, 850);
        // 设置窗体相对于另一个组件居中，参数null表示窗体相对于屏幕的中央位置
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentNum = PROGRESS_MIN;
        pro.setMaximum(PROGRESS_MAX);
        pro.setMinimum(PROGRESS_MIN);
        // 在进度条中实时显示百分比
        pro.setStringPainted(true);
        pro.setPreferredSize(new Dimension(800,30));
        // 设置背景图片
        ImageIcon imageIcon = new ImageIcon("D:\\1.jpg");
        JLabel label2 = new JLabel(imageIcon);
        panel1 = new JPanel();
        panel1.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        panel1.add(label2);
        frame.add(panel1);
        jp = new JPanel();
        jp.add(pro);
        jp.add(label1);
        frame.add(jp, BorderLayout.SOUTH);
        frame.setVisible(true);
        // 设置禁止调整窗口的大小
        frame.setResizable(false);
    }
    // 测试该类用类的主函数
    public static void main(String[] args) {
        ProgressWindow progressWindow = new ProgressWindow(100, null, null, null);
        progressWindow.execute();
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
            pro.setValue(currentNum);
            // 在进度条右侧显示消息
            label1.setText("已加载: " + currentNum + " / " + PROGRESS_MAX);
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
        int res;
        try {
            Boolean result = get();
            if (result) {
                res = JOptionPane.showConfirmDialog(null,"资源加载成功,点击是否开始考试?","prompting",JOptionPane.YES_NO_OPTION);
                if(res == 0) {

                    // 如果doInBackground方法执行完成后返回0表示用户点击了是的按钮，则执行下面的代码
                    // 初始化考试界面并将题目加载上去
                    answerInterface.initialize();
                    // 还没到考试时间将考试界面设置为不可见
                    answerInterface.frame.setVisible(false);
                    // 提示用户考试时间没有到，请等待考试时间到了才能显示试题
                    popupMenu = new PopupMenu();
                    // 向服务端发送正确答案
//                    sendServer();
                    // 将该进度条窗口关闭
                    frame.dispose();
                } else {
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "哇额，资源加载失败了!", "prompting",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
