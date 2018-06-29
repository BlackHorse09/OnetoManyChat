package com.dexterlab.finalchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    TextView textAnswer,textQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        textAnswer = (TextView) findViewById(R.id.textAnswer);
        textQuestion = (TextView) findViewById(R.id.textQuestion);

        String answer = getIntent().getStringExtra("answer");
        String question = getIntent().getStringExtra("question");

        textQuestion.setText(question);
        textAnswer.setText(answer);

    }
}
