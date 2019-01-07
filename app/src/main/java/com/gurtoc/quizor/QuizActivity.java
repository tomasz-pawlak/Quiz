package com.gurtoc.quizor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class QuizActivity extends AppCompatActivity {

    private TextView textAktualnepunkty;
    private TextView textAktualnepytanie;
    private TextView textTimer;
    private TextView textQuestion;
    private RadioGroup radioGrp;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Button buttonPotwierdz;

    public static final String DODATKOWE_PUNKTY = "dodatkowePunkty";
    private static final long TIMER = 15000;


    private ColorStateList textColorDefRb;
    private ColorStateList textColorDefTimer;

    private CountDownTimer countDownTimer;
    private long pozostalyCzas;

    private int pytanieLicznik;
    private int pytanieOgolne;
    private Question pytanieAktualne;

    private int wynik;
    private boolean odpowiedz;


    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textAktualnepunkty = findViewById(R.id.text_aktualnepunkty);
        textAktualnepytanie = findViewById(R.id.text_aktualnepytanie);
        textTimer = findViewById(R.id.text_timer);
        textQuestion = findViewById(R.id.text_question);
        radioGrp = findViewById(R.id.radio_grp);
        radioButton1 = findViewById(R.id.radio_button1);
        radioButton2 = findViewById(R.id.radio_button2);
        radioButton3 = findViewById(R.id.radio_button3);
        buttonPotwierdz = findViewById(R.id.button_potwierdz);
        //textTimer = findViewById(R.id.text_timer);

        textColorDefRb = radioButton1.getTextColors();
        textColorDefTimer = textTimer.getTextColors();

        QuizDBHelper quizDBHelper = new QuizDBHelper(this);

        questionList = quizDBHelper.getAllQuestions();

        pytanieOgolne = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonPotwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!odpowiedz) {
                    if (radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Proszę zaznaczyć odpowiedź", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }


    private void showNextQuestion() {
        radioButton1.setTextColor(textColorDefRb);
        radioButton2.setTextColor(textColorDefRb);
        radioButton3.setTextColor(textColorDefRb);
        radioGrp.clearCheck();

        if (pytanieLicznik < pytanieOgolne) {
            pytanieAktualne = questionList.get(pytanieLicznik);

            textQuestion.setText(pytanieAktualne.getPytanie());
            radioButton1.setText(pytanieAktualne.getOpcja1());
            radioButton2.setText(pytanieAktualne.getOpcja2());
            radioButton3.setText(pytanieAktualne.getOpcja3());

            pytanieLicznik++;
            textAktualnepytanie.setText("Pytanie: " + pytanieLicznik + "/" + pytanieOgolne);
            odpowiedz = false;
            buttonPotwierdz.setText("Potwierdz");

            pozostalyCzas = TIMER;
            startTimer();
        } else {
            finishQuiz();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(pozostalyCzas, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pozostalyCzas = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                pozostalyCzas = 0;
                updateTimer();
                checkAnswer();
            }
        }.start();
    }

    private void updateTimer(){
        int minutes = (int) (pozostalyCzas / 1000) / 60;
        int seconds = (int) (pozostalyCzas / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textTimer.setText(timeFormatted);

        if (pozostalyCzas < 10000) {
            textTimer.setTextColor(Color.RED);
        } else {
            textTimer.setTextColor(textColorDefTimer);
        }
    }

    private void finishQuiz() {
        Intent intent = new Intent();
        intent.putExtra(DODATKOWE_PUNKTY , wynik);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void checkAnswer() {
        odpowiedz = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(radioGrp.getCheckedRadioButtonId());
        int answerNr = radioGrp.indexOfChild(rbSelected) + 1;

        if (answerNr == pytanieAktualne.getOdpowiedz()) {
            wynik++;
            textAktualnepunkty.setText("Wynik: " + wynik);
        }

        showSolution();
    }

    private void showSolution() {
        radioButton1.setTextColor(Color.RED);
        radioButton2.setTextColor(Color.RED);
        radioButton3.setTextColor(Color.RED);

        switch (pytanieAktualne.getOdpowiedz()) {
            case 1:
                radioButton1.setTextColor(Color.GREEN);
                textAktualnepytanie.setText("Odpowiedź 1  jest prawidłowa");
                break;
            case 2:
                radioButton2.setTextColor(Color.GREEN);
                textAktualnepytanie.setText("Odpowiedź 2  jest prawidłowa");
                break;
            case 3:
                radioButton3.setTextColor(Color.GREEN);
                textAktualnepytanie.setText("Odpowiedź 3  jest prawidłowa");
                break;

        }

        if (pytanieLicznik < pytanieOgolne) {
            buttonPotwierdz.setText("Następne");
        } else {
            buttonPotwierdz.setText("Koniec");
        }
    }

}
