//package com.tianyufighter.temporary;
//
//import com.tianyufighter.draw.ProgressWindow;
//import com.tianyufighter.function.CountDown;
//import com.tianyufighter.model.ChoiceQuestion;
//import com.tianyufighter.model.JudgeQuestion;
//import com.tianyufighter.receive.GetAllQuestion;
//
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.FileDialog;
//import java.awt.Font;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.MenuItem;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//
//import javax.swing.ButtonGroup;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JScrollPane;
//import javax.swing.WindowConstants;
//
//
//
//public class DrawPaper implements ActionListener {
//
//	public JFrame frame;
//	private ButtonGroup[] buttonGroup = new ButtonGroup[1000]; // 使每个题只有一个选项
//	private JRadioButton[] button = new JRadioButton[1000]; // 每个选项的按钮
//	private JLabel[] label = new JLabel[1000]; // 每个题的问题
//	private JCheckBox[] checkBox = new JCheckBox[1000]; // 判断的按钮
//	private int[] ans1 = new int[1000]; // 选择题答案
//	private int[] ans2 = new int[1000]; // 判断题答案
//	private int score = 0; // 分数
//	private int ti = 0;
//	private boolean flag = true;
//	// 菜单，导入试卷
//	private Menu fileMenu = new Menu("File");
//	private MenuItem fileOpen = new MenuItem("Open");
//	private MenuItem fileExit = new MenuItem("Exit");
//
//
//	private JPanel jp = null ;
//	// 倒计时的对象
//	public CountDown countDown = null;
//	// 提交结果按钮
//	public JButton result = new JButton("提交");
//	private ProgressWindow progressWindow = null;
////	private ProgressWindow progressWindow = new ProgressWindow(null, 100);
//	// 临时变量
//	private int lin = 0;
//	boolean biao = false;
//	// 得到单选题，多选题，判断题的题目和答案
//	ChoiceQuestion[] danxuan = null;
//	ChoiceQuestion[] duoxuan = null;
//	JudgeQuestion[] panduan = null;
//	// 记录标题的坐标
//	public int titlex = 0;
//	public int titley = 0;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DrawPaper window = new DrawPaper();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the application.
//	 */
//	public DrawPaper() {
//		fileMenu.add(fileOpen); fileOpen.addActionListener(this);
//		fileMenu.addSeparator();
//		fileMenu.add(fileExit); fileExit.addActionListener(this);
//		MenuBar menu = new MenuBar();
//		menu.add(fileMenu);
//		menu.setFont(new Font("宋体", Font.BOLD, 16));
//		initialize(menu);
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize(MenuBar menu) {
//		// 设置窗口部分
//				frame = new JFrame();
//				frame.setMenuBar(menu);
//				// 设置窗体的大小
//				frame.setBounds(270, 10, 950, 850);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.setTitle("面向对象程序设计技术（Java）单元测试试卷");
//				frame.getContentPane().setLayout(null);
//				jp = new JPanel();
//				jp.setPreferredSize(new Dimension(1000, 11000));
//				JScrollPane sp = new JScrollPane(jp);
//				jp.setLayout(null);
//				sp.setBounds(0, 0, 935, 790);
//				frame.getContentPane().add(sp);
//				frame.setVisible(true);
//				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//
//			}
//	// 获取点击菜单的事件，并获取到文件的路径
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		// TODO Auto-generated method stub
//				if(e.getSource() == fileExit)
//					System.exit(0);
//				else if(e.getSource() == fileOpen) {
//					FileDialog fd = new FileDialog(frame, "Open File", FileDialog.LOAD);
//					fd.setVisible(true);
//					if(fd.getFile() != null) {
//						File file = new File(fd.getDirectory() + fd.getFile());
//						if(file.exists()) {
//							int num = readFile(file.toString());
//							frame.setVisible(false);
//							// 当用户指定了文件后创建progressWindow类
//							progressWindow = new ProgressWindow(num);
//							progressWindow.execute();
//							// 在新的线程中运行readFile方法
//							new Thread() {
//								@Override
//								public void run() {
//									writeFile();
//								}
//							}.start();
//						}
//						else
//							System.out.println("文件是无效的！！！！");
//					}
//					fd.dispose();
//				}
//	}
//	public int readFile(String file) {
//		GetAllQuestion getAllQuestion = new GetAllQuestion();
//		getAllQuestion.getQuestion(file);
//		danxuan = getAllQuestion.danxuan;
//		duoxuan = getAllQuestion.duoxuan;
//		panduan = getAllQuestion.panduan;
//		return danxuan.length + duoxuan.length + panduan.length;
//	}
//	public void writeFile() {
//		// 设置标题
//		JLabel label0 = new JLabel();
//		label0.setBounds(220, 30, 800, 30);
//		label0.setFont(new Font("宋体", Font.BOLD, 25)); // 1
//		label0.setText("面向对象程序设计技术（Java）单元测试试卷");
//		// 记录标题的坐标
//		titlex = 220;
//		titley = 30;
//
//		// 设置标题
//		JLabel label1 = new JLabel();
//		label1.setBounds(750, 30, 200, 30);
//		label1.setFont(new Font("宋体", Font.BOLD, 14)); // 1
//		label1.setText("考试总时长: 100分钟");
//
//		jp.add(label0);
//		jp.add(label1);
//		jp.updateUI();
////		// 得到单选题，多选题，判断题的题目和答案
////		ChoiceQuestion[] danxuan = null;
////        ChoiceQuestion[] duoxuan = null;
////        JudgeQuestion[] panduan = null;
////        GetAllQuestion getAllQuestion = new GetAllQuestion();
////        getAllQuestion.getQuestion(file);
////        danxuan = getAllQuestion.danxuan;
////        duoxuan = getAllQuestion.duoxuan;
////        panduan = getAllQuestion.panduan;
//		System.out.println("单选题个数: " + danxuan.length);
//		System.out.println("多选题个数: " + duoxuan.length);
//		System.out.println("判断题个数: " + panduan.length);
//		System.out.println("--------------------------------");
//		// 选项的个数
//		int xuan = 0;
//		// 创建单选题部分并赋值
////		int ti = 0; // 题目的个数
//		int pan = 0; // 判断题的个数
//		int x = 100, y = 100;
//		JLabel ti1 = new JLabel("一、单选题（共20题，每题2分，共40分）");
//		ti1.setFont(new Font("宋体", Font.BOLD, 22)); // 1
//		ti1.setBounds(x, y, 800, 20);
//		jp.add(ti1);
//		y += 30;
//		// 设置单项选择题的问题标签和选项按钮
//		for(int i = 0; i < 4 * danxuan.length; i++) {
//			if(i % 4 == 0) {
//				label[++ti] = new JLabel();
//				label[ti].setBounds(x, y, 1000, 70);
//				label[ti].setFont(new Font("宋体", Font.BOLD, 20));  // 1
//				y += 70;
//				jp.add(label[ti]);
//			}
//			button[++xuan] = new JRadioButton();
//			button[xuan].setBounds(x, y, 1000, 20);
//			button[xuan].setFont(new Font("宋体", Font.BOLD, 20)); // 1
//			y += 35;
//			if(buttonGroup[ti] == null) {
//				buttonGroup[ti] = new ButtonGroup();
//			}
//			buttonGroup[ti].add(button[xuan]);
//			jp.add(button[xuan]);
//
//		}
//		// 在面板上添加单项选择题的问题
//		for(int i = 1; i <= ti; i++) {
//			label[i].setText(danxuan[i-1].getQuestion());
//		}
//
//		// 以下四个变量分别记录单项选择题中的A,B,C,D选项分别是第几个
//		int aindex = 0, bindex = 0, cindex = 0, dindex = 0;
//		// 在面板上把每个题的A,B,C,D的四个选项添加到面板上
//		for(int i = 1; i <= xuan; i++) {
////			button[i].setText("张洪涛");
//			if(i % 4 == 1) {
//				aindex++;
//				button[i].setText(danxuan[aindex-1].getA());
//				// 当执行到此处时表示当前已经加载的题目数量加1，并输出到控制台
//				System.out.println("我读到了1----" + (++lin));
//				progressWindow.currentNum++;
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			} else if(i % 4 == 2) {
//				bindex++;
//				button[i].setText(danxuan[bindex-1].getB());
//			} else if(i % 4 == 3) {
//				cindex++;
//				button[i].setText(danxuan[cindex-1].getC());
//			} else {
//				dindex++;
//				button[i].setText(danxuan[dindex-1].getD());
//			}
//		}
//		// 将其变量置零
//		aindex = 0;
//		bindex = 0;
//		cindex = 0;
//		dindex = 0;
//
//		// 将每个题的答案存入答案数组
//		for(int i = 0; i < danxuan.length; i++) {
//			String[] str = danxuan[i].getAnswer();
//			int offset = 0;
//			for(int j = 0; j < str.length; j++) {
//				if(str[j].equals("A")) {
//					offset = 1;
//				} else if(str[j].equals("B")) {
//					offset = 2;
//				} else if(str[j].equals("C")) {
//					offset = 3;
//				} else {
//					offset = 4;
//				}
//			}
//			ans1[i * 4 + offset] = 1;
//		}
//
//		y += 60;
//
//
//		JLabel ti2 = new JLabel("二、多选题（共20题，每题2分，共40分）");
//		ti2.setFont(new Font("宋体", Font.BOLD, 22)); // 1
//		ti2.setBounds(x, y, 800, 20);
//		jp.add(ti2);
//		y += 30;
//		// 创建多选题部分并赋值
//		for(int i = 0; i < 4 * duoxuan.length; i++) {
//			if(i % 4 == 0) {
//				label[++ti] = new JLabel();
//				label[ti].setBounds(x, y, 1000, 80);
//				label[ti].setFont(new Font("宋体", Font.BOLD, 20)); // 1
//				y += 80;
//				jp.add(label[ti]);
//			}
//			button[++xuan] = new JRadioButton();
//			button[xuan].setBounds(x, y, 1000, 20);
//			button[xuan].setFont(new Font("宋体", Font.BOLD, 20)); // 1
//			y += 40;
//			jp.add(button[xuan]);
//		}
//		// 把多项选择题的题目画在面板上
//		for(int i = danxuan.length + 1; i <= ti; i++) {
//			label[i].setText(duoxuan[i-(danxuan.length + 1)].getQuestion());
//		}
//
//
//		// 在面板上把每个题的A,B,C,D的四个选项添加到面板上
//		for(int i = 4 * danxuan.length + 1; i <= xuan; i++) {
////			button[i].setText("张洪涛");
//			if(i % 4 == 1) {
//				aindex++;
//				button[i].setText(duoxuan[aindex-1].getA());
//				// 新添加
//				progressWindow.currentNum++;
//				System.out.println("我读到了2----" + (++lin));
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			} else if(i % 4 == 2) {
//				bindex++;
//				button[i].setText(duoxuan[bindex-1].getB());
//			} else if(i % 4 == 3) {
//				cindex++;
//				button[i].setText(duoxuan[cindex-1].getC());
//			} else {
//				dindex++;
//				button[i].setText(duoxuan[dindex-1].getD());
//			}
//		}
//		// 将其变量置零
//		aindex = 0;
//		bindex = 0;
//		cindex = 0;
//		dindex = 0;
//
//		// 将每个题的答案存入答案数组
//		for(int i = 0; i < duoxuan.length; i++) {
//			String[] str = duoxuan[i].getAnswer();
//			int offset = 0;
//			for(int j = 0; j < str.length; j++) {
//				if(str[j].equals("A")) {
//					offset = 1;
//				} else if(str[j].equals("B")) {
//					offset = 2;
//				} else if(str[j].equals("C")) {
//					offset = 3;
//				} else {
//					offset = 4;
//				}
//				ans1[danxuan.length * 4 + i * 4 + offset] = 1;
//			}
//		}
//		y += 60;
//		JLabel ti3 = new JLabel("三、判断题（共10题，每题2分，共20分）");
//		ti3.setBounds(x, y, 800, 20);
//		ti3.setFont(new Font("宋体", Font.BOLD, 22)); // 1
//		jp.add(ti3);
//		y += 30;
//		// 创建判断题部分并赋值
//		for(int i = 0; i < panduan.length * 2; i++) {
//			if(i % 2 == 0) {
//				label[++ti] = new JLabel();
//				label[ti].setBounds(x, y, 1000, 60);
//				label[ti].setFont(new Font("宋体", Font.BOLD, 20)); // 1
//				y += 60;
//				jp.add(label[ti]);
//			}
//			checkBox[++pan] = new JCheckBox();
//			checkBox[pan].setBounds(x, y, 1000, 20);
//			checkBox[pan].setFont(new Font("宋体", Font.BOLD, 20)); // 1
//			y += 32;
//			jp.add(checkBox[pan]);
//			if(buttonGroup[ti] == null) {
//				buttonGroup[ti] = new ButtonGroup();
//			}
//			buttonGroup[ti].add(checkBox[pan]);
//		}
//		for(int i = duoxuan.length + danxuan.length + 1; i <= ti; i++) {
//			label[i].setText(panduan[i-(duoxuan.length + danxuan.length + 1)].getQuestion());
//		}
//
//		// 在面板上把每个题的A,B,C,D的四个选项添加到面板上
//		for(int i = 1; i <= pan; i++) {
//			if(i % 2 == 1) {
//				aindex++;
//				checkBox[i].setText(panduan[aindex-1].getA());
//				progressWindow.currentNum++;
//				System.out.println("我读到了3---" + (++lin));
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			} else {
//				bindex++;
//				checkBox[i].setText(panduan[bindex-1].getB());
//			}
//		}
//		// 将其变量置零
//		aindex = 0;
//		bindex = 0;
//		cindex = 0;
//		dindex = 0;
//
//		// 将每个题的答案存入答案数组
//		for(int i = 0; i < panduan.length; i++) {
//			String str = panduan[i].getAnswer();
//			int offset = 0;
//			if(str.equals("正确")) {
//				offset = 1;
//			} else {
//				offset = 2;
//			}
//			ans2[i * 2 + offset] = 1;
//		}
//		y += 60;
//
//		// 开始倒计时
//		countDown = new CountDown(jp, 100 * 60, this);
////		countDown.execute(titlex + 20, titley + 2);
//
//		result.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				achiveScore(titlex, titley);
//			}
//		});
//		result.setBounds(x+80, y, 500, 50);
//		result.setFont(new Font("宋体", Font.BOLD, 22));
//		jp.add(result);
//	}
//
//	/**
//	 * 当flag为true时计算相应的分数显示在提交按钮中，反之则重置按钮
//	 * @param titlex: 该试卷标题的横坐标坐标
//	 * @param titley: 该试卷标题的纵坐标
//	 */
//	public void  achiveScore(int titlex, int titley) {
//		if(flag) {
////			System.out.println("开始算分， 其实分数score = " + score);
//			countDown.stop();
//			// 算单选题的分
//			for(int i = 1; i <= danxuan.length * 4; i++) {
//				if(button[i].isSelected() && ans1[i] == 1) {
//					score += 2;
//				}
//			}
//			// 算多选题的分
//			for(int i = danxuan.length * 4 + 1; i <= danxuan.length * 4 + duoxuan.length * 4 - 3; i += 4) {
//				boolean biao = true;
//				for(int j = i; j <= i + 3 && j <= danxuan.length * 4 + duoxuan.length * 4; j++) {
//					if(button[j].isSelected() == true && ans1[j] == 0) {
//						biao = false;
//						break;
//					}
//					if(button[j].isSelected() == false && ans1[j] == 1) {
//						biao = false;
//						break;
//					}
//				}
//				if(biao) {
//					score += 2;
//				}
//			}
//			// 算判断题的分
//			for(int i = 1; i <= panduan.length * 2; i++) {
//				if(checkBox[i].isSelected() && ans2[i] == 1) {
//					score += 2;
//				}
//			}
//			if(score >= 60)
//			result.setText("恭喜你，你的分数是:" + score + "  点击是否从做");
//			else
//			result.setText("很遗憾， 你的分数是:" + score + "   点击是否重做");
//			score = 0;
//			flag = false;
//		}
//		else {
//			score = 0;
//			countDown.reset();
//			countDown = new CountDown(jp, 100 * 60, this);
//			countDown.execute(titlex + 20, titley + 2);
//			result.setText("提交");
//			// 将按钮的选中状态变成未选中状态
//			for(int i = danxuan.length * 4 + 1; i <= danxuan.length * 4 + duoxuan.length; i++) {
//				if(button[i].isSelected())
//					button[i].setSelected(false);
//			}
//			for(int i = 1; i <= danxuan.length; i++) {
//				buttonGroup[i].clearSelection();
//			}
//			for(int i = danxuan.length + duoxuan.length + 1; i <= ti; i++)
//			{
//				buttonGroup[i].clearSelection();
//			}
//
//			flag = true;
//		}
//	}
//}
