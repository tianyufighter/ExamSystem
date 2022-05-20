package com.tianyufighter.model;

import java.io.Serializable;
import java.util.Arrays;

public class ChoiceQuestion implements Serializable {
    private static final long serialVersionUID = -6780737817804289638L;
    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String[]  answer;

    public String getQuestion() {
        return question;
    } 

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return A; 
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String[] getAnswer() {
        return answer;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", A='" + A + '\'' +
                ", B='" + B + '\'' +
                ", C='" + C + '\'' +
                ", D='" + D + '\'' +
                ", answer=" + Arrays.toString(answer) +
                '}';
    }
}
