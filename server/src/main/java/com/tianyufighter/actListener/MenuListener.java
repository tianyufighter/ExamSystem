package com.tianyufighter.actListener;

import com.tianyufighter.draw.HomePage;
import com.tianyufighter.receive.GetAllQuestion;
import com.tianyufighter.util.ModelDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 菜单的监听器
 */
public class MenuListener implements ActionListener {
    private HomePage homePage = null;
    public MenuListener() {
    }

    public MenuListener(HomePage homePage) {
        this.homePage = homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == homePage.fileExit)
            System.exit(0);
        else if(e.getSource() == homePage.fileOpen) {
            FileDialog fd = new FileDialog(homePage.frame, "Open File", FileDialog.LOAD);
            fd.setVisible(true);
            if(fd.getFile() != null) {
                File file = new File(fd.getDirectory() + fd.getFile());
                if(file.exists()) {
                    // 返回的总的题目数
                    int num = readFile(file.toString());
                    System.out.println("文件加载成功");
                    achieveAnswer();
                    System.out.println("试卷的答案存储完成");
                }
                else
                    System.out.println("文件是无效的！！！！");
            }
            fd.dispose();
        } else if(e.getSource() == homePage.examTime) {
            // 当点击设置考试时间时弹出模态框
            ModelDialog modelDialog = new ModelDialog(homePage.frame, "设置考试时间", true);
            modelDialog.showDialog();
            // 获取设定的考试时间
            String res = modelDialog.getRes();

            String longTime = modelDialog.getLongTime();
            // 将考试时间加入到homePage的变量中去
            homePage.testTime = res;
            // 将考试时长加入到homePage的变量中去
            homePage.testLongTime = longTime;
        }
    }
    /**
     * 读取文件中的内容，并将每个题封装成对象
     * @param file 文件的路径名
     * @return 总的题目数
     */
    public int readFile(String file) {
        GetAllQuestion getAllQuestion = new GetAllQuestion();
        getAllQuestion.getQuestion(file);
        homePage.danxuan = getAllQuestion.danxuan;
        homePage.duoxuan = getAllQuestion.duoxuan;
        homePage.panduan = getAllQuestion.panduan;
        return homePage.danxuan.size() + homePage.duoxuan.size() + homePage.panduan.size();
    }

    /**
     * 从文件中获取题目的答案，并将答案转换成数组以便改卷
     */
    public void achieveAnswer () {
        HomePage.ans1.add(0, 0);
        // 将每个单选题的答案存入答案数组
        for(int i = 0; i < homePage.danxuan.size(); i++) {
            String[] str = homePage.danxuan.get(i).getAnswer();
            int offset = 0;
            for(int j = 0; j < str.length; j++) {
                if(str[j].equals("A")) {
                    offset = 1;
                } else if(str[j].equals("B")) {
                    offset = 2;
                } else if(str[j].equals("C")) {
                    offset = 3;
                } else {
                    offset = 4;
                }
            }
            for(int j = 1; j <= 4; j++) {
                HomePage.ans1.add(i * 4 + 1, 0);
            }
            HomePage.ans1.set(i * 4 + offset, 1);
        }

        // 将每个多选题的答案存入答案数组
        for(int i = 0; i < homePage.duoxuan.size(); i++) {
            String[] str = homePage.duoxuan.get(i).getAnswer();
            int offset = 0;
            for(int j = 0; j < str.length; j++) {
                if(str[j].equals("A")) {
                    offset = 1;
                } else if(str[j].equals("B")) {
                    offset = 2;
                } else if(str[j].equals("C")) {
                    offset = 3;
                } else {
                    offset = 4;
                }
            }
            for(int j = 1; j <= 4; j++) {
                HomePage.ans1.add(homePage.danxuan.size() * 4 + i * 4 + j, 0);
            }
            HomePage.ans1.set(homePage.danxuan.size() * 4 + i * 4 + offset, 1);
        }
        HomePage.ans2.add(0, 0);
        //将每个判断题的答案存入答案数组
        for(int i = 0; i < homePage.panduan.size(); i++) {
            String str = homePage.panduan.get(i).getAnswer();
            int offset = 0;
            if(str.equals("正确")) {
                offset = 1;
            } else {
                offset = 2;
            }
            for (int j = 1; j <= 2; j++) {
                HomePage.ans2.add(i * 2 + j, 0);
            }
            HomePage.ans2.set(i * 2 + offset, 1);
        }
    }
}
