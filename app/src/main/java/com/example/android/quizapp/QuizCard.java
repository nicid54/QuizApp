
package com.example.android.quizapp;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizCard implements Parcelable {
    public enum QuizType {
        START, CHECKBOX, RADIOBUTTON, TEXTENTRY, END
    }

    private QuizType type;
    private int imageId;
    private String question;
    private ArrayList<String> answerList;
    private ArrayList<String> correctAnswerList;

    /**
     * Constructor for QuizCard: stores card image, question, possible answer choices, and the correct answer
     * @param type is the type of QuizCard
     * @param imageId is the image id on the card
     * @param question is the question for the card
     * @param answerList is the list of possible answers to choose from
     * @param correctAnswerList is the correct answer for the card
     */
    public QuizCard(QuizType type, int imageId, String question, ArrayList<String> answerList, ArrayList<String> correctAnswerList) {
        this.type = type;
        this.imageId = imageId;
        this.question = question;
        this.answerList = answerList;
        this.correctAnswerList = correctAnswerList;
    }

    /**
     * Returns the QuizType of the QuizCard
     * @return the type of quiz
     */
    public QuizType getType() {
        return type;
    }

    /**
     * Returns the Image Id of the QuizCard
     * @return the image id
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Returns a String of the question for the QuizCard
     * @return is the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns an ArrayList of the answers to choose from for the quiz
     * @return is the list of answers
     */
    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    /**
     * Replaces the QuizCard ArrayList of answers with a new ArrayList of answers
     * @param answerList is the list of answers
     */
    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }

    /**
     * Returns a String of the correct answer for the QuizCard
     * @return the correct answer
     */
    public ArrayList<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    /**
     * Parcelable Interface Requirement: returns a description of the object as an integer
     * @return is the integer description
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable Interface Requirement: writes the QuizCard data to the parcel
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(type);
        parcel.writeInt(imageId);
        parcel.writeString(question);
        parcel.writeStringList(answerList);
        parcel.writeStringList(correctAnswerList);
    }

    /**
     * Parcelable Interface Requiremnt: creates a singleton with a method to read the written
     * QuizCard data.
     */
    public static final Parcelable.Creator<QuizCard> CREATOR = new Parcelable.Creator<QuizCard>() {

         /**
          * Creates a new QuizCard by reading the stored data in the parcel
          * @param parcel
          * @return
          */
         @Override
         public QuizCard createFromParcel(Parcel parcel) {

             QuizType quizType = (QuizType) parcel.readValue(QuizType.class.getClassLoader());
             int imageId = parcel.readInt();
             String question = parcel.readString();
             ArrayList<String> answerList = new ArrayList<>();
             parcel.readStringList(answerList);
             ArrayList<String> correctAnswerList = new ArrayList<>();
             parcel.readStringList(correctAnswerList);

             return new QuizCard(quizType, imageId, question, answerList, correctAnswerList);
         }

        @Override
        public QuizCard[] newArray(int i) {
            return new QuizCard[0];
        }
    };
}
