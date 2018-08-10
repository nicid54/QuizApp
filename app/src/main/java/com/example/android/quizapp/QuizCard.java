
package com.example.android.quizapp;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizCard {
    public enum QuizType {
        CHECKBOX, RADIOBUTTON, TEXTENTRY
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
     * Returns a String of the correct answer for the QuizCard
     * @return the correct answer
     */
    public ArrayList<String> getCorrectAnswerList() {
        return correctAnswerList;
    }
}
