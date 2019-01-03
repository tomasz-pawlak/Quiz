package com.gurtoc.quizor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gurtoc.quizor.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MojaBazaPytan.db";
    private static final int DATABASE_VERSION = 5;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuestionTabele.TABLE_NAME + " (" +
                QuestionTabele._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                QuestionTabele.COLUMN_QUESTION + " TEXT, " +
                QuestionTabele.COLUMN_OPCJA1 + " TEXT, " +
                QuestionTabele.COLUMN_OPCJA2 + " TEXT, " +
                QuestionTabele.COLUMN_OPCJA3 + " TEXT, " +
                QuestionTabele.COLUMN_ODPOWIEDZ + " INTEGER " + ")";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);

        fillQuestionTable();

    }



    private void fillQuestionTable() {
        Question q1 = new Question("Najwiekszy szczyt Polski to:", "Rysy","Sniezka","Lysa Gora",1);
        addQuestion(q1);
        Question q2 = new Question("Elon Musk jest właścicielem marki:", "Volvo","Tesla","Skoda",2);
        addQuestion(q2);
        Question q3 = new Question("Jedyne zwycięskie polskie powstanie to: ", "Sląskie","Warszawskie","Wielkopolskie",3);
        addQuestion(q3);
        Question q4 = new Question("Który produkt Kinder jest zakazany w USA?", "Jajko-niespodzianka","Bueno","Nutella",1);
        addQuestion(q4);
        Question q5 = new Question("Jak nazywał się najsłynniejszy polski karateka?", "Bruce Lee","Chuck Norris","Franek Kimono",3);
        addQuestion(q5);
        Question q6 = new Question("Jaka powieść otrzymała nagrodę Booker w 2017r.?", "Lincoln in the Bardo","Sprzedawczyk ","Krótka historia siedmiu zabójstw",1);
        addQuestion(q6);
        Question q7 = new Question("Najdalej wysunięte miasto na północ to:", "Londyn","Warszawa ","Oslo",3);
        addQuestion(q7);
        Question q8 = new Question("W którym roku rozpoczeła się II Wojna Swiatowa", "1918","1939 ","1987",2);
        addQuestion(q8);
        Question q9 = new Question("Główna postać sagi Wiedzmin miała na imię:", "Geralt","Zoltan ","Jaskier",1);
        addQuestion(q9);
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ QuestionTabele.TABLE_NAME);
        onCreate(db);
    }



    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionTabele.TABLE_NAME,  null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setPytanie(cursor.getString(cursor.getColumnIndex(QuestionTabele.COLUMN_QUESTION)));
                question.setOpcja1(cursor.getString(cursor.getColumnIndex(QuestionTabele.COLUMN_OPCJA1)));
                question.setOpcja2(cursor.getString(cursor.getColumnIndex(QuestionTabele.COLUMN_OPCJA2)));
                question.setOpcja3(cursor.getString(cursor.getColumnIndex(QuestionTabele.COLUMN_OPCJA3)));
                question.setOdpowiedz(cursor.getInt(cursor.getColumnIndex(QuestionTabele.COLUMN_ODPOWIEDZ)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questionList;
    }
}
