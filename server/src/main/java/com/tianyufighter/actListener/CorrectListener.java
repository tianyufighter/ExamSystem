package com.tianyufighter.actListener;

import com.tianyufighter.dao.UserDao;
import com.tianyufighter.dao.impl.UserDaoImpl;
import com.tianyufighter.draw.HomePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleToIntFunction;

/**
 * 服务端界面改卷按钮的监听器
 */
public class CorrectListener implements ActionListener {
    private HomePage homePage;

    public CorrectListener(HomePage homePage) {
        this.homePage = homePage;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        perusal();
    }

    /**
     * 批阅试卷的方法
     */
    public void perusal() {
        for(int i = 0; i < homePage.user.size(); i++) {
            try {
                int sum = 0;
                // 算单选题的分数
                for (int j = 1; j <= homePage.danxuan.size() * 4; j++) {
                    if (HomePage.ans1.get(j) == 1 && homePage.user.get(i).choiceRecord.get(j) == 1) {
                        sum += 2;
                    }
                }
                // 算所选题的分数
                for (int j = homePage.danxuan.size() * 4 + 1; j <= homePage.danxuan.size() * 4 + homePage.duoxuan.size() * 4 - 3; j += 4) {
                    boolean biao = true;
                    for (int k = j; k <= j + 3 && k <= homePage.danxuan.size() * 4 + homePage.duoxuan.size() * 4; k++) {
                        if (homePage.user.get(i).choiceRecord.get(k) == 1 && HomePage.ans1.get(k) == 0) {
                            biao = false;
                            break;
                        }
                        if (homePage.user.get(i).choiceRecord.get(k) == 0 && HomePage.ans1.get(k) == 1) {
                            biao = false;
                            break;
                        }
                    }
                    if (biao) {
                        sum += 2;
                    }
                }
                // 算判断题的分数
                //            for(int j = 1; j <= homePage.panduan.size() * 2; j++) {
                //
                //                if(homePage.user.get(i).judgeRecord.get(j) == 1 && homePage.ans2.get(j) == 1) {
                //                    sum += 2;
                //                }
                //            }
                homePage.user.get(i).setScore(sum);
                //            break;
            } catch (Exception e) {
                homePage.user.get(i).setScore(0);
            }
        }
        System.out.println("批改完成");
        JOptionPane.showMessageDialog(homePage.frame, "所有学生的试卷已批改完成！！！", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    }
}
