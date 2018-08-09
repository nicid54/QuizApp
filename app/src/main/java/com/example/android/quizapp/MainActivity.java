package com.example.android.quizapp;

import android.content.res.Resources;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<QuizCard> cardList;
    private ArrayList<Integer> radioButtonViews;
    private int correctScore = 0;
    private int incorrectScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the card data, hide the quiz buttons, and disable the submit button
        initCardData();
        showRadioButtons(false);
        (findViewById(R.id.submit_button)).setEnabled(false);
        loadCard(cardList.get(0));
    }

    /**
     * Initializes the Quiz Card data for the quiz app
     */
    private void initCardData() {

        //Create new ArrayList
        cardList = new ArrayList<QuizCard>();

        //Card 1 data
        String question = "What engangered animal is this?";
        ArrayList<String> answerChoice = new ArrayList<>(Arrays.asList("Sumatran Tiger", "Kangaroo", "Dog", "Bird"));
        String answerCorrect = "Sumatran Tiger";
        cardList.add(new QuizCard(QuizCard.QuizType.RADIOBUTTON,
                                    R.drawable.sumatran_tiger,
                                    question,
                                    answerChoice,
                                    answerCorrect));

    }


    /**
     * Loads an input QuizCard's data into the views and shows the correct quiz type
     * @param card the input QuizCard
     */
    private void loadCard(QuizCard card) {

        //Load the card image
        Drawable image = getResources().getDrawable(card.getImageId());
        ((ImageView) findViewById(R.id.card_image)).setImageDrawable(image);

        //Radio button quiz
        if (card.getType() == QuizCard.QuizType.RADIOBUTTON) {
            //Show the radio buttons
            showRadioButtons(true);

            //Load the radio button views
            radioButtonViews = new ArrayList<>(Arrays.asList(R.id.answer1,
                                                                                R.id.answer2,
                                                                                R.id.answer3,
                                                                                R.id.answer4));
            //Load the answer data
            loadQuizAnswers(radioButtonViews, card.getAnswerChoice());
        }

    }

    /**
     * Takes in an ArrayList of quiz answers and adds them to an input ArrayList of button views
     * @param buttonViewIds is the ArrayList of View Ids for the quiz buttons
     * @param quizAnswers is the ArrayList of quiz answers
     */
    private void loadQuizAnswers(ArrayList<Integer> buttonViewIds, ArrayList<String> quizAnswers) {

        //Randomly Sort the ArrayList of answers
        Collections.shuffle(quizAnswers);

        //Load the answers in the button views
        for (int i  = 0; i < buttonViewIds.size(); i++) {
            int buttonViewId = buttonViewIds.get(i);
            String answer = quizAnswers.get(i);

            ((Button)findViewById(buttonViewId)).setText(answer);
        }
    }

    /**
     * Checks if the submitted answer was correct, tallies the score, and loads the next quiz card
     * @param view is the submit button
     */
    public void submitButtonClicked(View view) {

       //if the selected answer is correct
       if (getCheckedAnswer().equals((cardList.get(0)).getAnswerCorrect())) {
           correctScore++;
       }
       else {
           incorrectScore++;
       }

       //remove the current card and load the next card
       cardList.remove(0);
       if (!cardList.isEmpty()) {

           loadCard(cardList.get(0));
       }//if no other card, then show score
       else {

           String toastMessage = "Correct: " + correctScore + " Incorrect: " + incorrectScore;
           (Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)).show();
           (findViewById(R.id.submit_button)).setEnabled(false);
       }
    }

    /**
     * Unchecks all other radio buttons when a radio button is checked
     * (a RadioGroup was not used because of the layout limitations this method handles the selection logic)
     * @param view is the checked radio button
     */
    public void radioButtonChecked(View view) {

        //uncheck all non-selected radio buttons
        for (Integer viewId : radioButtonViews) {
            if (viewId != view.getId()) {
                ((RadioButton) findViewById(viewId)).setChecked(false);
            }
        }

        //enable the submit button
        (findViewById(R.id.submit_button)).setEnabled(true);
    }


    /**
     * Returns the String answer of the currently checked radio button
     * @return is the String of the currently selected answer or an empty String
     * if nothing is checked
     */
    private String getCheckedAnswer() {

        //find the answer of the checked radio button
        for (Integer viewId: radioButtonViews) {
            RadioButton radioButton = findViewById(viewId);

            if (radioButton.isChecked()) {
                return (radioButton.getText()).toString();
            }
        }

        return ""; //No radio buttons checked
    }


    /**
     * Depending on then input boolean hides or shows the radio buttons
     * @param isVisable is a boolean for whether the buttons should be shown
     */
    private void showRadioButtons(boolean isVisable) {

        if (isVisable) {
            (findViewById(R.id.answer1)).setVisibility(View.VISIBLE);
            (findViewById(R.id.answer2)).setVisibility(View.VISIBLE);
            (findViewById(R.id.answer3)).setVisibility(View.VISIBLE);
            (findViewById(R.id.answer4)).setVisibility(View.VISIBLE);
        }
        else {
            (findViewById(R.id.answer1)).setVisibility(View.GONE);
            (findViewById(R.id.answer2)).setVisibility(View.GONE);
            (findViewById(R.id.answer3)).setVisibility(View.GONE);
            (findViewById(R.id.answer4)).setVisibility(View.GONE);
        }
    }
}
