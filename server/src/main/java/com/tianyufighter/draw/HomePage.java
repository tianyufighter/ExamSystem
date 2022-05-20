package com.tianyufighter.draw;

import com.tianyufighter.actListener.*;
import com.tianyufighter.model.ChoiceQuestion;
import com.tianyufighter.model.JudgeQuestion;
import com.tianyufighter.model.User;
import com.tianyufighter.util.CountDown;
import com.tianyufighter.util.FlushTime;
import com.tianyufighter.util.StoreCurrentUser;
import com.tianyufighter.util.UpdateOnline;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端的操作界面
 */
public class HomePage {
    // 创建窗口
    public JFrame frame = null;
    // 服务端的输入流
    public DataInputStream in = null;
    // 服务端的输出流
    public DataOutputStream out = null;
    // 菜单，导入试卷
    public Menu fileMenu = new Menu("File");
    public MenuItem fileOpen = new MenuItem("Open");
    public MenuItem fileExit = new MenuItem("Exit");
    // 设置考试时间的菜单
    public Menu setting = new Menu("Setting");
    public MenuItem examTime = new MenuItem("ExamTime");
    public MenuBar menu = null;
    // 得到单选题，多选题，判断题的题目和答案
//    public ChoiceQuestion[] danxuan = null;
    public List<ChoiceQuestion> danxuan = null;
    public List<ChoiceQuestion> duoxuan = null;
    public List<JudgeQuestion> panduan = null;
    // 用户信息的按钮
    public List<User> user = null;
    // 加载用户信息的按钮
    public JButton button = null;
    // 存放背景图片的标签
    public JLabel label;
    // 显示用户数据的表格对象
    public JTable table = null;
    // 显示用户信息的面板
    public JScrollPane scrollPane = null;
    // 显示用户信息的面板
    public JPanel panel = null;
    // 设置当前系统的时间
    public JLabel label3 = null;
    // 记录在线的人数
    public static int onlinePeople = 0;
    // 存储当前类的对象用于服务端共享该类
//    public static HomePage servicePage;
    // 显示倒计时的标签
    public JLabel label2 = null;
    // 设置考生在线人数的标签
    public JLabel label1 = null;
    // 存放用户设定的考试时间
    public static String testTime = "";
    // 存放用户设定的考试时长
    public static String testLongTime = "";
    // 存储成绩按钮
    private JButton  store = null;
    // 改卷的按钮
    private JButton correct = null;
    // 存储不同系统的答案
    // 选择题答案
    public static List<Integer> ans1 = new ArrayList<Integer>();
    // 判断题答案
    public static List<Integer>  ans2 = new ArrayList<Integer>();
    // 标记用户是否可以连接，当考试时间设置完成后用户就可以连接了
    public static boolean isConnect = false;

    public HomePage() {
        fileMenu.add(fileOpen);
        fileMenu.addSeparator();
        fileMenu.add(fileExit);
        setting.add(examTime);
        menu = new MenuBar();
        menu.add(fileMenu);
        menu.add(setting);
        menu.setFont(new Font("宋体", Font.BOLD, 16));
    }

