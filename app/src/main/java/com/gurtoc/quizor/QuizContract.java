package com.gurtoc.quizor;

import android.provider.BaseColumns;

public class QuizContract {

    private QuizContract() {
    }

    public static class QuestionTabele implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "pytania";
        public static final String COLUMN_OPCJA1 = "opcja1";
        public static final String COLUMN_OPCJA2 = "opcja2";
        public static final String COLUMN_OPCJA3 = "opcja3";
        public static final String COLUMN_ODPOWIEDZ = "odpowiedz";

    }
}
