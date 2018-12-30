package com.gurtoc.quizor;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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



        QuizDBHelper quizDBHelper = new QuizDBHelper(this);

        questionList = quizDBHelper.getAllQuestions();

    }

}
