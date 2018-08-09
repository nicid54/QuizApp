package com.example.android.quizapp;

import android.content.res.Resources;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<QuizCard> cardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the card data and hide the quiz buttons
        initCardData();
        showRadioButtons(false);
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
            ArrayList<Integer> radioButtonViews = new ArrayList<>(Arrays.asList(R.id.answer1,
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
     * Loads a QuizCard when the submit button is clicked
     * @param view is the submit button
     */
    public void submitButtonClicked(View view) {
        QuizCard card = cardList.get(0);
        int i = 5;
        loadCard(cardList.get(0));
    }

    /**
     * Deselects all other radio buttons when a radio button is checked
     * (a RadioGroup was not used because of the layout limitations this method handles the selection logic)
     * @param view is the checked radio button
     */
    public void radioButtonChecked(View view) {

        RadioButton button;
        if (view.getId() != R.id.answer1) {
            button = findViewById(R.id.answer1);
            button.setChecked(false);
        }

        if (view.getId() != R.id.answer2) {
            button = findViewById(R.id.answer2);
            button.setChecked(false);
        }

        if (view.getId() != R.id.answer3) {
            button = findViewById(R.id.answer3);
            button.setChecked(false);
        }

        if (view.getId() != R.id.answer4) {
            button =  findViewById(R.id.answer4);
            button.setChecked(false);
        }
    }


    /**
     * Returns the currently checked radio button
     * @return is the currently checked button or -1 if no button is checked
     */
    private int getCheckedRadioButton() {

        if (((RadioButton) findViewById(R.id.answer1)).isChecked()) {
            return R.id.answer1;
        }

        if (((RadioButton) findViewById(R.id.answer2)).isChecked()) {
            return R.id.answer2;
        }

        if (((RadioButton) findViewById(R.id.answer3)).isChecked()) {
            return R.id.answer3;
        }

        if (((RadioButton) findViewById(R.id.answer4)).isChecked()) {
            return R.id.answer4;
        }

        return -1; //No radio buttons checked
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
