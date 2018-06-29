package com.dexterlab.finalchat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

public class questionList extends ArrayAdapter<Question> {
    private Activity context;
    private List<Question> listOfQuestion;

    public questionList (Activity context, List<Question> listOfQuestion) {
        super(context, R.layout.list_item,listOfQuestion);
        this.context = context;
        this.listOfQuestion = listOfQuestion;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item,null,true);
        TextView textView = (TextView) listViewItem.findViewById(R.id.textItem);

        Question question = listOfQuestion.get(position);
        textView.setText(question.getQuestion());


        return listViewItem;
    }
}
