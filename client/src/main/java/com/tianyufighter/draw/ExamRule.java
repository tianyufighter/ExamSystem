package com.tianyufighter.draw;

import com.tianyufighter.actionListener.LoginListener;
import com.tianyufighter.actionListener.StartListener;
import com.tianyufighter.actionListener.VerifyListener;
import com.tianyufighter.function.ValidCode;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * 登录界面
 */
public class ExamRule {
    // 创建窗口
    private JFrame frame = null;
    // 客户端的输入流
    private DataInputStream in = null;
    // 客户端的输出流
    private DataOutputStream out = null;
    public ExamRule(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
        initialize();
    }

    /**
     * 初始化窗口，并把组件添加到窗口上
     */
    private void initialize() {
        //加载图片
        ImageIcon icon=new ImageIcon("D:\\3.jpg");
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        JFrame frame=new JFrame();

        //获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)frame.getContentPane();
        j.setOpaque(false);

        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        // 考场规则内容的标题
        JLabel title = new JLabel();
        title.setForeground(Color.PINK);
        title.setText("<html><br/><br/>考场规则</html>");
        title.setFont(new Font("宋体", Font.BOLD, 20));
        panel.add(title);
        // 显示考场规则的标签
        JLabel label1 = new JLabel();
        label1.setText("<html>" +
                "一、考生凭借准考证入场" +
                "<br/><br/>二、考试结束考生应立即停止答题" +
                "<br/><br/>三、严谨携带各种通讯工具" +
                "<br/><br/>四、未到考试时间请勿答题" +
                "<br/><br/>五、开考15分钟后不得进入考场参加考试" +
                "<br/><br/>六、考试时不得交头接耳、不得大声喧哗" +
                "<br/><br/>七、考生不遵守考试纪律将视作违纪、违规" +
                "</html>");
        label1.setFont(new Font("宋体", Font.BOLD, 15));
        panel.add(label1);

        // 设置开始按钮
        JButton button = new JButton("开始考试");
        panel.add(button);

        //必须设置为透明的。否则看不到图片
        panel.setOpaque(false);

        // 设置窗体的标题
        frame.setTitle("Examination rules ");
        frame.setSize(400, 500);
        // 设置窗体的关闭操作，3标识关闭窗体并退出程序
        frame.setDefaultCloseOperation(3);
        frame.add(panel);
        frame.add(panel);
        frame.setSize(400,500);
        frame.setVisible(true);
        // 设置窗体相对于另一个组件居中，参数null表示窗体相对于屏幕的中央位置
        frame.setLocationRelativeTo(null);
        // 设置禁止调整窗口的大小
        frame.setResizable(false);
        // 实例化开始考试按钮监听器类的对象
        StartListener startListener = new StartListener(frame, in, out);
        // 对当前窗口监听方法
        button.addActionListener(startListener);

    }
    public static void main(String[] args) {
        ExamRule login = new ExamRule(null, null);
    }
}