    /**
     * 启动客户端客气客户端界面
     */
    public void launch() {
        initialize();
    }
    /**
     * 初始化窗口，并把组件添加到窗口上
     */
    private void initialize() {
        //加载图片
//        ImageIcon icon=new ImageIcon("D:\\4.jpg");
        Image icon=new ImageIcon("D:\\4.jpg").getImage();
        //Image im=new Image(icon);
        //将图片放入label中
//        label=new JLabel(icon);
        label=new aLabel(icon);

        //设置label的大小
//        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        label.setBounds(0,0,950,850);
        frame=new JFrame();

        //获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)frame.getContentPane();
        j.setOpaque(false);


        // 页面上面的面板
        JPanel topPanel = new JPanel();
        // 将topPanel面板设置为透明
        topPanel.setOpaque(false);
//        frame.getContentPane().add(topPanel, BorderLayout.SOUTH);
        // 设置topPanel布局为GridLayout布局
//        topPanel.setLayout(new GridLayout(1, 3, 0, 0));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        // 设置考生在线人数的标签
        label1 = new JLabel("考生在线人数: "+onlinePeople+" / 30", JLabel.CENTER);
        // 每隔一秒更新一下在线人数的标签
        new UpdateOnline(this).execute();

        topPanel.add(label1);
        label1.setFont(new Font("宋体", Font.BOLD, 19));
        label1.setForeground(Color.LIGHT_GRAY);

        // 填充label1和label2之间的空白
        topPanel.add(Box.createHorizontalGlue());

        // 设置倒计时的标签
        label2 = new JLabel("距离考试结束时间:00时00分00秒", JLabel.CENTER);
        topPanel.add(label2);
        label2.setFont(new Font("宋体", Font.BOLD, 19));
        label2.setForeground(Color.LIGHT_GRAY);

        // 填充label2 和label3之间的空白
        topPanel.add(Box.createHorizontalGlue());

        // 设置当前时间的标签
        label3 = new JLabel("当前时间: 2020/12/21 12:22:21", JLabel.CENTER);
        topPanel.add(label3);
        label3.setFont(new Font("宋体", Font.BOLD, 19));
        label3.setForeground(Color.LIGHT_GRAY);


        // 显示用户信息的面板
        panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        //实例化JButton组件
        button = new JButton("读取考生信息");
        panel.add(button);
        //必须设置为透明的。否则看不到图片
        panel.setOpaque(false);


        // 页面下面的面板
        JPanel butPanel = new JPanel();
        // 将butPanel面板设置为透明
//        butPanel.setOpaque(false);
        // 设置butPanel布局为GridLayout布局
        butPanel.setBackground(new Color(51,73,86));
        butPanel.setLayout(new GridLayout(1, 3, 20, 0));
        // 设置刷新用户列表的标签
        JButton refresh = new JButton("刷新");
        butPanel.add(refresh);
        refresh.setFont(new Font("宋体", Font.BOLD, 20));
//        refresh.setForeground(Color.LIGHT_GRAY);
        refresh.setForeground(Color.BLACK);

        // 设置收卷的标签
        store = new JButton("存储");
        butPanel.add(store);
        store.setFont(new Font("宋体", Font.BOLD, 20));
//        accept.setForeground(Color.LIGHT_GRAY);
        store.setForeground(Color.BLACK);

        // 设置改卷的标签
        JButton correct = new JButton("改卷");
        butPanel.add(correct);
        correct.setFont(new Font("宋体", Font.BOLD, 20));
//        correct.setForeground(Color.LIGHT_GRAY);
        correct.setForeground(Color.BLACK);



        // 设置frame的布局
//        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frame.setLayout(new BorderLayout(0, 0));
        // 将菜单加入到窗口中
        frame.setMenuBar(menu);
        // 设置窗体的标题
        frame.setTitle("考试系统");
        frame.setSize(950, 850);
//        label.setBounds(0, 0, frame.getWidth(), frame.getHeight());/
        // 设置窗体的关闭操作，3标识关闭窗体并退出程序
        frame.setDefaultCloseOperation(3);
        // 将显示用户列表的面板放在窗口窗口中央
//        frame.add(panel);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        // 设置窗体相对于另一个组件居中，参数null表示窗体相对于屏幕的中央位置
        frame.setLocationRelativeTo(null);
        // 设置禁止调整窗口的大小
        frame.setResizable(true);

        // 给菜单项中的"Open"添加监听事件
        MenuListener loadPaperListener = new MenuListener(this);
        fileOpen.addActionListener(loadPaperListener);
        // 给菜单项中的"Exit"添加监听事件
        fileExit.addActionListener(loadPaperListener);
        // 给菜单项中的"Exam Time"添加监听事件
        examTime.addActionListener(loadPaperListener);

        // 给"读取考生信息"的按钮添加监听事件
        ButtonListener buttonListener = new ButtonListener(this);
        button.addActionListener(buttonListener);
        // 给frame添加监听事件
        WindowChangeListener windowChangeListener = new WindowChangeListener(this);
        frame.addComponentListener(windowChangeListener);

        // 每个一秒刷新当前窗口的时间
        FlushTime flushTime = new FlushTime(this);
        flushTime.execute();

        // 启动倒计时功能，每隔1秒刷新一下存放倒计时的面板
//        CountDown countDown = new CountDown(this, 100);
//        countDown.execute();
        // 为改卷按钮添加监听事件
        CorrectListener correctListener = new CorrectListener(this);
        correct.addActionListener(correctListener);
        // 为存储按钮添加监听事件
        StoreListener storeListener = new StoreListener(this);
        store.addActionListener(storeListener);
        // 为刷新按钮添加监听事件
        RenovateListener renovateListener = new RenovateListener(this);
        refresh.addActionListener(renovateListener);
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

    public static void main(String[] args) {
        HomePage login = new HomePage();
        login.launch();
    }
}
