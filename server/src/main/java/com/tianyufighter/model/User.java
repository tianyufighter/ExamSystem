package com.tianyufighter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放用户信息的类
 */
public class User {
    private Integer id;
    private String username;
    private String identity;
    private String examineeNumber;
    private String password;
    private Boolean islogin;
    private Integer score;
    public List<Integer> choiceRecord = new ArrayList<Integer>();
    public List<Integer> judgeRecord = new ArrayList<Integer>();
    private Boolean isSubmit;
    private Boolean isException;

    public User() {
    }

    public User(Integer id, String username, String identity, String examineeNumber, String password, Boolean islogin, Integer score, Boolean isSubmit) {
        this.id = id;
        this.username = username;
        this.identity = identity;
        this.examineeNumber = examineeNumber;
        this.password = password;
        this.islogin = islogin;
        this.score = score;
        this.isSubmit = isSubmit;
        this.isException = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getExamineeNumber() {
        return examineeNumber;
    }

    public void setExamineeNumber(String examineeNumber) {
        this.examineeNumber = examineeNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(Boolean islogin) {
        this.islogin = islogin;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getSubmit() {
        return isSubmit;
    }

    public void setSubmit(Boolean submit) {
        isSubmit = submit;
    }

    public Boolean getException() {
        return isException;
    }

    public void setException(Boolean exception) {
        isException = exception;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", identity='" + identity + '\'' +
                ", examineeNumber='" + examineeNumber + '\'' +
                ", password='" + password + '\'' +
                ", islogin=" + islogin +
                ", score=" + score +
                ", isSubmit=" + isSubmit +
                '}';
    }
}
