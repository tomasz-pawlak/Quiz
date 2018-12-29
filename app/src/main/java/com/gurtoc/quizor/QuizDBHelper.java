package com.gurtoc.quizor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gurtoc.quizor.QuizContract.*;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MojaBazaPytan.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuestionTabele.TABLE_NAME + " (" +
                QuestionTabele._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                QuestionTabele.COLUMN_QUESTION + " TEXT, " +
                QuestionTabele.COLUMN_OPCJA1 + " TEXT, " +
                QuestionTabele.COLUMN_OPCJA2 + " TEXT, " +
                QuestionTabele.COLUMN_OPCJA3 + " TEXT, " +
                QuestionTabele.COLUMN_ODPOWIEDZ + " INTEGER " + " )";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();
    }

    private void fillQuestionTable() {
        Question q1 = new Question("Najwiekszy szczyt Polski to:", "Rysy","Sniezka","Lysa Gora",1);
        addQuestion(q1);
        Question q2 = new Question("Elon Musk jest właścicielem marki:", "Volvo","Tesla","Skoda",2);
        addQuestion(q2);
        Question q3 = new Question("Jedyne zwycięskie polskie powstanie to: ", "Sląskie","Warszawskie","Wielkopolskie",2);
        addQuestion(q3);
        Question q4 = new Question("Który produkt Kinder jest zakazany w USA?", "Jajko-niespodzianka","Bueno","Nutella",1);
        addQuestion(q4);
        Question q5 = new Question("Jak nazywał się najsłynniejszy polski karateka?", "Bruce Lee","Chuck Norris","Franek Kimono",3);
        addQuestion(q5);
    }

    private void addQuestion(Question question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionTabele.COLUMN_QUESTION, question.getPytanie());
        contentValues.put(QuestionTabele.COLUMN_OPCJA1, question.getOpcja1());
        contentValues.put(QuestionTabele.COLUMN_OPCJA2, question.getOpcja2());
        contentValues.put(QuestionTabele.COLUMN_OPCJA3,question.getOpcja3());
        contentValues.put(QuestionTabele.COLUMN_ODPOWIEDZ, question.getOdpowiedz());
        db.insert(QuestionTabele.TABLE_NAME, null,contentValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ QuestionTabele.TABLE_NAME);
        onCreate(db);
    }
}
