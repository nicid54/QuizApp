package com.example.android.quizapp;

import android.content.res.Resources;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<QuizCard> cardList;
    private ArrayList<Integer> radioButtonViews;
    private ArrayList<Integer> checkboxViews;
    private int correctScore = 0;
    private int incorrectScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the card data, hide the quiz buttons, and disable the submit button
        initCardData();
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
        String question1 = "What engangered animal is this?";
        ArrayList<String> answerList1 = new ArrayList<>(Arrays.asList("Sumatran Tiger", "Kangaroo", "Dog", "Bird"));
        ArrayList<String> correctAnswerList1 = new ArrayList<>(Arrays.asList("Sumatran Tiger"));
        cardList.add(new QuizCard(QuizCard.QuizType.RADIOBUTTON,
                R.drawable.sumatran_tiger,
                question1,
                answerList1,
                correctAnswerList1));

        //Card 2 data
        String question2 = "What is true about the Kakapo?";
        ArrayList<String> answerList2 = new ArrayList<>(Arrays.asList("Native to New Zealand",
                                                                        "Can fly for long distances",
                                                                        "Can live for nearly 100 years",
                                                                        "Also known as the owl parrot"));
        ArrayList<String> correctAnswerList2 = new ArrayList<>(Arrays.asList("Native to New Zealand",
                                                                        "Can live for nearly 100 years",
                                                                        "Also known as the owl parrot"));
        cardList.add(new QuizCard(QuizCard.QuizType.CHECKBOX,
                R.drawable.kakapo,
                question2,
                answerList2,
                correctAnswerList2));
    }


    /**
     * Loads an input QuizCard's data into the views and shows the correct quiz type
     *
     * @param card the input QuizCard
     */
    private void loadCard(QuizCard card) {

        //Load the card image
        Drawable image = getResources().getDrawable(card.getImageId());
        ((ImageView) findViewById(R.id.card_image)).setImageDrawable(image);

        //Radio button quiz
        if (card.getType() == QuizCard.QuizType.RADIOBUTTON) {
            //Show the radio buttons and hide the other answer submission types
            showRadioButtons(true);
            showCheckboxes(false);

            //Load the radio button views
            radioButtonViews = new ArrayList<>(Arrays.asList(R.id.radio_button1,
                    R.id.radio_button2,
                    R.id.radio_button3,
                    R.id.radio_button4));

            //Load the answer data
            loadQuizAnswers(radioButtonViews, card.getAnswerList());
        } else if (card.getType() == QuizCard.QuizType.CHECKBOX) {
            //Show the checkboxes and hide the other answer submission types
            showCheckboxes(true);
            showRadioButtons(false);

            //Load the checkbox views
            checkboxViews = new ArrayList<>(Arrays.asList(R.id.checkbox1,
                    R.id.checkbox2,
                    R.id.checkbox3,
                    R.id.checkbox4));

            //Load the answer data
            loadQuizAnswers(checkboxViews, card.getAnswerList());
        }

    }

    /**
     * Takes in an ArrayList of quiz answers and adds them to an input ArrayList of button views
     *
     * @param buttonViewIds is the ArrayList of View Ids for the quiz buttons
     * @param quizAnswers   is the ArrayList of quiz answers
     */
    private void loadQuizAnswers(ArrayList<Integer> buttonViewIds, ArrayList<String> quizAnswers) {

        //Randomly Sort the ArrayList of answers
        Collections.shuffle(quizAnswers);

        //Load the answers in the button views
        for (int i = 0; i < buttonViewIds.size(); i++) {
            int buttonViewId = buttonViewIds.get(i);
            String answer = quizAnswers.get(i);

            ((Button) findViewById(buttonViewId)).setText(answer);
        }
    }

    /**
     * Checks if the submitted answer was correct, tallies the score, and loads the next quiz card
     *
     * @param view is the submit button
     */
    public void submitButtonClicked(View view) {

        //if there are no more cards to process then exit the method
        if (cardList.isEmpty()) {
            return;
        }

        //get the current card
        QuizCard card = cardList.get(0);

        //if the selected answer(s) is correct, increment the correct score
        if (card.getType() == QuizCard.QuizType.RADIOBUTTON) {
            correctScore += isAnswerCorrect(radioButtonViews, card.getCorrectAnswerList()) ? 1 : 0;
        }
        else if (card.getType() == QuizCard.QuizType.CHECKBOX) {

            correctScore += isAnswerCorrect(checkboxViews, card.getCorrectAnswerList()) ? 1 : 0;
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
     *
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
     * Compares the list of selected answers in the quiz views with the correct answer list
     *
     * @param viewList is a list of the selected views
     * @param correctAnswerList is a list of the correct answer(s)
     * @return is whether the selected answers were correct
     */
    private boolean isAnswerCorrect(ArrayList<Integer> viewList, ArrayList<String> correctAnswerList) {

        ArrayList<String> answerViewList = new ArrayList<>();

        //add the answers of the selected views to a list and set to lowercase
        for (Integer viewId : viewList) {
            CompoundButton button = findViewById(viewId);

            if (button.isChecked()) {
                answerViewList.add(((button.getText()).toString()).toLowerCase());
            }
        }

        //sort the answer list and correct answer list alphabetically
        Collections.sort(answerViewList);
        Collections.sort(correctAnswerList);

        //compare the lists ignoring case
        return (answerViewList.toString()).equalsIgnoreCase(correctAnswerList.toString());
    }

    /**
     * Depending on then input boolean hides or shows the radio buttons
     *
     * @param isVisable is a boolean for whether the buttons should be shown
     */
    private void showRadioButtons(boolean isVisable) {

        if (isVisable) {
            (findViewById(R.id.radio_button1)).setVisibility(View.VISIBLE);
            (findViewById(R.id.radio_button2)).setVisibility(View.VISIBLE);
            (findViewById(R.id.radio_button3)).setVisibility(View.VISIBLE);
            (findViewById(R.id.radio_button4)).setVisibility(View.VISIBLE);
        } else {
            (findViewById(R.id.radio_button1)).setVisibility(View.GONE);
            (findViewById(R.id.radio_button2)).setVisibility(View.GONE);
            (findViewById(R.id.radio_button3)).setVisibility(View.GONE);
            (findViewById(R.id.radio_button4)).setVisibility(View.GONE);
        }
    }

    /**
     * Depending on the input boolean hides or shows the checkboxes
     *
     * @param isVisable
     */
    private void showCheckboxes(boolean isVisable) {

        if (isVisable) {
            (findViewById(R.id.checkbox1)).setVisibility(View.VISIBLE);
            (findViewById(R.id.checkbox2)).setVisibility(View.VISIBLE);
            (findViewById(R.id.checkbox3)).setVisibility(View.VISIBLE);
            (findViewById(R.id.checkbox4)).setVisibility(View.VISIBLE);
        } else {
            (findViewById(R.id.checkbox1)).setVisibility(View.GONE);
            (findViewById(R.id.checkbox2)).setVisibility(View.GONE);
            (findViewById(R.id.checkbox3)).setVisibility(View.GONE);
            (findViewById(R.id.checkbox4)).setVisibility(View.GONE);
        }
    }
}
