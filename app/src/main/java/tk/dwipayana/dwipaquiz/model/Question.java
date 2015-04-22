package tk.dwipayana.dwipaquiz.model;

import java.util.List;

/**
 * Created by Kadek Juliana Parwanta on 4/22/15.
 */
public class Question {
    private int ID;
    private String mQuestion;
    private String mAnswer;
    private String[] mOptions;
    private int mYear;
    private TOPICS mTopic;
    private MAJOR mMajor;
    private String mSolutionID;

    public enum TOPICS {
        MATH,
        INDO,
        ENG,
        NONE
    }

    public enum MAJOR {
        IPA,
        IPS,
        NONE
    }

    public Question(int year, TOPICS topic, MAJOR major, String question, String[] options, String answer, String solutionId) {
        mYear = year;
        mTopic = topic;
        mMajor = major;
        mQuestion = question;
        mOptions = options;
        mAnswer = answer;
        mSolutionID = solutionId;
    }

    public Question() {
        mYear = 0;
        mTopic = TOPICS.NONE;
        mMajor = MAJOR.NONE;
        mQuestion = "";
        mOptions = new String[3];
        mAnswer = "";
        mSolutionID = "";
        ID = 0;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }

    public String[] getOptions() {
        return mOptions;
    }

    public void setOptions(String[] options) {
        this.mOptions = options;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setID(int id)
    {
        this.ID=id;
    }

    public int getID() {
        return ID;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }


    public TOPICS getTopic() {
        return mTopic;
    }

    public void setTopic(TOPICS mTopic) {
        this.mTopic = mTopic;
    }

    public MAJOR getMajor() {
        return mMajor;
    }

    public void setMajor(MAJOR mMajor) {
        this.mMajor = mMajor;
    }

    public String getSolutionID() {
        return mSolutionID;
    }

    public void setSolutionID(String mSolutionID) {
        this.mSolutionID = mSolutionID;
    }

}
