package com.example.bartek.badminton.Language;

public class LanguagePolski implements LanguageInterface{

    @Override
    public String getConfirmSetMessage() {
        return "Czy chesz zatwierdzić wynik tego seta?";
    }

    @Override
    public String getEndOfMatchMessage() {
        return "";
    }

    @Override
    public String getYesOption() {
        return "Tak";
    }

    @Override
    public String getNoOption() {
        return "Nie";
    }

    @Override
    public String getOkOption() {
        return "OK";
    }
}
