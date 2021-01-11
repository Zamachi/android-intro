package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionModel implements Parcelable {

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

    protected QuestionModel(Parcel in) {
        super();
        questionTitle = in.readString();
        answerA = in.readString();
        answerB = in.readString();
        answerC = in.readString();
        questionNumber = in.readInt();
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionTitle);
        dest.writeString(answerA);
        dest.writeString(answerB);
        dest.writeString(answerC);
        dest.writeInt(questionNumber);
    }
}
