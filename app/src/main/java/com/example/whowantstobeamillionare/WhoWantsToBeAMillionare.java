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
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WhoWantsToBeAMillionare extends Activity {

    Options options = new Options();

    CountDownTimer countdownMainTimer, countdownWrongQuestion;
    Button time;
    ImageView imageViewAudienceAnswer;

    String correctanswer;
    int intForNextQuestion = 0;

    int correctAnswerCounter = 0;
    boolean joker1, joker2, joker3, joker4 = false;

    Button buttonQuestion, buttonOptionA, buttonOptionB, buttonOptionC, buttonOptionD, buttonNext, buttonFiftyFifty, buttonPhoneSomeone, buttonAskAudience, buttonSkipQuestion;
    int elementOfArraylist = 0;
    MediaPlayer clockTickingAudio, applauseAudio;
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
                Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MainActivity.class);
                startActivityForResult(activityStart, 1);
                clockTickingAudio.reset();
                clockTickingAudio.release();
                applauseAudio.reset();
                applauseAudio.release();
                finish();
            }
        });
        Button button2 = findViewById(R.id.buttonSettings);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Options.class);
                startActivityForResult(activityStart, 1);
                finish();
            }
        });

        clockTickingAudio = MediaPlayer.create(this, R.raw.ticktock);
        if (options.getBooleanValue()) {
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
                Intent intent = new Intent(WhoWantsToBeAMillionare.this, EndscreenTimeout.class);
                startActivity(intent);
                countdownMainTimer.cancel();
                finish();
            }
        };
    }

    public void clap() {
        clockTickingAudio.pause();
        if (options.getBooleanValue()) {
            applauseAudio.start();
        }
    }

    private void populatelistview() {
        String[] myitems = {"15   1 Million", "14     500.000", "13     125.000", "12     32.000", "11     32.000",
                "10     16.000", "9      8.000", "8      4.000", "7      2.000", "6      1.000", "5      500",
                "4      300", "3      200", "2      100", "1      50"};
        adapter = new MySimpleArrayAdapter(this, myitems) {};
        ListView list = findViewById(R.id.listViewMain);
        list.setAdapter(adapter);
    }

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

    public void onPhoneSomeoneClicked(View view) {
        Button b = findViewById(R.id.buttonPhone);
        b.setBackgroundResource(R.drawable.jokerphonex);
        b.setEnabled(false);
        joker2 = true;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }

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

    private ArrayList<Integer> arrayList = new ArrayList<>(16);

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

    public void read() {
        intForNextQuestion = generateNumber();
        elementOfArraylist++;
        SAXBuilder builder = new SAXBuilder();
        AssetManager assets = getAssets();

        try {
            if (getCurrentLocale().equals("en")) {
                InputStream in = assets.open("questionfile_en.xml");
                Document document = builder.build(in);
                Element rootNode = document.getRootElement();
                List<Element> list = rootNode.getChildren("Question");
                Element node = list.get(intForNextQuestion);
                System.out.println("Question : " + node.getChildText("question"));
                buttonQuestion.setText(node.getChildText("question"));
                System.out.println("option A : " + node.getChildText("optiona"));
                buttonOptionA.setText(node.getChildText("optiona"));
                System.out.println("option B : " + node.getChildText("optionb"));
                buttonOptionB.setText(node.getChildText("optionb"));
                System.out.println("option C : " + node.getChildText("optionc"));
                buttonOptionC.setText(node.getChildText("optionc"));
                System.out.println("option D : " + node.getChildText("optiond"));
                buttonOptionD.setText(node.getChildText("optiond"));
                correctanswer = (node.getChildText("correctanswer"));
                System.out.println(correctanswer);

            } else if (getCurrentLocale().equals("de")) {

                InputStream in = assets.open("questionfile_de.xml");
                Document document = builder.build(in);

                Element rootNode = document.getRootElement();

                List<Element> list = rootNode.getChildren("Question");

                Element node = list.get(intForNextQuestion);

                System.out.println("Question : " + node.getChildText("question"));
                buttonQuestion.setText(node.getChildText("question"));

                System.out.println("option A : " + node.getChildText("optiona"));
                buttonOptionA.setText(node.getChildText("optiona"));

                System.out.println("option B : " + node.getChildText("optionb"));
                buttonOptionB.setText(node.getChildText("optionb"));

                System.out.println("option C : " + node.getChildText("optionc"));
                buttonOptionC.setText(node.getChildText("optionc"));

                System.out.println("option D : " + node.getChildText("optiond"));
                buttonOptionD.setText(node.getChildText("optiond"));

                correctanswer = (node.getChildText("correctanswer"));

                System.out.println(correctanswer);
            }

        } catch (Exception io) {
            System.out.println(io.getMessage());
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

    public void onOptionAClicked(View view) {
        countdownMainTimer.cancel();
        clockTickingAudio.pause();
        String value2 = buttonOptionA.getText().toString();
        disableButtons();

        if (value2.equalsIgnoreCase(correctanswer) && correctAnswerCounter != 14) {
            Button b2 = findViewById(R.id.boptiona);
            b2.setBackgroundResource(R.drawable.buttonanswerright);
            buttonNext.setVisibility(View.VISIBLE);
            correctAnswerCounter++;
            clap();
        } else if (value2.equalsIgnoreCase(correctanswer) && correctAnswerCounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
            finish();
        } else {
            Button b2 = findViewById(R.id.boptiona);
            b2.setBackgroundResource(R.drawable.buttonawnserwrong);


            countdownWrongQuestion = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
                    startActivityForResult(activityStart, 1);
                    finish();
                }
            };
            countdownWrongQuestion.start();
            whatWasTheCorrectAwnser();
        }


    }

    public void onOptionBClicked(View view) {
        countdownMainTimer.cancel();
        clockTickingAudio.pause();
        String value3 = buttonOptionB.getText().toString();
        disableButtons();


        if (value3.equalsIgnoreCase(correctanswer) && correctAnswerCounter != 14) {
            Button b3 = findViewById(R.id.boptionb);
            b3.setBackgroundResource(R.drawable.buttonanswerright);
            buttonNext.setVisibility(View.VISIBLE);
            correctAnswerCounter++;
            clap();
        } else if (value3.equalsIgnoreCase(correctanswer) && correctAnswerCounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
            finish();
        } else {
            Button b2 = findViewById(R.id.boptionb);
            b2.setBackgroundResource(R.drawable.buttonawnserwrong);


            countdownWrongQuestion = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
                    startActivityForResult(activityStart, 1);
                    finish();
                }
            };
            countdownWrongQuestion.start();
            whatWasTheCorrectAwnser();
        }
    }

    public void onOptionCClicked(View view) {
        countdownMainTimer.cancel();
        clockTickingAudio.pause();

        String value4 = buttonOptionC.getText().toString();
        disableButtons();

        if (value4.equalsIgnoreCase(correctanswer) && correctAnswerCounter != 14) {
            Button b4 = findViewById(R.id.boptionc);
            b4.setBackgroundResource(R.drawable.buttonanswerright);
            buttonNext.setVisibility(View.VISIBLE);
            correctAnswerCounter++;
            clap();
        } else if (value4.equalsIgnoreCase(correctanswer) && correctAnswerCounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
            finish();
        } else {
            Button b2 = findViewById(R.id.boptionc);
            b2.setBackgroundResource(R.drawable.buttonawnserwrong);


            countdownWrongQuestion = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
                    startActivityForResult(activityStart, 1);
                    finish();
                }
            };
            countdownWrongQuestion.start();
            whatWasTheCorrectAwnser();
        }
    }

    public void onOptionDClicked(View view) {
        countdownMainTimer.cancel();
        clockTickingAudio.pause();
        String value5 = buttonOptionD.getText().toString();
        disableButtons();

        if (value5.equalsIgnoreCase(correctanswer) && correctAnswerCounter != 14) {
            Button b2 = findViewById(R.id.boptiond);
            b2.setBackgroundResource(R.drawable.buttonanswerright);
            buttonNext.setVisibility(View.VISIBLE);
            correctAnswerCounter++;
            clap();
        } else if (value5.equalsIgnoreCase(correctanswer) && correctAnswerCounter == 14) {

            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
            finish();
        } else {
            Button b = findViewById(R.id.boptiond);
            b.setBackgroundResource(R.drawable.buttonawnserwrong);


            countdownWrongQuestion = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
                    startActivityForResult(activityStart, 1);
                    finish();
                }
            };
            countdownWrongQuestion.start();
            whatWasTheCorrectAwnser();
        }

    }

    public void nextPlayClicked(View view) {
        read();
        countdownMainTimer.start();
        if (options.getBooleanValue()) {
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
