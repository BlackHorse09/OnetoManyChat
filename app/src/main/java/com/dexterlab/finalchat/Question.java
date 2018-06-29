package com.dexterlab.finalchat;

public class Question {
    String id;
    String question;
    String answer;

    public Question() {

    }

    public Question(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
