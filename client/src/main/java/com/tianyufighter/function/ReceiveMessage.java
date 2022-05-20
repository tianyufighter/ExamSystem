package com.tianyufighter.function;

import com.tianyufighter.draw.*;
import com.tianyufighter.model.ChoiceQuestion;
import com.tianyufighter.model.JudgeQuestion;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiveMessage extends Thread {
    // 客户端的输入流
    private DataInputStream in;
    // 客户端的输出流
    private DataOutputStream out;
    // 将获取到的消息按指定数组分割存储在数组中
    private String[] res;
    // 存储要处理的类型
    private String type;
    // 登录界面的对象
    public static Login login = null;
    // 用于客户端和服务端之间传题
    private ObjectInputStream tempIn = null;
    private ObjectOutputStream tempOut = null;
    // 客户端含有试题的界面的对象
    private AnswerInterface answerInterface = new AnswerInterface();
    // 显示进度条窗口的对象
    private ProgressWindow progressWindow = null;

    public ReceiveMessage(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
        answerInterface.in = in;
        answerInterface.out = out;
    }

    @Override
    public void run() {
        String info = "";
        // 如果当读取的字符串的为QUIT则结束通信
        while(!info.equalsIgnoreCase("QUIT")) {
            // 每隔100ms接收一次消息
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                info = in.readUTF();
                System.out.println("信息:" + info);
                // 将从客户端得到的信息按|分割得到字符串，并存储要处理那种业务
                res = info.split("\\|");
                type = res[0];
//                System.out.println("类型: " + type);
                execute();
            } catch (IOException e) {

            }
        }
    }


    /**
     * 判断给类型应该执行哪个方法
     * type: 表示客户端想要请求事件的类型
     */
    public void execute() {
        // 如果返回的是login类型
        if(type.equals("login")) {
            // 服务器传回的结果
            String str = res[1];
            handlerLogin(str);
            System.out.println("客户端: 我收到了服务端传回的登录信息");
        } else if(type.equals("testPaper")) {
            receiptQuestion();
        } else if(type.equals("countdown")) {
            // 服务器传回的结果
            String str = res[1];
            freshTime(str);
            try {
                if(!answerInterface.frame.isVisible()) {
                    answerInterface.frame.setVisible(true);
                    // 关闭提示考生考试时间没有到的窗口
                    progressWindow.popupMenu.frame.dispose();
                }
            } catch (Exception e) {

            }

//            System.out.println("我收到了你的时间");
        } else if(type.equals("record")) {
            // 用于客户端和服务端之间传题
            try {
                tempIn = new ObjectInputStream(in);
                tempOut = new ObjectOutputStream(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 接收服务端传过来的用户选择题的做题记录
            List<Integer> choiceRecord = null;
            try {
                choiceRecord = (List<Integer>) tempIn.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 接收服务端传过来的用户判断题的做题记录
            List<Integer> judgeRecord = null;
            try {
                judgeRecord = (List<Integer>) tempIn.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            answerInterface.choiceRecord = choiceRecord;
            answerInterface.judgeRecord = judgeRecord;
            System.out.println(answerInterface.choiceRecord);
            System.out.println(answerInterface.judgeRecord);
            if(answerInterface.choiceRecord.size() != 0 && answerInterface.judgeRecord.size() != 0) {
                answerInterface.isFirst = false;
            }
            System.out.println("正在恢复用户做题数据");
            System.out.println("用户做题记录恢复完成");
        }
    }

    /**
     * 服务端返回登录请求信息后执行的操作
     * @param str 如果是true代表登录成功，进入下一个页面，否则没有登录成功给予提示
     */
    public void handlerLogin(String str) {
        if(str.equals("true")) {
            new Thread() {
                @Override
                public void run() {
                    ExamRule examRule = new ExamRule(in, out);
                }
            }.start();
            // 通过我们获取的登录界面对象，用dispose方法关闭它
            login.frame.dispose();
        } else if(str.equals("false")) {
            JOptionPane.showMessageDialog(null, "准考证号或密码错误，请重新登录", "Error",JOptionPane.ERROR_MESSAGE);
            // 清除准考证输入框的内容
            login.textName.setText("");
            // 清除密码输入框的内容
            login.password.setText("");
            System.out.println("用户名或者密码错误，请重新登录");
        } else {
            System.out.println("服务端正忙，请稍后");
        }
    }

    /**
     * 接收从服务器发来的题目
     */
    public void receiptQuestion() {
        // 用于客户端和服务端之间传题
        try {
            tempIn = new ObjectInputStream(in);
            tempOut = new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将从服务端受到的题存为对象
        answerInterface.danxuan = new ArrayList<ChoiceQuestion>();
        answerInterface.duoxuan = new ArrayList<ChoiceQuestion>();
        answerInterface.panduan = new ArrayList<JudgeQuestion>();
        // 单选题的个数
        int index1 = 0;
        // 多选题的个数
        int index2 = 0;
        // 判断题的个数
        int index3 = 0;
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        try {
            // 单选题的数量
            num1 = (int) tempIn.readObject();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        try {
            // 多选题的数量
            num2 = (int) tempIn.readObject();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        try {
            // 判断题的数量
            num3 = (int) tempIn.readObject();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        progressWindow = new ProgressWindow(num1 + num2 + num3, in, out, answerInterface);
        progressWindow.execute();
        while(true) {
            try {
                Object object = tempIn.readObject();
                if((object instanceof ChoiceQuestion) && (index1 < num1)) {
                    ++index1;
                    answerInterface.danxuan.add((ChoiceQuestion) object);
                    progressWindow.currentNum++;
                    progressWindow.label1.updateUI();
                } else if(object instanceof ChoiceQuestion) {
                    answerInterface.duoxuan.add((ChoiceQuestion) object);
                    progressWindow.currentNum++;
                    progressWindow.label1.updateUI();
                } else if(object instanceof JudgeQuestion) {
                    answerInterface.panduan.add((JudgeQuestion) object);
                    progressWindow.currentNum++;
                    progressWindow.label1.updateUI();
                } else if(object instanceof String) {
                    String str = (String) object;
                    if(str.equals("over")) {
                        System.out.println("接收到所有题目");
                    }
                    break;
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }

    /**
     * 刷新含试题页面的倒计时
     * @param str 存放在倒计时标签中文字
     */
    public void freshTime(String str) {
        answerInterface.time.setText(str);
    }

}
