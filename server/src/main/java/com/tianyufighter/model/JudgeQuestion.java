package com.tianyufighter.model;

import java.io.Serializable;

public class JudgeQuestion implements Serializable {
    private static final long serialVersionUID = -5385376902787916126L;
    private String question;
    private String A;
    private String B;
    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "JudgeQuestion{" +
                "question='" + question + '\'' +
                ", A='" + A + '\'' +
                ", B='" + B + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
