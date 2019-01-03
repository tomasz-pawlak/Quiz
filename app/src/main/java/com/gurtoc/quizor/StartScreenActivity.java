package com.gurtoc.quizor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartScreenActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_QUIZ = 1;

    private TextView textPokazNajwiekszyWynik;

    private int najwiekszyWynik;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAJLEPSZE_PKT = "najlepszePKT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        Button buttonStartButton = findViewById(R.id.button_rozpocznij_quiz);
        buttonStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

        textPokazNajwiekszyWynik = findViewById(R.id.text_highscore);
        loadHighscore();

    }

    private void startQuiz() {
        Intent intent = new Intent(StartScreenActivity.this, QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int wynik = data.getIntExtra(QuizActivity.DODATKOWE_PUNKTY, 0);
                if (wynik > najwiekszyWynik) {
                    updateHighscore(wynik);
                }
            }
        }
    }

    private void updateHighscore(int highscoreNew) {
        najwiekszyWynik = highscoreNew;
        textPokazNajwiekszyWynik.setText("Najlepszy wynik: " + najwiekszyWynik);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE); //utrzymanie danych po wlaczdeniu i wylaczeniu app
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NAJLEPSZE_PKT, najwiekszyWynik);
        editor.apply();
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        najwiekszyWynik = prefs.getInt(NAJLEPSZE_PKT, 0);
        textPokazNajwiekszyWynik.setText("Najlepszy wynik: " + najwiekszyWynik);
    }
}

