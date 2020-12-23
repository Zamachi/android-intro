package com.example.project;

public class QuestionModel {

    private String questionTitle,answerA,answerB,answerC;
    private int questionNumber;

    public QuestionModel() {
    }

    public QuestionModel(String questionTitle, String answerA, String answerB, String answerC, int questionNumber) {
        this.questionTitle = questionTitle;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.questionNumber = questionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    @Override
    public String toString() {
        return "Question No.\t" + questionNumber +
                ":\t" + questionTitle + "\n" +
                "A:" + answerA + "\n" +
                "B:" + answerB + "\n" +
                "C:" + answerC + "\n\n";
    }
}
