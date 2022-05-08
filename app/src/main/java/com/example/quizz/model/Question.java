package com.example.quizz.model;

import java.util.List;

public class Question {

    private final String mQuestion;
    private final List<String> mChoiceList;

    public Question(String question, List<String> choiceList) {
        mQuestion = question;
        mChoiceList = choiceList;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }
}