package com.example.android.quizapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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

import java.lang.reflect.Array;
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

        if (savedInstanceState != null) {
            QuizCard currentQuizCard = getQuizCardFromBundle(savedInstanceState);
            loadCard(currentQuizCard,false);
            reloadQuizCardStateFromBundle(savedInstanceState);
        }
        else {
            initCardData();
            loadCard(mCardList.get(0), true);
        }
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
        mEditTextViews = new ArrayList<Integer>();
        mEditTextViews.add(R.id.edit_text);
    }

    /**
     * Initializes the Quiz Card data for the quiz app
     * ToDo: Parse QuizCard data from XML File with JSON
     */
    private void initCardData() {

        //Create new ArrayList
        mCardList = new ArrayList<QuizCard>();

        //Cover Card
        String startTitle = getResources().getString(R.string.start_card_title);
        mCardList.add(new QuizCard(QuizCard.QuizType.START,
                R.drawable.black_rhino,
                startTitle,
                null,
                null));

        //Card 1 data
        String question1 = getResources().getString(R.string.card1_question);
        ArrayList<String> answerList1 = new ArrayList<>();
        answerList1.add(getResources().getString(R.string.card1_answer1));
        answerList1.add(getResources().getString(R.string.card1_answer2));
        answerList1.add(getResources().getString(R.string.card1_answer3));
        answerList1.add(getResources().getString(R.string.card1_answer4));

        ArrayList<String> correctAnswerList1 = new ArrayList<>();
        correctAnswerList1.add(getResources().getString(R.string.card1_answer1));

        mCardList.add(new QuizCard(QuizCard.QuizType.RADIOBUTTON,
                R.drawable.sumatran_tiger,
                question1,
                answerList1,
                correctAnswerList1));

        //Card 2 data
        String question2 = getResources().getString(R.string.card2_question);
        ArrayList<String> answerList2 = new ArrayList<>();
        answerList2.add(getResources().getString(R.string.card2_answer1));
        answerList2.add(getResources().getString(R.string.card2_answer2));
        answerList2.add(getResources().getString(R.string.card2_answer3));
        answerList2.add(getResources().getString(R.string.card2_answer4));

        ArrayList<String> correctAnswerList2 = new ArrayList<>();
        correctAnswerList2.add(getResources().getString(R.string.card2_answer1));
        correctAnswerList2.add(getResources().getString(R.string.card2_answer2));
        correctAnswerList2.add(getResources().getString(R.string.card2_answer3));

        mCardList.add(new QuizCard(QuizCard.QuizType.CHECKBOX,
                R.drawable.kakapo,
                question2,
                answerList2,
                correctAnswerList2));

        //Card 3 data
        String question3 = getResources().getString(R.string.card3_question);
        ArrayList<String> answerList3 = new ArrayList<>();
        answerList3.add(getResources().getString(R.string.card3_answer1));
        answerList3.add(getResources().getString(R.string.card3_answer2));
        answerList3.add(getResources().getString(R.string.card3_answer3));
        answerList3.add(getResources().getString(R.string.card3_answer4));

        ArrayList<String> correctAnswerList3 = new ArrayList<>();
        correctAnswerList3.add(getResources().getString(R.string.card3_answer1));

        mCardList.add(new QuizCard(QuizCard.QuizType.RADIOBUTTON,
                R.drawable.sea_turtle,
                question3,
                answerList3,
                correctAnswerList3));

        //Card 4 data
        String question4 = getResources().getString(R.string.card4_question);
        ArrayList<String> answerList4 = new ArrayList<>();
        answerList4.add(getResources().getString(R.string.card4_answer1));
        answerList4.add(getResources().getString(R.string.card4_answer2));
        answerList4.add(getResources().getString(R.string.card4_answer3));
        answerList4.add(getResources().getString(R.string.card4_answer4));

        ArrayList<String> correctAnswerList4 = new ArrayList<>();
        correctAnswerList4.add(getResources().getString(R.string.card4_answer1));
        correctAnswerList4.add(getResources().getString(R.string.card4_answer2));

        mCardList.add(new QuizCard(QuizCard.QuizType.CHECKBOX,
                R.drawable.orangutan,
                question4,
                answerList4,
                correctAnswerList4));

        //Card 5 data
        String question5 = getResources().getString(R.string.card5_question);
        ArrayList<String> correctAnswerList5 = new ArrayList<>();
        correctAnswerList5.add(getResources().getString(R.string.card5_answer1));
        mCardList.add(new QuizCard(QuizCard.QuizType.TEXTENTRY,
                                    R.drawable.panda,
                                    question5,
                                    null,
                                    correctAnswerList5));

        //Donate Card
        String endTitle = getResources().getString(R.string.end_card_title);
        mCardList.add(new QuizCard(QuizCard.QuizType.END,
                                    R.drawable.elephant,
                                    endTitle,
                                    null,
                                    null));
    }

    /**
     * Initializes the listeners for the quiz app
     */
    private void initListeners() {

        //add a listener to check if the user has input text and enable the submit button
        //final EditText mEditText = findViewById(mEditTextViews.get(0));
        final EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //this method will get called automatically because the EditText is reset
                //to an empty string when the App orientation changes, so check to make
                //sure that the current card is and EditText Quiz
                if (mCardList.get(0).getType() == QuizCard.QuizType.TEXTENTRY) {

                    Button submitButton = findViewById(R.id.submit_button);

                    if (isTextEditEmpty(editText)) {
                        submitButton.setEnabled(false);
                    } else {
                        submitButton.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Loads an input QuizCard's data into the views and shows the correct quiz type
     * ToDo: Swap out card questions with Fragments
     * @param card the input QuizCard
     */
    private void loadCard(QuizCard card, boolean shuffleAnswers) {

        //Load the card question text
        TextView questionTextView = findViewById(R.id.card_question);
        questionTextView.setText(card.getQuestion());
        questionTextView.setTextSize(getResources().getDimension(R.dimen.question_text_size));

        //Load the card image
        Drawable image = getResources().getDrawable(card.getImageId());
        ((ImageView) findViewById(R.id.card_image)).setImageDrawable(image);

        //Initialize default submit button text
        String buttonText = "Submit";
        boolean buttonEnabledState = false;

        //Radio button quiz
        if (card.getType() == QuizCard.QuizType.RADIOBUTTON) {

            //Show the radio buttons and hide the other answer submission types
            setViewsVisible(mRadioButtonViews,true);
            setViewsVisible(mCheckboxViews,false);
            setViewsVisible(mEditTextViews, false);

            //Load the answer data
            loadQuizAnswers(mRadioButtonViews, card.getAnswerList(), shuffleAnswers);
        } else if (card.getType() == QuizCard.QuizType.CHECKBOX) { //Checkbox quiz

            //Show the checkboxes and hide the other answer submission types
            setViewsVisible(mCheckboxViews,true);
            setViewsVisible(mRadioButtonViews,false);
            setViewsVisible(mEditTextViews,false);

            //Load the answer data
            loadQuizAnswers(mCheckboxViews, card.getAnswerList(), shuffleAnswers);
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

            //Set Start Quiz Title Text Size
            questionTextView.setTextSize(getResources().getDimension(R.dimen.start_title_text_size));

        }
        else { //End Quiz Donate Card

            //Hide the RadioButton, Checkbox, and EditText Views
            setViewsVisible(mRadioButtonViews, false);
            setViewsVisible(mCheckboxViews, false);
            setViewsVisible(mEditTextViews, false);

            //Display the Donate Button
            buttonEnabledState = true;
            buttonText = "Donate";

            //Set Quiz Title Text Size
            questionTextView.setTextSize(getResources().getDimension(R.dimen.start_title_text_size));
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
    private void loadQuizAnswers(ArrayList<Integer> buttonViewIds, ArrayList<String> quizAnswers, boolean shuffleAnswers) {

        //Randomly Sort the ArrayList of answers
        if (shuffleAnswers) {
            Collections.shuffle(quizAnswers);
        }

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

            mCorrectScore += isAnswerCorrect(mEditTextViews, card.getCorrectAnswerList()) ? 1 : 0;
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
            String toastMessage = getResources().getString(R.string.toast_score_message, mCorrectScore);
            Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        //if there are more quiz cards to load, remove the current QuizCard and load the next one
        if (mCardList.size() != 1) {
            mCardList.remove(0);
            loadCard(mCardList.get(0),true);
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
     * Depending on an input boolean hides or shows the views provided in the ArrayList.
     * If the views are hidden then they are also unchecked
     * @param viewList is the ArrayList of View Ids to hide or show
     * @param isVisable is the boolean of whether to hide or show the views
     */
    public void setViewsVisible(ArrayList<Integer> viewList, boolean isVisable) {

        int visibility = isVisable ? View.VISIBLE : View.GONE;

        for (Integer viewId : viewList) {

            //uncheck all RadioButtons and Checkboxes when hiding them
            if (!isVisable && findViewById(viewId) instanceof CompoundButton) {
                ((CompoundButton) findViewById(viewId)).setChecked(false);
            }

            findViewById(viewId).setVisibility(visibility);
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

    /**
     * Takes an ArrayList of View IDs and gets the order of the previously shuffled questions
     * This is used to ensure that the question order remains the same when the App is rotated
     * @param viewIdList
     */
    private void saveQuizAnswerOrder(QuizCard quizCard, ArrayList<Integer> viewIdList, ArrayList<Integer> checkedViewIdList) {

        ArrayList<String> answerListOrder = new ArrayList<>();

        //save the answers in the view in the current order in the array list
        for (Integer viewId : viewIdList) {
            CompoundButton compoundButton = findViewById(viewId);
            answerListOrder.add(compoundButton.getText().toString());

            //add view ids of checked buttons to the checked list
            if (compoundButton.isChecked()) {
                checkedViewIdList.add(viewId);
            }
        }

        //replace the QuizCards default answers with the answers in the current order
        quizCard.setAnswerList(answerListOrder);
    }

    /**
     * Stores App state data when it is placed in the background or the screen is rotated
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        writeBundleData(outState);
    }

    /**
     * Writes out the QuizCard App state data to an input bundle
     * @param outState is the bundle data to write to
     */
    private void writeBundleData(Bundle outState) {

        QuizCard currentQuizCard = mCardList.get(0);
        ArrayList<Integer> checkedViewList = new ArrayList<>();

        //If current card is radio button quiz
        if (currentQuizCard.getType() == QuizCard.QuizType.RADIOBUTTON) {
            saveQuizAnswerOrder(currentQuizCard, mRadioButtonViews, checkedViewList);
        }
        else if (currentQuizCard.getType() == QuizCard.QuizType.CHECKBOX) {
            saveQuizAnswerOrder(currentQuizCard, mCheckboxViews, checkedViewList);
        }
        else if (currentQuizCard.getType() == QuizCard.QuizType.TEXTENTRY) {
            //the answer order doesn't matter for the EditText quiz, but save the current
            //string value in the EditText View
            EditText editText = findViewById(R.id.edit_text);
            outState.putString("editTextMessage", editText.getText().toString());
        }

        //save the Card List, the list of the IDs of the checked Views
        //and the total questions correct score
        outState.putParcelableArrayList("cardList", mCardList);
        outState.putIntegerArrayList("checkedViewList", checkedViewList);
        outState.putInt("correctScore", mCorrectScore);
    }

    /**
     * Reads from an input Bundle to setup the QuizCard App data and returns the ch
     * @param savedState is the input bundle
     */
    private QuizCard getQuizCardFromBundle(Bundle savedState) {

        ArrayList<QuizCard> quizCardList = savedState.getParcelableArrayList("cardList");

        if (!quizCardList.isEmpty()) {
            return quizCardList.get(0);
        }

        return null;
    }

    /**
     * Reloads the QuizCard state data from the bundle including the answer data and and button states
     * @param savedState is the saved instance state bundle
     */
    private void reloadQuizCardStateFromBundle(Bundle savedState) {

        //repopulate member variables
        mCardList = savedState.getParcelableArrayList("cardList");
        mCorrectScore = savedState.getInt("correctScore");
        ArrayList<Integer> checkedViewList = savedState.getIntegerArrayList("checkedViewList");

        //get the current card
        QuizCard currentQuizCard = mCardList.get(0);

        //if the current card is a RadioButton quiz, then check the currently selected button
        if (currentQuizCard.getType() == QuizCard.QuizType.RADIOBUTTON && !checkedViewList.isEmpty()) {
            RadioButton radioButton = findViewById(checkedViewList.get(0));
            radioButton.setChecked(true); //ToDo: make sure that this doesn't fire the onClick method (radioButtonChecked)
            radioButtonChecked(findViewById(checkedViewList.get(0)));
        }
        else if (currentQuizCard.getType() == QuizCard.QuizType.CHECKBOX && !checkedViewList.isEmpty()) {
            //check all checked checkboxes
            for (Integer checkedViewId : checkedViewList) {
                CheckBox checkbox = findViewById(checkedViewId);
                checkbox.setChecked(true); //ToDo: make sure that this doesn't fire the onClick method (checkboxChecked)
                checkboxChecked(findViewById(checkedViewId));
            }
        }//if the current card is an EditText quiz
        else if (currentQuizCard.getType() == QuizCard.QuizType.TEXTENTRY) {

            String editTextMessage = savedState.getString("editTextMessage");
            //if the EditText contained text data, reinstate that information and enable the submit button
            if (editTextMessage != null && !editTextMessage.isEmpty()) {
                EditText editText = findViewById(R.id.edit_text);
                editText.setText(editTextMessage); //unnecessary since EditText automatically retains text for orientation change
                findViewById(R.id.submit_button).setEnabled(true);
            }
        }
    }
}
