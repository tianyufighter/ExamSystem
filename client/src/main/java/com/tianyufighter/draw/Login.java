package com.tianyufighter.draw;

import com.tianyufighter.actionListener.LoginListener;
import com.tianyufighter.actionListener.VerifyListener;
import com.tianyufighter.function.ReceiveMessage;
import com.tianyufighter.function.ValidCode;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * 登录界面
 */
public class Login{
    // 创建窗口
    public JFrame frame = null;
    // 客户端的输入流
    private DataInputStream in = null;
    // 客户端的输出流
    private DataOutputStream out = null;
    // 换一张验证码的按钮
    public JButton button1 = null;
    // 准考证输入框
    public JTextField textName = null;
    // 密码输入框
    public JPasswordField password = null;
    public Login() {
        initialize();
    }

    public Login(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
        initialize();
    }

    /**
     * 初始化窗口，并把组件添加到窗口上
     */
    private void initialize() {
        // 把登录页面对象传给DistributorHandler
        ReceiveMessage.login = this;
        //加载图片
        ImageIcon icon=new ImageIcon("D:\\2.jpg");
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        frame=new JFrame();

        //获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)frame.getContentPane();
        j.setOpaque(false);

        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        JLabel temp = new JLabel();
        temp.setPreferredSize(new Dimension(400, 100));
        panel.add(temp);
        // 实例化JLabel标签对象，该对象显示"账号: "
        JLabel labName = new JLabel("账号: ");
        // 将labName标签添加到窗体上
        panel.add(labName);

        // 实例化JTextField标签对象
        textName = new JTextField();
        Dimension dim1 = new Dimension(300, 30);
        //textName.setSize(dim);//setSize这方法只对顶级容器有效，其他组件使用无效。
        textName.setPreferredSize(dim1);//设置除顶级容器组件其他组件的大小
        // 将textName标签添加到窗体上
        panel.add(textName);

        //实例化JLabel标签对象，该对象显示"密码："
        JLabel labpass= new JLabel("密码: ");
        //将labpass标签添加到窗体上
        panel.add(labpass);

        //实例化JPasswordField
        password = new JPasswordField();
        //设置大小
        password.setPreferredSize(dim1);//设置组件大小
        //添加textword到窗体上
        panel.add(password);

        /*
        * 验证码
        * */
        //实例化JLabel标签对象，该对象显示"密码："
        JLabel verify = new JLabel("验证码：");
        //将labpass标签添加到窗体上
        panel.add(verify);

        // 输入验证码的框
        JTextField yanzheng = new JTextField();
        Dimension dim3 = new Dimension(60, 30);
        //textName.setSize(dim);//setSize这方法只对顶级容器有效，其他组件使用无效。
        yanzheng.setPreferredSize(dim3);//设置除顶级容器组件其他组件的大小
        // 将textName标签添加到窗体上
        panel.add(yanzheng);


        //生成验证码图片
        ValidCode validCode = new ValidCode();
        ImageIcon verifyImage =new ImageIcon(validCode.getImage());
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label1=new JLabel(verifyImage);
        panel.add(label1);

        // 换验证码的按钮
        button1 = new JButton();
        button1.setText("换一张");
        button1.setPreferredSize(new Dimension(100, 30));
        panel.add(button1);

        //实例化JButton组件
        JButton button=new JButton();
        //设置按钮的显示内容
        Dimension dim2 = new Dimension(160,30);
        button.setText("登录");
        //设置按钮的大小
        button.setPreferredSize(dim2);
        panel.add(button);



        //必须设置为透明的。否则看不到图片
        panel.setOpaque(false);

        // 设置窗体的标题
        frame.setTitle("Login");
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

        // 首先实例化登录按钮监听类的对象，并把登录界面中账号和密码输入框的对象传给它
        LoginListener ll = new LoginListener(in, out, textName, password, yanzheng, validCode, this);
        // 对当前窗口添加监听方法
        button.addActionListener(ll);

        // 实例化验证码按钮监听器类的对象，并把验证码输入框中对象和画验证码的对象传进
        VerifyListener verifyListener = new VerifyListener(validCode, verifyImage, label1);
        // 对当前窗口监听方法
        button1.addActionListener(verifyListener);
    }
    public static void main(String[] args) {
        Login login = new Login();
    }
}
