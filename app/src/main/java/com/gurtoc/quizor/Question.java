package com.gurtoc.quizor;

public class Question {

    private String pytanie;
    private String opcja1;
    private String opcja2;
    private String opcja3;
    private int odpowiedz;


    public Question(String pytanie, String opcja1, String opcja2, String opcja3, int odpowiedz) {
        this.pytanie = pytanie;
        this.opcja1 = opcja1;
        this.opcja2 = opcja2;
        this.opcja3 = opcja3;
        this.odpowiedz = odpowiedz;
    }

    public Question(){}



    public String getPytanie() {
        return pytanie;
    }

    public void setPytanie(String pytanie) {
        this.pytanie = pytanie;
    }

    public String getOpcja1() {
        return opcja1;
    }

    public void setOpcja1(String opcja1) {
        this.opcja1 = opcja1;
    }

    public String getOpcja2() {
        return opcja2;
    }

    public void setOpcja2(String opcja2) {
        this.opcja2 = opcja2;
    }

    public String getOpcja3() {
        return opcja3;
    }

    public void setOpcja3(String opcja3) {
        this.opcja3 = opcja3;
    }

    public int getOdpowiedz() {
        return odpowiedz;
    }

    public void setOdpowiedz(int odpowiedz) {
        this.odpowiedz = odpowiedz;
    }
}
