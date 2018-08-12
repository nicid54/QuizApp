package com.example.android.quizapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<QuizCard> mCardList;
    private ArrayList<Integer> mRadioButtonViews;
    private ArrayList<Integer> mCheckboxViews;
    private ArrayList<Integer> mEditTextViews;
    private int mCorrectScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the answer view lists, card data, and loads the first quiz card
        initViewLists();
        initCardData();
        loadCard(mCardList.get(0));
        initListeners();
    }

    /**
     * Initializes the RadioButton and Checkbox View Lists
     */
    private void initViewLists() {
        //Load the RadioButton Views
        mRadioButtonViews = new ArrayList<>(Arrays.asList(R.id.radio_button1,
                R.id.radio_button2,
                R.id.radio_button3,
                R.id.radio_button4));

        //Load the Checkbox Views
        mCheckboxViews = new ArrayList<>(Arrays.asList(R.id.checkbox1,
                R.id.checkbox2,
                R.id.checkbox3,
                R.id.checkbox4));

        //Load the EditText Views
        mEditTextViews = new ArrayList<Integer>(Arrays.asList(R.id.edit_text));
    }

    /**
     * Initializes the Quiz Card data for the quiz app
     */
    private void initCardData() {

        //Create new ArrayList
        mCardList = new ArrayList<QuizCard>();

        //Cover Card
        String startTitle = "Endangered Animal Quiz!";
        mCardList.add(new QuizCard(QuizCard.QuizType.START,
                R.drawable.kakapo,
                startTitle,
                null,
                null));

        //Card 1 data
        String question1 = "What endangered animal is this?";
        ArrayList<String> answerList1 = new ArrayList<>(Arrays.asList("Sumatran Tiger", "Kangaroo", "Dog", "Bird"));
        ArrayList<String> correctAnswerList1 = new ArrayList<>(Arrays.asList("Sumatran Tiger"));
        mCardList.add(new QuizCard(QuizCard.QuizType.RADIOBUTTON,
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
        mCardList.add(new QuizCard(QuizCard.QuizType.CHECKBOX,
                R.drawable.kakapo,
                question2,
                answerList2,
                correctAnswerList2));

        //Card 4 data
        String question4 = "What endangered animal is this?";
        ArrayList<String> correctAnswerList4 = new ArrayList<>(Arrays.asList("Panda"));
        mCardList.add(new QuizCard(QuizCard.QuizType.TEXTENTRY,
                                    R.drawable.sumatran_tiger,
                                    question4,
                                    new ArrayList<String>(),
                                    correctAnswerList4));

        //Donate Card
        String endTitle = "Please Donate to the World Wildlife Fund!";
        mCardList.add(new QuizCard(QuizCard.QuizType.END,
                                    R.drawable.kakapo,
                                    endTitle,
                                    null,
                                    null));
    }

    /**
     * Initializes the listeners for the quiz app
     */
    private void initListeners() {

        //add a listener to check if the user has input text and enable the submit button
        final EditText mEditText = findViewById(mEditTextViews.get(0));

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Button submitButton = findViewById(R.id.submit_button);

                if (isTextEditEmpty(mEditText)) {
                    submitButton.setEnabled(false);
                }
                else {
                    submitButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Loads an input QuizCard's data into the views and shows the correct quiz type
     *
     * @param card the input QuizCard
     */
    private void loadCard(QuizCard card) {

        //Load the card question text
        ((TextView) findViewById(R.id.card_question)).setText(card.getQuestion());

        //Load the card image
        Drawable image = getResources().getDrawable(card.getImageId());
        ((ImageView) findViewById(R.id.card_image)).setImageDrawable(image);

        //Initialize default submit button text and enabled state
        String buttonText = "Submit";
        boolean buttonEnabledState = false;

        //Radio button quiz
        if (card.getType() == QuizCard.QuizType.RADIOBUTTON) {

            //Show the radio buttons and hide the other answer submission types
            setViewsVisible(mRadioButtonViews,true);
            setViewsVisible(mCheckboxViews,false);
            setViewsVisible(mEditTextViews, false);

            //Load the answer data
            loadQuizAnswers(mRadioButtonViews, card.getAnswerList());
        } else if (card.getType() == QuizCard.QuizType.CHECKBOX) { //Checkbox quiz

            //Show the checkboxes and hide the other answer submission types
            setViewsVisible(mCheckboxViews,true);
            setViewsVisible(mRadioButtonViews,false);
            setViewsVisible(mEditTextViews,false);

            //Load the answer data
            loadQuizAnswers(mCheckboxViews, card.getAnswerList());
        }
        else if (card.getType() == QuizCard.QuizType.TEXTENTRY){ //Edit Text quiz

            //Show the edit text view
            setViewsVisible(mEditTextViews,true);
            setViewsVisible(mRadioButtonViews,false);
            setViewsVisible(mCheckboxViews,false);
        }
        else if (card.getType() == QuizCard.QuizType.START) {//Start Quiz Cover Card

            //Hide the RadioButton, Checkbox, and EditText Views
            setViewsVisible(mRadioButtonViews, false);
            setViewsVisible(mCheckboxViews, false);
            setViewsVisible(mEditTextViews, false);

            //Set Start Button data
            buttonEnabledState = true;
            buttonText = "Start";

            //Add Listener to check if text has been input

        }
        else { //End Quiz Donate Card

            //Hide the RadioButton, Checkbox, and EditText Views
            setViewsVisible(mRadioButtonViews, false);
            setViewsVisible(mCheckboxViews, false);
            setViewsVisible(mEditTextViews, false);

            //Display the Donate Button
            buttonEnabledState = true;
            buttonText = "Donate";
        }

        //Set the submit button text and the initial state
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setText(buttonText);
        findViewById(R.id.submit_button).setEnabled(buttonEnabledState);
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
        if (mCardList.isEmpty()) {
            return;
        }

        //get the current card
        QuizCard card = mCardList.get(0);

        //if the selected answer(s) is correct, increment the correct score
        if (card.getType() == QuizCard.QuizType.RADIOBUTTON) {
            mCorrectScore += isAnswerCorrect(mRadioButtonViews, card.getCorrectAnswerList()) ? 1 : 0;
        }
        else if (card.getType() == QuizCard.QuizType.CHECKBOX) {

            mCorrectScore += isAnswerCorrect(mCheckboxViews, card.getCorrectAnswerList()) ? 1 : 0;
        }
        else if (card.getType() == QuizCard.QuizType.TEXTENTRY) { //quiz type is edit text

            mCorrectScore += isAnswerCorrect(new ArrayList<Integer>(Arrays.asList(R.id.edit_text)), card.getCorrectAnswerList()) ? 1 : 0;
        }
        else if (card.getType() == QuizCard.QuizType.END) { //donate card

            //Open the donation website in a browser
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://worldwildlife.org"));
            startActivity(intent);
        }



        //if there are two quiz cards remaining (last is donate card), display results of the quiz
        if (mCardList.size() == 2) {
            //Display the results of the quiz once
            String toastMessage = "You answered " + mCorrectScore + " questions correctly!";
            (Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)).show();
        }

        //if there are more quiz cards to load, remove the current QuizCard and load the next one
        if (mCardList.size() != 1) {
            mCardList.remove(0);
            loadCard(mCardList.get(0));
        }
    }

    /**
     * Unchecks all other radio buttons when a radio button is checked
     * (a RadioGroup was not used because of the layout limitations this method handles the selection logic)
     *
     * @param view is the checked radio button view
     */
    public void radioButtonChecked(View view) {

        //uncheck all non-selected radio buttons
        for (Integer viewId : mRadioButtonViews) {
            if (viewId != view.getId()) {
                ((RadioButton) findViewById(viewId)).setChecked(false);
            }
        }

        //enable the submit button
        (findViewById(R.id.submit_button)).setEnabled(true);
    }

    /**
     * Enables the submit button if at least one checkbox is checked
     * @param view is the checkbox view
     */
    public void checkboxChecked(View view) {

        //if at least one checkbox is checked, then enable the submit button
        for (Integer viewId : mCheckboxViews) {
            if (((CheckBox) findViewById(viewId)).isChecked()) {
                findViewById(R.id.submit_button).setEnabled(true);
                return;
            }
        }

        //no checkboxes are checked, so disable the submit button
        findViewById(R.id.submit_button).setEnabled(false);
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

            if(findViewById(viewId) instanceof CompoundButton) {
                CompoundButton button = findViewById(viewId);

                if (button.isChecked()) {
                    answerViewList.add(button.getText().toString());
                }
            }
            else if (findViewById(viewId) instanceof EditText) {
                EditText editText = findViewById(viewId);
                answerViewList.add(editText.getText().toString());
            }
        }

        //sort the answer list and correct answer list alphabetically
        Collections.sort(answerViewList);
        Collections.sort(correctAnswerList);

        //compare the lists ignoring case
        return (answerViewList.toString()).equalsIgnoreCase(correctAnswerList.toString());
    }


    /**
     * Depending on an input boolean hides or shows the views provided in the ArrayList
     * @param viewList is the ArrayList of View Ids to hide or show
     * @param isVisable is the boolean of whether to hide or show the views
     */
    public void setViewsVisible(ArrayList<Integer> viewList, boolean isVisable) {

        int visibility = isVisable ? View.VISIBLE : View.GONE;

        for (Integer viewId : viewList) {
            (findViewById(viewId)).setVisibility(visibility);
        }
    }

    /**
     * Checks to see if an EditText contains input text or not
     * @param editText is the EditText view
     * @return whether the EditText text is empty
     */
    private boolean isTextEditEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

}
