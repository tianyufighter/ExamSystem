package com.tianyufighter.receive;


import com.tianyufighter.model.ChoiceQuestion;
import com.tianyufighter.model.JudgeQuestion;

import java.util.List;

public class GetAllQuestion {
    public List<ChoiceQuestion> danxuan = null;
    public List<ChoiceQuestion> duoxuan = null;
    public List<JudgeQuestion> panduan = null;

    public void getQuestion(String file) {
        this.danxuan = GetChoiceSelective.getChoiceQuestion(file, true);
        this.duoxuan = GetChoiceSelective.getChoiceQuestion(file, false);
        this.panduan = GetJudgeQuestion.getJudgeQuestion(file);
    } 

    public List<ChoiceQuestion> getDanxuan() {
        return danxuan;
    }

    public void setDanxuan(List danxuan) {
        this.danxuan = danxuan;
    }

    public List<ChoiceQuestion> getDuoxuan() {
        return duoxuan;
    }

    public void setDuoxuan(List duoxuan) {
        this.duoxuan = duoxuan;
    }

    public List<JudgeQuestion> getPanduan() {
        return panduan;
    }

    public void setPanduan(List panduan) {
        this.panduan = panduan;
    }
}
