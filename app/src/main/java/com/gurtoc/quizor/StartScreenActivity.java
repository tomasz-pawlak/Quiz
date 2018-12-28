package com.gurtoc.quizor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {

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

    }
    private void startQuiz(){
        Intent intent = new Intent(StartScreenActivity.this, QuizActivity.class);
        startActivity(intent);
    }
}

