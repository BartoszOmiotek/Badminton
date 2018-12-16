package com.example.bartek.badminton.Language;

public class LanguageEnglish implements LanguageInterface {
    @Override
    public String getConfirmSetMessage() {
        return "Do you want to confirm set result?";
    }

    @Override
    public String getEndOfMatchMessage() {
        return "The match is over.";
    }

    @Override
    public String getYesOption() {
        return "Yes";
    }

    @Override
    public String getNoOption() {
        return "No";
    }

    @Override
    public String getOkOption() {
        return "OK";
    }
}
