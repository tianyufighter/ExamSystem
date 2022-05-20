package com.tianyufighter.receive;


import com.tianyufighter.model.ChoiceQuestion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class GetChoiceSelective  {
    /**
     * 获取题目并存储到数组当中
     * @param flag 用flag标记是获取单项选择题还是多项选择题，若flag=true表示单项选择题，反之多项选择题
     * @return
     */
    public static List<ChoiceQuestion> getChoiceQuestion(String file, boolean flag) {
        // 存放选择题的集合
        List<ChoiceQuestion> questions = new ArrayList<ChoiceQuestion>();
        // 用该变量标记是否读取到了指定的选择题的类型，如果是就存题，反之
        boolean canDo = false;
        // 获取文件每行的变量
        String line;
        String temp = "<html>";
        // 临时存储答案
        String tempAnswer = "";
        BufferedReader in = null;
        // 临时存放选择题的对象
        ChoiceQuestion tempQuestion = null;
        try {
            // 创建指定的文件路径的输入流
            in = new BufferedReader(new FileReader(file));
            while((line = in.readLine()) != null) {
                // 如果当前题型读完了，就退出
                if(canDo == true && (!(line.equals(""))) && (line.charAt(0) == '二' || line.charAt(0) == '三')) {
                    tempQuestion.setD(temp);
                    questions.add(tempQuestion);
                    tempQuestion = new ChoiceQuestion();
                    break;
                }
                // 如果读到指定的行就判断
                if(flag == true && (!(line.equals(""))) && line.contains("单选题")) {
                    canDo = true;
                    continue;
                }
                if(flag == false && (!(line.equals(""))) && line.contains("多选题")) {
                    canDo = true;
                    continue;
                }
                if(canDo) {
                    if(!(line.equals(""))) {
                        // 如果读取的一行中含有<或>给它替换成html中的格式
                        if(line.contains("<")) {
                            line = line.replace("<", "&lt;");
                        }
                        if(line.contains(">")) {
                            line = line.replace(">", "&gt;");
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
                                if(temp.charAt(i) >= 'A' && temp.charAt(i) <= 'D') {
                                    tempAnswer += temp.charAt(i);
                                    temp = temp.replace(temp.charAt(i), ' ');
                                }
                            }
                            // 存储答案
                            tempQuestion.setAnswer(tempAnswer.split(""));
                            tempAnswer = "";
                            // 新添加
                            temp += "</html>";
                            tempQuestion.setQuestion(temp);
                            temp = "<html>";
                        } else if(line.charAt(0) == 'B') {
                            temp += "</html>";
                            tempQuestion.setA(temp);
                            temp = "<html>";
                        } else if(line.charAt(0) == 'C') {
                            // 新添加
                            temp += "</html>";
                            tempQuestion.setB(temp);
                            temp = "<html>";
                        } else if(line.charAt(0) == 'D') {
                            // 新添加
                            temp += "</html>";
                            tempQuestion.setC(temp);
                            temp = "<html>";
                        } else if(line.charAt(0) >= '0' && line.charAt(0) <= '9') {
                            // 对于读每个题型的第一个题时这是不需要将tempQuestion加入对象
                            if (tempQuestion != null) {
                                // 新添加
                                temp += "</html>";
                                tempQuestion.setD(temp);
                                temp = "<html>";
                                questions.add(tempQuestion);
                            }
                            tempQuestion = new ChoiceQuestion();
                        }
                    }
                    if(line != null) {
                        temp += line + "<br/>";
                    }
                }
            }
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
