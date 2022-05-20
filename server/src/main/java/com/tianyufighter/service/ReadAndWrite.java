package com.tianyufighter.service;

import com.tianyufighter.dao.UserDao;
import com.tianyufighter.dao.impl.UserDaoImpl;
import com.tianyufighter.draw.HomePage;
import com.tianyufighter.model.ChoiceQuestion;
import com.tianyufighter.model.User;
import com.tianyufighter.util.CountDown;
import com.tianyufighter.util.StoreCurrentUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理业务的类
 */
public class ReadAndWrite {
    // 服务端的输入和输出流
    private DataInputStream in = null;
    private DataOutputStream out = null;
    // 服务端接收到的消息
    private String info = "";
    // 存储要处理的类型
    private String type;
    // 将客户端传递过来的信息分割成字符串数组
    private String[] res;
    // 操作数据库的对象
    private UserDao userDao = null;
    // 服务端的界面对象
    private HomePage homePage;
    // HandlerLogic对象
    private HandlerLogic handlerLogic;
    // 存储当前用户对象
    public static User nowUser = null;
    public ReadAndWrite() {
    }

    public ReadAndWrite(DataInputStream in, DataOutputStream out, String info, HomePage homePage, HandlerLogic handlerLogic) {
        this.in = in;
        this.out = out;
        this.info = info;
        this.userDao = new UserDaoImpl();
        this.homePage = homePage;
        this.handlerLogic = handlerLogic;
        init();
        execute();
    }

    /**
     * 将从客户端得到的信息按|分割得到字符串，并存储要处理那种业务
     */
    public void init() {
        System.out.println("客户端说: " + info);
        res = info.split("\\|");
        type = res[0];
    }

