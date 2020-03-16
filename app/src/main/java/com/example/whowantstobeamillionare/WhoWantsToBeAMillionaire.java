package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WhoWantsToBeAMillionaire extends Activity {

    //setup timers
    private CountDownTimer countdownMainTimer, countdownWrongQuestion;
    // in there the time is displayed
    private Button time;
    // init to display the graph
    private ImageView imageViewAudienceAnswer;

    //to know what was the correct answer after clicking
    private String correctanswer;
    // int which will take the numbers out of the arraylist to display non dupicate random questions
    private int intForNextQuestion = 0;
    //needed to set a counter to win after 14 questions
    private int correctAnswerCounter = 0;
    //if a answer has been pressed i don't want the user to click on certain jokers, so to make sure that the correct accesability is set on a new question
    private boolean joker1, joker2, joker3, joker4 = false;
    //all interaction buttons
    private Button buttonQuestion, buttonOptionA, buttonOptionB, buttonOptionC, buttonOptionD, buttonNext, buttonFiftyFifty, buttonPhoneSomeone, buttonAskAudience, buttonSkipQuestion;
    //go to the next element of the arraylist
    private int elementOfArraylist = 0;
    //initialize Mediaplayer for clock ticking and applause audio
    private MediaPlayer clockTickingAudio, applauseAudio;
    //initialize the list view
    private MySimpleArrayAdapter adapter;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.whowantstobeamillionare);

        getCurrentLocale();
        setupCountDown();

        Button button1 = findViewById(R.id.buttonBack);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(WhoWantsToBeAMillionaire.this, MainActivity.class);
                startActivityForResult(activityStart, 1);
                clockTickingAudio.pause();
                applauseAudio.pause();
                finish();
            }
        });
        Button button2 = findViewById(R.id.buttonSettings);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(WhoWantsToBeAMillionaire.this, Options.class);
                startActivityForResult(activityStart, 1);
                finish();
            }
        });

        clockTickingAudio = MediaPlayer.create(this, R.raw.ticktock);
        if (Options.isMusicOn()) {
            clockTickingAudio.start();
        } else {
            clockTickingAudio.pause();
        }
        applauseAudio = MediaPlayer.create(this, R.raw.applause2);
        clockTickingAudio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                clockTickingAudio.start();
            }
        });

        fillermehtod();
        populatelistview();

        imageViewAudienceAnswer = findViewById(R.id.graph);
        time = findViewById(R.id.buttonClock);

        buttonQuestion = findViewById(R.id.bquestion);
        buttonOptionA = findViewById(R.id.boptiona);
        buttonOptionB = findViewById(R.id.boptionb);
        buttonOptionC = findViewById(R.id.boptionc);
        buttonOptionD = findViewById(R.id.boptiond);
        buttonNext = findViewById(R.id.buttonNext);
        buttonFiftyFifty = findViewById(R.id.buttonfiftyfifty);
        buttonPhoneSomeone = findViewById(R.id.buttonPhone);
        buttonAskAudience = findViewById(R.id.buttonaudience);
        buttonSkipQuestion = findViewById(R.id.buttonflip);

        read();
    }

    private String getCurrentLocale() {
        return Locale.getDefault().getLanguage();
    }

    public void onPause() {
        super.onPause();
        countdownMainTimer.cancel();
        clockTickingAudio.pause();

    }
    public void onResume(){
        super.onResume();
        clockTickingAudio.start();
        countdownMainTimer.start();
    }

    /**
     * set up main countdown for each question
     */
    private void setupCountDown() {
        countdownMainTimer = new CountDownTimer(50000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);
                int s = (int) millisUntilFinished / 1000;
                System.out.println("This is the time" + s);
            }

            public void onFinish() {
                clockTickingAudio.pause();
                setContentView(R.layout.endscreen);
                Intent intent = new Intent(WhoWantsToBeAMillionaire.this, EndscreenTimeout.class);
                startActivity(intent);
                countdownMainTimer.cancel();
                finish();
            }
        };
    }

    public void clap() {
        clockTickingAudio.pause();
        if (Options.isMusicOn()) {
            applauseAudio.start();
        }
    }

    /**
     * these are the lines of which the listview consists
     */
    private void populatelistview() {
        String[] myitems = {"15   1 Million", "14     500.000", "13     125.000", "12     32.000", "11     32.000",
                "10     16.000", "9      8.000", "8      4.000", "7      2.000", "6      1.000", "5      500",
                "4      300", "3      200", "2      100", "1      50"};
        adapter = new MySimpleArrayAdapter(this, myitems) {};
        ListView list = findViewById(R.id.listViewMain);
        list.setAdapter(adapter);
    }

    /**
     * Will remain to answers with the correct answer included, and also display nothing to two possible wrong answers
     * @param view updates the picture to a used joker
     */

    public void onJokerFiftyFiftyClicked(View view) {
        Button b = findViewById(R.id.buttonfiftyfifty);
        b.setBackgroundResource(R.drawable.fiftyfiftyx);
        b.setEnabled(false);
        joker1 = true;

        String value2 = buttonOptionA.getText().toString();
        String value3 = buttonOptionB.getText().toString();
        String value4 = buttonOptionC.getText().toString();
        String value5 = buttonOptionD.getText().toString();

        if (value2.equalsIgnoreCase(correctanswer)) {
            buttonOptionB.setText("");
            buttonOptionC.setText("");
        } else if (value3.equalsIgnoreCase(correctanswer)) {
            buttonOptionA.setText("");
            buttonOptionD.setText("");
        } else if (value4.equalsIgnoreCase(correctanswer)) {
            buttonOptionA.setText("");
            buttonOptionD.setText("");
        } else if (value5.equalsIgnoreCase(correctanswer)) {
            buttonOptionB.setText("");
            buttonOptionC.setText("");
        }
    }

    /**
     * will open the phone activity to call someone
     * @param view  updates the picture to a used joker
     */
    public void onPhoneSomeoneClicked(View view) {
        Button b = findViewById(R.id.buttonPhone);
        b.setBackgroundResource(R.drawable.jokerphonex);
        b.setEnabled(false);
        joker2 = true;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }

    /**
     * will display a picture where you will see four graphs, depending on the correct answer the picture will show the highest percentage or the correct answer
     * @param view  updates the picture to a used joker
     */
    public void onAudienceJokerClicked(View view) {
        Button b = findViewById(R.id.buttonaudience);
        b.setBackgroundResource(R.drawable.jokerpublicx);
        b.setEnabled(false);
        joker3 = true;

        String value2 = buttonOptionA.getText().toString();
        String value3 = buttonOptionB.getText().toString();
        String value4 = buttonOptionC.getText().toString();
        String value5 = buttonOptionD.getText().toString();

        if (value2.equalsIgnoreCase(correctanswer)) {
            imageViewAudienceAnswer.setBackgroundResource(R.drawable.agraph);
            imageViewAudienceAnswer.setVisibility(View.VISIBLE);
        } else if (value3.equalsIgnoreCase(correctanswer)) {
            imageViewAudienceAnswer.setBackgroundResource(R.drawable.bgraph);
            imageViewAudienceAnswer.setVisibility(View.VISIBLE);
        } else if (value4.equalsIgnoreCase(correctanswer)) {
            imageViewAudienceAnswer.setBackgroundResource(R.drawable.cgraph);
            imageViewAudienceAnswer.setVisibility(View.VISIBLE);
        } else if (value5.equalsIgnoreCase(correctanswer)) {
            imageViewAudienceAnswer.setBackgroundResource(R.drawable.dgraph);
            imageViewAudienceAnswer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * lets the image view disappear
     */
    private void disapperImage() {
        imageViewAudienceAnswer.setVisibility(View.INVISIBLE);
    }

    public void onSkipClicked(View view) {
        Button b = findViewById(R.id.buttonflip);
        b.setBackgroundResource(R.drawable.jokerrevertx);
        b.setEnabled(false);
        joker4 = true;

        read();
    }

    /**
     * a public array list where 16 numbers between 0 and 27 are randomly displayed
     */
    private ArrayList<Integer> arrayList = new ArrayList<>(16);

    /**
     * to fill the arraylist this method is needed. it will only add a new random number when ths number isn't already in the list - so we will not have any duplicates
     */
    public void fillermehtod() {
        while (arrayList.size() < 16) {
            int x = newNumber();
            if (arrayList.contains(x)) {
                continue;
            }
            arrayList.add(x);
        }
        System.out.println(arrayList);
    }

    public int generateNumber() {
        return arrayList.get(elementOfArraylist);
    }

    //generates random number duplicates possible
    public static int newNumber() {
        Random rand = new Random();
        return rand.nextInt(27);
    }

    public void disableButtons() {
        buttonOptionA.setEnabled(false);
        buttonOptionB.setEnabled(false);
        buttonOptionC.setEnabled(false);
        buttonOptionD.setEnabled(false);
        buttonFiftyFifty.setEnabled(false);
        buttonPhoneSomeone.setEnabled(false);
        buttonAskAudience.setEnabled(false);
        buttonSkipQuestion.setEnabled(false);
    }

    /**
     * this function will use the external library jdom After generating a new SAX builder it will read out the input of the xml files
     * and displays them into the layouts provided
     */
    public void read() {
        intForNextQuestion = generateNumber();
        elementOfArraylist++;
        AssetManager assets = getAssets();

        try {
            if (getCurrentLocale().equals("en")) {
                InputStream in = assets.open("questionfile_en.xml");
                readXML(in);
            } else if (getCurrentLocale().equals("de")) {
                InputStream in = assets.open("questionfile_de.xml");
               readXML(in);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void readXML(InputStream in) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(in);
            Element rootNode = document.getRootElement();

            List<Element> list = rootNode.getChildren("Question");
            Element node = list.get(intForNextQuestion);

            buttonQuestion.setText(node.getChildText("question"));
            buttonOptionA.setText(node.getChildText("optiona"));
            buttonOptionB.setText(node.getChildText("optionb"));
            buttonOptionC.setText(node.getChildText("optionc"));
            buttonOptionD.setText(node.getChildText("optiond"));

            correctanswer = (node.getChildText("correctanswer"));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void whatWasTheCorrectAwnser() {
        String optionAWasRight = buttonOptionA.getText().toString();
        String optionBWasRight = buttonOptionB.getText().toString();
        String optionCWasRight = buttonOptionC.getText().toString();
        String optionDWasRight = buttonOptionD.getText().toString();

        if (optionAWasRight.equalsIgnoreCase(correctanswer)) {
            Button b1 = findViewById(R.id.boptiona);
            b1.setBackgroundResource(R.drawable.buttonanswerright);
        } else if (optionBWasRight.equalsIgnoreCase(correctanswer)) {
            Button b2 = findViewById(R.id.boptionb);
            b2.setBackgroundResource(R.drawable.buttonanswerright);
        } else if (optionCWasRight.equalsIgnoreCase(correctanswer)) {
            Button b3 = findViewById(R.id.boptionc);
            b3.setBackgroundResource(R.drawable.buttonanswerright);
        } else if (optionDWasRight.equalsIgnoreCase(correctanswer)) {
            Button b4 = findViewById(R.id.boptiond);
            b4.setBackgroundResource(R.drawable.buttonanswerright);
        }
    }


    /**
     * logic for every answer button
     * @param button to make sure which button is clicked
     */
    private void onAnswerButtonClicked(Button button) {
        countdownMainTimer.cancel();
        clockTickingAudio.pause();
        String value2 = button.getText().toString();
        disableButtons();

        if (value2.equalsIgnoreCase(correctanswer) && correctAnswerCounter != 14) {
            button.setBackgroundResource(R.drawable.buttonanswerright);
            buttonNext.setVisibility(View.VISIBLE);
            correctAnswerCounter++;
            clap();
        } else if (value2.equalsIgnoreCase(correctanswer) && correctAnswerCounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionaire.this, MillionaireEndScreen.class);
            startActivityForResult(activityStart, 1);
            finish();
        } else {
            button.setBackgroundResource(R.drawable.buttonawnserwrong);
            countdownWrongQuestion = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    Intent activityStart = new Intent(WhoWantsToBeAMillionaire.this, Endscreen.class);
                    startActivityForResult(activityStart, 1);
                    finish();
                }
            };
            countdownWrongQuestion.start();
            whatWasTheCorrectAwnser();
        }

    }

    public void onOptionAClicked(View view) {
        onAnswerButtonClicked(buttonOptionA);
    }

    public void onOptionBClicked(View view) {
        onAnswerButtonClicked(buttonOptionB);
    }

    public void onOptionCClicked(View view) {
        onAnswerButtonClicked(buttonOptionC);
    }

    public void onOptionDClicked(View view) {
        onAnswerButtonClicked(buttonOptionD);
    }

    /**
     * this button will lead to the next question
     * @param view sets visibility of the button
     */
    public void nextPlayClicked(View view) {
        read();
        countdownMainTimer.start();
        if (Options.isMusicOn()) {
            clockTickingAudio.start();
        }

        buttonNext.setVisibility(View.INVISIBLE);

        disapperImage();
        buttonOptionA.setEnabled(true);
        buttonOptionB.setEnabled(true);
        buttonOptionC.setEnabled(true);
        buttonOptionD.setEnabled(true);
        if (!joker1) {
            buttonFiftyFifty.setEnabled(true);
        } else {
            buttonFiftyFifty.setEnabled(false);
        }
        if (!joker2) {
            buttonPhoneSomeone.setEnabled(true);
        } else {
            buttonPhoneSomeone.setEnabled(false);
        }
        if (!joker3) {
            buttonAskAudience.setEnabled(true);
        } else {
            buttonAskAudience.setEnabled(false);
        }
        if (!joker4) {
            buttonSkipQuestion.setEnabled(true);
        } else {
            buttonSkipQuestion.setEnabled(false);
        }

        Button b2 = findViewById(R.id.boptiona);
        b2.setBackgroundResource(R.drawable.buttonanswer);
        Button b3 = findViewById(R.id.boptionb);
        b3.setBackgroundResource(R.drawable.buttonanswer);
        Button b4 = findViewById(R.id.boptionc);
        b4.setBackgroundResource(R.drawable.buttonanswer);
        Button b5 = findViewById(R.id.boptiond);
        b5.setBackgroundResource(R.drawable.buttonanswer);

        adapter.setCurrentPosition(14 - correctAnswerCounter);
    }
}
