package com.gurtoc.quizor;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Collections;
import java.util.List;


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

    private ColorStateList textColorDefRb;

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

        textColorDefRb = radioButton1.getTextColors();

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
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        finish();
    }

    private void checkAnswer() {
        odpowiedz = true;

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
