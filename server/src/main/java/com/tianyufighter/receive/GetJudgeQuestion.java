package com.tianyufighter.receive;


import com.tianyufighter.model.JudgeQuestion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class GetJudgeQuestion {
    public static List<JudgeQuestion> getJudgeQuestion(String file){
        List<JudgeQuestion> questions = new ArrayList<JudgeQuestion>();
        int index = 0;
        boolean canDo = false;
        BufferedReader in = null;
        // 临时存放判断题的对象
        JudgeQuestion tempQuestion = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            String temp = "<html>";
            // 临时存储答案
            String tempAnswer = "";
            while((line = in.readLine()) != null) {
                if(line.contains("判断题")) {
                    canDo = true;
                    continue;
                }
                if(canDo) {
                    if(!(line.equals(""))) {
                        if(line.contains("<")) {
                            line.replace("<", "&lt;");
                        }
                        if(line.contains(">")) {
                            line.replace(">", "&gt;");
                        }
                        // 存当前题目
                        if(line.charAt(0) == 'A') {
                            int left=0x3f3f3f3f, right=0x3f3f3f3f;
                            if(temp.indexOf("(") != -1) {
                                left = min(left, temp.indexOf("("));
                            }
                            if(temp.indexOf("（") != -1) {
                                left = min(left,temp.indexOf("（"));
                            }

                            if(temp.indexOf(")") != -1) {
                                right = min(right, temp.indexOf(")"));
                            } else if(temp.indexOf("）") != -1) {
                                right = min(right,temp.indexOf("）"));
                            }
                            for(int i = left + 1; i < right; i++) {
                                if(temp.charAt(i) != ' ') {
                                    tempAnswer += temp.charAt(i);
                                    temp = temp.replace(temp.charAt(i), ' ');
                                }
                            }
                            // 存储答案
                            tempQuestion.setAnswer(tempAnswer);
                            tempAnswer = "";
                            // 新添加
                            temp += "</html>";
                            tempQuestion.setQuestion(temp);
                            temp = "<html>";
                        } else if(line.charAt(0) == 'B') {
                            // 新添加
                            temp += "</html>";
                            tempQuestion.setA(temp);
                            temp = "<html>";
                        } else if(line.charAt(0) >= '0' && line.charAt(0) <= '9') {
                            // 对于读每个题型的第一个题时这是不需要将tempQuestion加入对象
                            if(tempQuestion != null) {
                                // 新添加
                                temp += "</html>";
                                tempQuestion.setB(temp);
                                temp = "<html>";
                                questions.add(tempQuestion);
                            }
                            tempQuestion = new JudgeQuestion();
                        }
                    }
                    if(!(line.equals(""))) {
                        temp += line + "<br/>";
                    }
                }

            }
            tempQuestion.setB(temp);
            questions.add(tempQuestion);
            temp = "<html>";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return questions;
    }
}
