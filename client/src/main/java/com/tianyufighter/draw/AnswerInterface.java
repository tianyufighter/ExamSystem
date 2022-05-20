package com.tianyufighter.draw;

import com.tianyufighter.actionListener.ButtonListener;
import com.tianyufighter.actionListener.CheckBoxListener;
import com.tianyufighter.actionListener.SubmitListener;
import com.tianyufighter.function.UpdateTime;
import com.tianyufighter.model.ChoiceQuestion;
import com.tianyufighter.model.JudgeQuestion;
import com.tianyufighter.receive.GetAllQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerInterface implements ActionListener {
    // 客户端的输入流
    public DataInputStream in;
    // 客户端的输出流
    public DataOutputStream out;
    // 创建窗口的对象
    public JFrame frame = null;
    // 存放题型的数组
    public List<ChoiceQuestion> danxuan = null;
    public List<ChoiceQuestion> duoxuan = null;
    public List<JudgeQuestion> panduan = null;
    // 使每个题只有一个选项
    private ButtonGroup[] buttonGroup = new ButtonGroup[1000];
    // 每个选项的按钮
    public List<JRadioButton> button = new ArrayList<JRadioButton>();
    // 每个题的问题
    private List<JLabel> label = new ArrayList<JLabel>();
    // 判断的按钮
    public List<JCheckBox> checkBox = new ArrayList<JCheckBox>();
    // 存放用户的选择题记录
    public List<Integer> choiceRecord = new ArrayList<Integer>();
    // 存放用户的判断题记录
    public List<Integer> judgeRecord = new ArrayList<Integer>();
    // 单选题的个数
    public int danxuanNum = 0;
	// 记录标题的坐标
	public int titlex = 0;
	public int titley = 0;
	// 记录题的数目
	private int ti = 0;
	// 顶部面板
    public JPanel topPanel = null;
	// 存放试题的面板
    public JPanel panel = null;
    // 底部面板
    public JPanel bottomPanel = null;
    // 提交结果按钮
	public JButton result = new JButton("提交");
	// 放试卷的滚动条面板
    public JScrollPane scrollPane = null;
    // 菜单，导入试卷
    private Menu fileMenu = new Menu("File");
    private MenuItem fileOpen = new MenuItem("Open");
    private MenuItem fileExit = new MenuItem("Exit");
    private MenuBar menu = null;

    // 设置卷子的标题
    private JLabel label0 = new JLabel();
    // 设置总分的标签
    private JLabel label1 = new JLabel();
    // 设置显示倒计时的标签
    public JLabel time = new JLabel();;
    // 标记用户是不是第一次登录
    public boolean isFirst = true;

    public AnswerInterface() {
    }

    public AnswerInterface(JFrame frame) {
        this.frame = frame;
        initialize();
    }

    /**
     * 画窗口并添加组件
     */
    public void initialize() {
        frame = new JFrame();
        // 将菜单条加入到frame
        frame.setMenuBar(menu);
        // 设置窗口的初始化大小
        frame.setSize(950, 850);

        // 放试卷题目，倒计时的面板
        topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("D:\\8.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, panel.getWidth(), panel.getHeight(), icon.getImageObserver());
            }
        };;
        // 设置布局为BoxLayout
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        // 设置显示倒计时的标签
        topPanel.add(time);
        // 填充空白的部分
        topPanel.add(Box.createHorizontalGlue());
        // 设置显示试卷题目的标签
        topPanel.add(label0);
        // 填充空白的部分
        topPanel.add(Box.createHorizontalGlue());
        // 设置显示考试总时长的标签
        topPanel.add(label1);
        // 增加一定的宽度
        topPanel.add(Box.createHorizontalStrut(20));




        // 放置试卷的面板
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("D:\\7.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, panel.getWidth(), panel.getHeight(), icon.getImageObserver());
            }
        };
        // 设置放试卷的面板的布局
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        // 将panel设置为透明
        panel.setOpaque(false);
        panel.setBackground(null);
        // 将给panel添加滚动条
        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth()-15, frame.getHeight()-15));
        scrollPane.setBackground(null);



        // 存放提交按钮的标签
        bottomPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("D:\\7.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, panel.getWidth(), panel.getHeight(), icon.getImageObserver());
            }
        };
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));


        // 向页面添加试题
        loadQuestion();
        // 初始化存储做题状态的集合
        initialRecord();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置frame的布局
        frame.setLayout(new BorderLayout(0, 0));
        // 将含有滚动条的面板添加到frame上
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        // 设置窗口的标题
        frame.setTitle("学生考试系统");
        // 设置窗体相对于另一个组件居中，参数null表示窗体相对于屏幕的中央位置
        frame.setLocationRelativeTo(null);
        // 将窗口设置为可见
        frame.setVisible(true);

        // 恢复用户的做题记录
        restoreRecord();
    }
    /**
     * 把试题加载到页面上
     */
    public void loadQuestion() {
        // 创建监听事件，为选项的按钮和选项的checkBox添加监听事件
        ButtonListener buttonListener = new ButtonListener(this);
        CheckBoxListener checkBoxListener = new CheckBoxListener(this);
        // 设置倒计时标签的内容
        time.setFont(new Font("宋体", Font.BOLD, 18));
        time.setForeground(Color.BLUE);
        time.setText("距离考试结束: 00时00分00秒");
        // 设置标题
        label0.setFont(new Font("宋体", Font.BOLD, 22)); // 1
        label0.setText("面向对象程序设计技术（Java）单元测试试卷");

        // 考试总时长
        label1.setFont(new Font("宋体", Font.BOLD, 14)); // 1
        label1.setText("考试总时长: 100分钟");
        // 更新panel面板上的内容
        panel.updateUI();

        System.out.println("--------------------------------");
        System.out.println("单选题个数: " + danxuan.size());
        System.out.println("多选题个数: " + duoxuan.size());
        System.out.println("判断题个数: " + panduan.size());
        System.out.println("--------------------------------");
        
        
        label.add(null);
        button.add(null);
        checkBox.add(null);
        // 临时存放选项的按钮
        JRadioButton tempButton = null;
        // 临时存放题目的标签
        JLabel tempLabel = null;
        // 临时存放判断题选项的按钮
        JCheckBox tempCheckBox = null;
        
        // 选项的个数
        int xuan = 0;
        // 创建单选题部分并赋值
        int pan = 0; // 判断题的个数
        panel.add(Box.createVerticalStrut(20));
        JLabel ti1 = new JLabel("一、单选题（共20题，每题2分，共40分）");
        ti1.setFont(new Font("宋体", Font.BOLD, 22)); // 1
        panel.add(ti1);
        panel.add(Box.createVerticalStrut(40));
        // 设置单项选择题的问题标签和选项按钮
        for(int i = 0; i < 4 * danxuan.size(); i++) {
            if(i % 4 == 0) {
                ++ti;
                tempLabel = new JLabel();
                tempLabel.setFont(new Font("宋体", Font.BOLD, 20));  // 1
                panel.add(tempLabel);
                panel.add(Box.createVerticalStrut(20));
                label.add(tempLabel);
            }
            ++xuan;
            tempButton = new JRadioButton();
            tempButton.setFont(new Font("宋体", Font.BOLD, 20)); // 1
            // 设置按钮为透明
            tempButton.setContentAreaFilled(false);
            if(buttonGroup[ti] == null) {
                buttonGroup[ti] = new ButtonGroup();
            }
            // 为选项按钮添加监听事件
            tempButton.addActionListener(buttonListener);
            buttonGroup[ti].add(tempButton);
            panel.add(tempButton);
            // 将临时存储选项的对象添加到button集合中去
            button.add(tempButton);
            panel.add(Box.createVerticalStrut(20));

        }
        danxuanNum = xuan;
        // 在面板上添加单项选择题的问题
        for(int i = 1; i <= ti; i++) {
            label.get(i).setText(danxuan.get(i-1).getQuestion());
        }

        // 以下四个变量分别记录单项选择题中的A,B,C,D选项分别是第几个
        int aindex = 0, bindex = 0, cindex = 0, dindex = 0;
        // 在面板上把每个题的A,B,C,D的四个选项添加到面板上
        for(int i = 1; i <= xuan; i++) {
            if(i % 4 == 1) {
                aindex++;
                button.get(i).setText(danxuan.get(aindex-1).getA());
            } else if(i % 4 == 2) {
                bindex++;
                button.get(i).setText(danxuan.get(bindex-1).getB());
            } else if(i % 4 == 3) {
                cindex++;
                button.get(i).setText(danxuan.get(cindex-1).getC());
            } else {
                dindex++;
                button.get(i).setText(danxuan.get(dindex-1).getD());
            }
        }
        // 将其变量置零
        aindex = 0;
        bindex = 0;
        cindex = 0;
        dindex = 0;



        panel.add(Box.createVerticalStrut(20));
        JLabel ti2 = new JLabel("二、多选题（共20题，每题2分，共40分）");
        ti2.setFont(new Font("宋体", Font.BOLD, 22)); //
        panel.add(ti2);
        panel.add(Box.createVerticalStrut(40));
        // 创建多选题部分并赋值
        for(int i = 0; i < 4 * duoxuan.size(); i++) {
            if(i % 4 == 0) {
                ++ti;
                tempLabel = new JLabel();
                tempLabel.setFont(new Font("宋体", Font.BOLD, 20)); // 1
                panel.add(tempLabel);
                label.add(tempLabel);
                panel.add(Box.createVerticalStrut(20));
            }
            ++xuan;
            tempButton = new JRadioButton();
            tempButton.setFont(new Font("宋体", Font.BOLD, 20)); // 1
            // 设置按钮为透明
            tempButton.setContentAreaFilled(false);
            panel.add(tempButton);
            // 为选项按钮添加监听事件
            tempButton.addActionListener(buttonListener);
            // 将临时存储的选项对象添加到button集合中去
            button.add(tempButton);
            panel.add(Box.createVerticalStrut(20));
        }
        // 把多项选择题的题目画在面板上
        for(int i = danxuan.size() + 1; i <= ti; i++) {
            label.get(i).setText(duoxuan.get(i-(danxuan.size() + 1)).getQuestion());
        }


        // 在面板上把每个题的A,B,C,D的四个选项添加到面板上
        for(int i = 4 * danxuan.size() + 1; i <= xuan; i++) {
            if(i % 4 == 1) {
                aindex++;
                button.get(i).setText(duoxuan.get(aindex-1).getA());
            } else if(i % 4 == 2) {
                bindex++;
                button.get(i).setText(duoxuan.get(bindex-1).getB());
            } else if(i % 4 == 3) {
                cindex++;
                button.get(i).setText(duoxuan.get(cindex-1).getC());
            } else {
                dindex++;
                button.get(i).setText(duoxuan.get(dindex-1).getD());
            }
        }
        // 将其变量置零
        aindex = 0;
        bindex = 0;
        cindex = 0;
        dindex = 0;

        panel.add(Box.createVerticalStrut(20));
        JLabel ti3 = new JLabel("三、判断题（共10题，每题2分，共20分）");
        ti3.setFont(new Font("宋体", Font.BOLD, 22)); // 1
        panel.add(ti3);
        panel.add(Box.createVerticalStrut(40));
        // 创建判断题部分并赋值
        for(int i = 0; i < panduan.size() * 2; i++) {
            if(i % 2 == 0) {
                ++ti;
                tempLabel = new JLabel();
                tempLabel.setFont(new Font("宋体", Font.BOLD, 20)); // 1
                panel.add(tempLabel);
                label.add(tempLabel);
                panel.add(Box.createVerticalStrut(20));
            }
            ++pan;
            tempCheckBox = new JCheckBox();
            tempCheckBox.setFont(new Font("宋体", Font.BOLD, 20)); // 1
            // 将checkBox设置为透明
            tempCheckBox.setContentAreaFilled(false);
            panel.add(tempCheckBox);
            // 为checkBox添加监听事件
            tempCheckBox.addActionListener(checkBoxListener);
            // 将临时存储选项对象加入到checkBox集合中去
            checkBox.add(tempCheckBox);
            panel.add(Box.createVerticalStrut(20));
            if(buttonGroup[ti] == null) {
                buttonGroup[ti] = new ButtonGroup();
            }
            buttonGroup[ti].add(tempCheckBox);
        }
        for(int i = duoxuan.size() + danxuan.size() + 1; i <= ti; i++) {
            label.get(i).setText(panduan.get(i-(duoxuan.size() + danxuan.size() + 1)).getQuestion());
        }

        // 在面板上把每个题的A,B,C,D的四个选项添加到面板上
        for(int i = 1; i <= pan; i++) {
            if(i % 2 == 1) {
                aindex++;
                checkBox.get(i).setText(panduan.get(aindex-1).getA());
            } else {
                bindex++;
                checkBox.get(i).setText(panduan.get(bindex-1).getB());
            }
        }
        // 将其变量置零
        aindex = 0;
        bindex = 0;
        cindex = 0;
        dindex = 0;

        panel.add(Box.createVerticalStrut(20));
        result.setFont(new Font("宋体", Font.BOLD, 22));
        // 将提交按钮加入到页面底端的面板上
        if(bottomPanel == null) {
            System.out.println("bottomPanel为空");
        } else {
            bottomPanel.add(result);
            // 为按钮添加监听事件
            SubmitListener submitListener = new SubmitListener(this);
            result.addActionListener(submitListener);
        }
    }

    // 测试
    // 获取点击菜单的事件，并获取到文件的路径
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fileExit)
            System.exit(0);
        else if(e.getSource() == fileOpen) {
            FileDialog fd = new FileDialog(frame, "Open File", FileDialog.LOAD);
            fd.setVisible(true);
            if(fd.getFile() != null) {
                File file = new File(fd.getDirectory() + fd.getFile());
                if(file.exists()) {
                    int num = readFile(file.toString());
                    System.out.println("文件读取成功");
                    loadQuestion();
                }
                else
                    System.out.println("文件是无效的！！！！");
            }
            fd.dispose();
        }
    }
    public int readFile(String file) {
        GetAllQuestion getAllQuestion = new GetAllQuestion();
        getAllQuestion.getQuestion(file);
        danxuan = getAllQuestion.danxuan;
        duoxuan = getAllQuestion.duoxuan;
        panduan = getAllQuestion.panduan;
        return danxuan.size() + duoxuan.size() + panduan.size();
    }

    /**
     * 初始化存储做题记录的list集合
     */
    public void initialRecord() {
        if(isFirst) {
            for(int i = 0; i < danxuan.size() * 4 + duoxuan.size() * 4 + 1; i++) {
                choiceRecord.add(0);
            }
            for(int i = 0; i < panduan.size() * 2 + 1; i++) {
                judgeRecord.add(0);
            }
        }
    }

    /**
     * 如果用户做过题，就将用户的记录恢复
     * 如果没有就不恢复
     */
    public void restoreRecord() {
        if(choiceRecord.size() != 0 && judgeRecord.size() != 0) {
            for(int i = 0; i < choiceRecord.size(); i++) {
                if(choiceRecord.get(i) == 1) {
                    button.get(i).setSelected(true);
                }
            }
            for(int i = 0; i < judgeRecord.size(); i++) {
                if(judgeRecord.get(i) == 1) {
                    checkBox.get(i).setSelected(true);
                }
            }
        }
    }
    public static void main(String[] args) {
        AnswerInterface answerInterface = new AnswerInterface(null);
    }

}
