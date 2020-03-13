package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WhoWantsToBeAMillionare extends Activity {

    CountDownTimer generalcdt, cdt1, cdt2, cdt3, countDownTimer;
    Button time;
    ImageView graph;

    String correctanswer;
    //LinearLayout li;
    int j = 0;
    int correctawnsercounter = 0;

    boolean joker1, joker2, joker3, joker4 = false;

    Button b1, b2, b3, b4, b5, playnext, j1, j2, j3, j4;
    TextView resulttextview;
    int i = 0;

    MediaPlayer mpaudio, mpaudio1, mpaudio2;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.whowantstobeamillionare);

        Button button1 = (Button) findViewById(R.id.buttonBack);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MainActivity.class);
                startActivityForResult(activityStart, 1);
            }
        });
        Button button2 = (Button) findViewById(R.id.buttonSettings);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Options.class);
                startActivityForResult(activityStart, 1);
            }
        });

        mpaudio = MediaPlayer.create(this, R.raw.ticktock);
        mpaudio.start();

        //mpaudio1= MediaPlayer.create(this,R.drawable.clock_ti);
        fillermehtod();

        populatelistview();


        graph = (ImageView) findViewById(R.id.graph);
        time = (Button) findViewById(R.id.buttonClock);
        mpaudio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mpaudio.start();
            }
        });

        b1 = (Button) findViewById(R.id.bquestion);
        b2 = (Button) findViewById(R.id.boptiona);
        b3 = (Button) findViewById(R.id.boptionb);
        b4 = (Button) findViewById(R.id.boptionc);
        b5 = (Button) findViewById(R.id.boptiond);
        playnext = (Button) findViewById(R.id.buttonNext);
        j1 = findViewById(R.id.buttonfiftyfifty);
        j2 = (Button) findViewById(R.id.buttonPhone);
        j3 = (Button) findViewById(R.id.buttonaudience);
        j4 = (Button) findViewById(R.id.buttonflip);

        //li=(LinearLayout)findViewById(R.id.interior4);
        //resulttextview = (TextView) findViewById(R.id.textresult);
        read();
    }

    private void setupCountDown() {
        generalcdt = new CountDownTimer(50000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);
                int s = (int) millisUntilFinished / 1000;
                System.out.println("This is the time" + s);

                if (s == 10) {
                    clap();
                }

            }

            public void onFinish() {
                //mpaudio1.pause();
                setContentView(R.layout.endscreen);
                Intent intent = new Intent(WhoWantsToBeAMillionare.this, EndscreenTimeout.class);
                startActivity(intent);
                WhoWantsToBeAMillionare.this.finish();
            }
        };
    }

    public void clap() {
        //mpaudio2.pause();
        //mpaudio1.start();
    }


    private void populatelistview() {

        String[] myitems = {"15  £ 1 Million", "14    £ 500.000", "13    £ 125.000", "12    £ 32.000", "11    £ 32.000",
                "10    £ 16.000", "9     £ 8.000", "8     £ 4.000", "7     £ 2.000", "6     £ 1.000", "5     £ 500",
                "4     £ 300", "3     £ 200", "2     £ 100", "1     £ 50"};
        //  ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myitems);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view, myitems);
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setAdapter(adapter);

    }


    public void onFiftyfifty(View view) {
        Button b = (Button) findViewById(R.id.buttonfiftyfifty);
        b.setBackgroundResource(R.drawable.custom_cancelfiftyfifty);
        b.setEnabled(false);
        joker1 = true;

        String value2 = b2.getText().toString();
        String value3 = b3.getText().toString();
        String value4 = b4.getText().toString();
        String value5 = b5.getText().toString();

        if (value2.equalsIgnoreCase(correctanswer)) {
            b3.setText("");
            b4.setText("");
        } else if (value3.equalsIgnoreCase(correctanswer)) {
            b2.setText("");
            b5.setText("");
        } else if (value4.equalsIgnoreCase(correctanswer)) {
            b2.setText("");
            b5.setText("");
        } else if (value5.equalsIgnoreCase(correctanswer)) {
            b3.setText("");
            b4.setText("");
        }

    }

    public void onPhoneAfriend(View view) {
        Button b = (Button) findViewById(R.id.buttonPhone);
        b.setBackgroundResource(R.drawable.custom_cancelphoneafreind);
        b.setEnabled(false);
        joker2 = true;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);

        timeout();


    }

    private void timeout() {

        cdt3 = new CountDownTimer(50000, 1000) {
            // int s;

            public void onTick(long millisUntilFinished) {
                // time.setText("" + millisUntilFinished / 1000);
                // s=(int)millisUntilFinished/1000;
                // System.out.println("This is the time"+s);


            }

            public void onFinish() {

                cdt3.cancel();
            }
        };
    }

    public void onAudiencepoll(View view) {
        Button b = (Button) findViewById(R.id.buttonaudience);
        b.setBackgroundResource(R.drawable.custom_cancelaudiencepoll);
        b.setEnabled(false);
        joker3 = true;

        String value2 = b2.getText().toString();
        String value3 = b3.getText().toString();
        String value4 = b4.getText().toString();
        String value5 = b5.getText().toString();

        if (value2.equalsIgnoreCase(correctanswer)) {
            graph.setBackgroundResource(R.drawable.a);
            graph.setVisibility(View.VISIBLE);
            // li.setBackgroundResource(R.drawable.a);
        } else if (value3.equalsIgnoreCase(correctanswer)) {
            graph.setBackgroundResource(R.drawable.b);
            graph.setVisibility(View.VISIBLE);
            // li.setBackgroundResource(R.drawable.b);
        } else if (value4.equalsIgnoreCase(correctanswer)) {
            graph.setBackgroundResource(R.drawable.c);
            graph.setVisibility(View.VISIBLE);
            //li.setBackgroundResource(R.drawable.c);
        } else if (value5.equalsIgnoreCase(correctanswer)) {
            graph.setBackgroundResource(R.drawable.d);
            graph.setVisibility(View.VISIBLE);
            // li.setBackgroundResource(R.drawable.d);
        }
    }

    private void disapperaimage() {
        graph.setVisibility(View.INVISIBLE);
        /*cdt2 = new CountDownTimer(5000, 1000)
        {
            // int s;

            public void onTick(long millisUntilFinished)
            {
                // time.setText("" + millisUntilFinished / 1000);
                // s=(int)millisUntilFinished/1000;
                // System.out.println("This is the time"+s);


            }

            // @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onFinish()
            {


                cdt2.cancel();

            }
        };

         */
    }

    public void onFlip(View view) {
        Button b = (Button) findViewById(R.id.buttonflip);
        b.setBackgroundResource(R.drawable.custom_cancelflip);
        b.setEnabled(false);
        joker4 = true;

        read();
    }


    public void onCompletion(MediaPlayer mediaPlayer) {
        //mpaudio2= MediaPlayer.create(this,R.drawable.);
        //mpaudio2.start();
    }

    private ArrayList<Integer> arrayList = new ArrayList<Integer>(15);

    public void fillermehtod() {
        while (arrayList.size() < 23) {
            int x = newNumber();
            if (arrayList.contains(x)) {
                continue;
            }
            arrayList.add(x);
        }
    }


    //
    public int generateNumber() {
        int element = arrayList.get(i);
        return element;
    }


    //generates random number duplicates possible
    public static int newNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(23);
        return randomNumber;
    }

    public void disableButtons() {
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        j1.setEnabled(false);
        j2.setEnabled(false);
        j3.setEnabled(false);
        j4.setEnabled(false);
    }


    public void read() {
        j = generateNumber();
        i++;
        setupCountDown();
        generalcdt.start();

        SAXBuilder builder = new SAXBuilder();

        //File xmlFile = new File(R.xml.test);
        AssetManager assets = getAssets();

        //  File xmlFile = new File(getFilesDir(),"assets/test.xml");

        try {
            InputStream in = assets.open("question.xml");

            Document document = (Document) builder.build(in);
            Element rootNode = document.getRootElement();
            // List list = rootNode.getChildren("staff");

            List<Element> list = rootNode.getChildren("Question");

            // for (int i = 0; i < list.size(); i++) {

            //Element node = (Element) list.get(i);
            Element node = (Element) list.get(j);

            System.out.println("Question : " + node.getChildText("question"));
            b1.setText(node.getChildText("question"));

            System.out.println("option A : " + node.getChildText("optiona"));
            b2.setText(node.getChildText("optiona"));

            System.out.println("option B : " + node.getChildText("optionb"));
            b3.setText(node.getChildText("optionb"));

            System.out.println("option C : " + node.getChildText("optionc"));
            b4.setText(node.getChildText("optionc"));

            System.out.println("option D : " + node.getChildText("optiond"));
            b5.setText(node.getChildText("optiond"));

            //System.out.println("Correct Answer : " + node.getChildText("correctanswer"));
            correctanswer = (node.getChildText("correctanswer")).toString();

            System.out.println(correctanswer);
            //  }


        } catch (Exception io) {
            System.out.println(io.getMessage());
        }


    }

    public void onOptionAClicked(View view) {
        generalcdt.cancel();
        //mpaudio2.pause();
        String value2 = b2.getText().toString();
        disableButtons();


        if (value2.equalsIgnoreCase(correctanswer) && correctawnsercounter != 14) {
            Button b2 = (Button) findViewById(R.id.boptiona);
            b2.setBackgroundResource(R.drawable.buttongreen);
            playnext.setVisibility(View.VISIBLE);
            correctawnsercounter++;
        } else if (value2.equalsIgnoreCase(correctanswer) && correctawnsercounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
        } else {
            Button b2 = (Button) findViewById(R.id.boptiona);
            b2.setBackgroundResource(R.drawable.buttonorange);

            countDownTimer = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
                    startActivityForResult(activityStart, 1);
                }
            };
        }


    }

    public void onOptionBClicked(View view) {
        generalcdt.cancel();
        //mpaudio2.pause();
        String value3 = b3.getText().toString();
        disableButtons();


        if (value3.equalsIgnoreCase(correctanswer) && correctawnsercounter != 14) {
            Button b3 = (Button) findViewById(R.id.boptionb);
            b3.setBackgroundResource(R.drawable.buttongreen);
            playnext.setVisibility(View.VISIBLE);
            correctawnsercounter++;
        } else if (value3.equalsIgnoreCase(correctanswer) && correctawnsercounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
        } else {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
            startActivityForResult(activityStart, 1);
        }
    }

    public void onOptionCClicked(View view) {
        generalcdt.cancel();
        //mpaudio2.pause();
        String value4 = b4.getText().toString();
        disableButtons();

        if (value4.equalsIgnoreCase(correctanswer) && correctawnsercounter != 14) {
            Button b4 = (Button) findViewById(R.id.boptionc);
            b4.setBackgroundResource(R.drawable.buttongreen);
            playnext.setVisibility(View.VISIBLE);
            correctawnsercounter++;
        } else if (value4.equalsIgnoreCase(correctanswer) && correctawnsercounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
        } else {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
            startActivityForResult(activityStart, 1);
        }
    }

    public void onOptionDClicked(View view) {
        generalcdt.cancel();
        //mpaudio2.pause();
        String value5 = b5.getText().toString();
        disableButtons();

        if (value5.equalsIgnoreCase(correctanswer) && correctawnsercounter != 14) {
            Button b2 = (Button) findViewById(R.id.boptiond);
            b2.setBackgroundResource(R.drawable.buttongreen);
            playnext.setVisibility(View.VISIBLE);
            correctawnsercounter++;
        } else if (value5.equalsIgnoreCase(correctanswer) && correctawnsercounter == 14) {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, MillionareEndsceen.class);
            startActivityForResult(activityStart, 1);
        } else {
            Intent activityStart = new Intent(WhoWantsToBeAMillionare.this, Endscreen.class);
            startActivityForResult(activityStart, 1);
        }

    }

    public void nextPlayClicked(View view) {
        read();
        //mpaudio2.start();
        playnext.setVisibility(View.INVISIBLE);

        disapperaimage();
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        if (!joker1) {
            j1.setEnabled(true);
        } else {
            j1.setEnabled(false);
        }
        if (!joker2) {
            j2.setEnabled(true);
        } else {
            j2.setEnabled(false);
        }
        if (!joker3) {
            j3.setEnabled(true);
        } else {
            j3.setEnabled(false);
        }
        if (!joker4) {
            j4.setEnabled(true);
        } else {
            j4.setEnabled(false);
        }

        Button b2 = (Button) findViewById(R.id.boptiona);
        b2.setBackgroundResource(R.drawable.buttonblue);
        Button b3 = (Button) findViewById(R.id.boptionb);
        b3.setBackgroundResource(R.drawable.buttonblue);
        Button b4 = (Button) findViewById(R.id.boptionc);
        b4.setBackgroundResource(R.drawable.buttonblue);
        Button b5 = (Button) findViewById(R.id.boptiond);
        b5.setBackgroundResource(R.drawable.buttonblue);
        afterquestion();
        cdt1.start();
    }

    public void afterquestion() {

        cdt1 = new CountDownTimer(5000, 1000) {
            // int s;

            public void onTick(long millisUntilFinished) {
                // time.setText("" + millisUntilFinished / 1000);
                // s=(int)millisUntilFinished/1000;
                // System.out.println("This is the time"+s);


            }

            public void onFinish() {

                cdt1.cancel();
            }
        };
    }

}
