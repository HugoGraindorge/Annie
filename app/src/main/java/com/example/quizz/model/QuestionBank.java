package com.example.quizz.model;

import java.util.List;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
    }

    public Question getCurrentQuestion() {
        return mQuestionList.get(mQuestionIndex);
    }

    public Question getNextQuestion() {
        mQuestionIndex++;
        return getCurrentQuestion();
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }
}