    /**
     * 判断给类型应该执行哪个方法
     * type: 表示客户端想要请求事件的类型
     */
    public void execute() {
        if(type.equals("login")) {
            if(judgeLogin()) {
                // 向客户端发送该用户做题的记录
                sendRecord();
                // 将当前的异常状态置为false
                handlerLogic.user.setException(false);
                try {
                    out.writeUTF("login|true");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("服务端说: 登录成功！！！");
            } else {
                try {
                    out.writeUTF("login|false");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("服务端说: 登录失败！！！");
            }
            System.out.println(handlerLogic.user);
        } else if(type.equals("testPaper")) {
            // 发送给客户端传回的类型
            try {
                out.writeUTF("testPaper");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectOutputStream tempOut = null;
            ObjectInputStream tempIn = null;
            try {
                // 将服务端的输入输出流暂时转为对象流
                tempOut = new ObjectOutputStream(out);
                tempIn = new ObjectInputStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            // 如果该对象不为空，表示此时服务端的界面已经开启
            if(homePage != null) {
                // 将各个题型的数量传给客户端，以便客户端容易区分是单选题，还是多选题
                try {
                    tempOut.writeObject(homePage.danxuan.size());
                    tempOut.writeObject(homePage.duoxuan.size());
                    tempOut.writeObject(homePage.panduan.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 传单选题的对象
                if(homePage.danxuan != null) {
                    for(int i = 0; i < homePage.danxuan.size(); i++) {
                        try {
                            tempOut.writeObject(homePage.danxuan.get(i));
                            try {
                                Thread.currentThread().sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 传多选题的对象
                if(homePage.duoxuan != null) {
                    for(int i = 0; i < homePage.duoxuan.size(); i++) {
                        try {
                            tempOut.writeObject(homePage.duoxuan.get(i));
                            try {
                                Thread.currentThread().sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 传判断题的对象
                if(homePage.panduan != null) {
                    for(int i = 0; i < homePage.panduan.size(); i++) {
                        try {
                            tempOut.writeObject(homePage.panduan.get(i));
                            try {
                                Thread.currentThread().sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 向客户端发送"over", 通知客户端题已经发送完成
                try {
                    tempOut.writeObject("over");
                    // 将倒计时中flag设置为true，即向客户端发送倒计时功能的信息
                    CountDown.flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("服务端说: 客户端我把题已经发给你了");
            }
        } else if(type.equals("stop")) {
            // 将服务端的倒计时类中的flag设置为false，即服务端不在向客户端发送倒计时时间
            CountDown.flag = false;
            // 将用户的提交状态设置已提交
            handlerLogic.user.setSubmit(true);
        } else if(type.equals("testOperation")) {
            ObjectOutputStream tempOut = null;
            ObjectInputStream tempIn = null;
            // 将服务端的输入输出流暂时转为对象流
            try {
                tempOut = new ObjectOutputStream(out);
                tempIn = new ObjectInputStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                List<Integer> choiceRecord = (List<Integer>) tempIn.readObject();
                handlerLogic.storeCurrentUser.user.choiceRecord = choiceRecord;
                System.out.println(handlerLogic.storeCurrentUser.user.choiceRecord);
                List<Integer> judgeRecord = (List<Integer>) tempIn.readObject();
                handlerLogic.storeCurrentUser.user.judgeRecord = judgeRecord;
                System.out.println(handlerLogic.storeCurrentUser.user.judgeRecord);
                System.out.println("做题记录做好了备份");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if(type.equals("correctAnswer")) {
            ObjectOutputStream tempOut = null;
            ObjectInputStream tempIn = null;
            // 将服务端的输入输出流暂时转为对象流
            try {
                tempOut = new ObjectOutputStream(out);
                tempIn = new ObjectInputStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 选择题答案
            List<Integer> ans1 = new ArrayList<Integer>();
            // 判断题答案
            List<Integer>  ans2 = new ArrayList<Integer>();
            try {
                ans1 = (List<Integer>) tempIn.readObject();
                ans2 = (List<Integer>) tempIn.readObject();
                HomePage.ans1 = ans1;
                homePage.ans2 = ans2;
                System.out.println(HomePage.ans1);
                System.out.println(HomePage.ans2);
                System.out.println("答案已成功存储到了服务端");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if(type.equals("disconnect")) {
            // 服务端与客户端断了连接，将用户的登录状态设置为false
            try {
                handlerLogic.user.setIslogin(false);
                // 将登录状态更新到数据库
                UserDao dao = new UserDaoImpl();
                int num = dao.update(handlerLogic.user);
                if(num != 0) {
                    System.out.println("用户登录状态记录到了数据库");
                }
                // 在线人数减1
                homePage.onlinePeople--;
            } catch (NullPointerException e) {
                System.out.println("客户端没有登录，与客户端的连接断开了");
            }

        }
    }
    /**
     * 验证登录信息是否正确
     */
    public boolean judgeLogin() {
       String examineeNumber = res[2];
       String password = res[4];
       // 查找成功，数据库中有该信息
       if(userDao.findUser(examineeNumber, password) != null) {
           //如果用户登录成功，将该用户对象的登录状态设置为true
           handlerLogic.user = findObject(examineeNumber, password);
           // 当前ReadAndWrite设置存储当前用户的变量，类型为static
           nowUser = handlerLogic.user;
           if(handlerLogic.user != null) {
               // 登录成功，将其user对象的登录状态设置为true
               handlerLogic.user.setIslogin(true);
               // 存储当前登录的用户的对象
               handlerLogic.storeCurrentUser = new StoreCurrentUser(handlerLogic.user);
               // 将登录状态更新到数据库
               UserDao dao = new UserDaoImpl();
               int num = dao.update(handlerLogic.user);
               if(num != 0) {
                   System.out.println("用户登录状态记录到了数据库");
               }
           }
           // 更新服务端界面的在线人数
           homePage.onlinePeople++;
           return true;
       }
       // 查找失败
       return false;
    }

    /**
     * 根据属性值查找User数组中的对象
     * @param examineeNumber
     * @param password
     * @return
     */
    public User findObject(String examineeNumber, String password) {
        for(int i = 0; i < homePage.user.size(); i++) {
            if(homePage.user.get(i).getExamineeNumber().equals(examineeNumber) && homePage.user.get(i).getPassword().equals(password)) {
                return homePage.user.get(i);
            }
        }
        return null;
    }
    /**
     *向客户端发送做题的情况
     */
    void sendRecord() {
        ObjectOutputStream tempOut = null;
        ObjectInputStream tempIn = null;
        // 告诉客户端发送消息的类型
        try {
            out.writeUTF("record");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 将服务端的输入输出流暂时转为对象流
            tempOut = new ObjectOutputStream(out);
            tempIn = new ObjectInputStream(in);
            // 将存储选择题的list集合传给服务端
            tempOut.writeObject(handlerLogic.user.choiceRecord);
            // 将存储判断题的list集合传给服务端
            tempOut.writeObject(handlerLogic.user.judgeRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("该用户的做题记录已经发送到了客户端");
    }
}
