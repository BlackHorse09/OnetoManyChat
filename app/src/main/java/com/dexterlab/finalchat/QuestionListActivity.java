package com.dexterlab.finalchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    DatabaseReference databaseQuestion;
    ListView listView;
    String questionOriginal;
    List<Question> listOfQuestion;


//    //From here
//    ArrayList<Question> questions = new ArrayList<>();
//    ArrayAdapter adapter1;
//    SearchView sv;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        listView = (ListView) findViewById(R.id.listView);
        databaseQuestion = FirebaseDatabase.getInstance().getReference().child("Post");

        listOfQuestion = new ArrayList<>();

//        //
//        sv = (SearchView) findViewById(R.id.searchView);
//
//        adapter1 = new ArrayAdapter<>(QuestionListActivity.this,R.layout.list_item,listOfQuestion);
//        listView.setAdapter(adapter1);
//
//
//        final List<Question> filteredOutput = new ArrayList<>();
//        final List<Question> output = new ArrayList<>();
//
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if (sv!=null) {
//                    for (Question item:output) {
//                        if (item.getQuestion().toLowerCase().startsWith(s.toLowerCase()));
//                        filteredOutput.add(item);
//                    }
//                }
//
//                questionList adapter1 = new questionList(QuestionListActivity.this,listOfQuestion);
//                listView.setAdapter(adapter1);
//                return false;
//            }
//        });
//
//
//        //

        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfQuestion.clear();
                for (DataSnapshot questionSnap : dataSnapshot.getChildren()) {
                    Question question = questionSnap.getValue(Question.class);
                    listOfQuestion.add(question);
                }

                questionList adapter = new questionList(QuestionListActivity.this,listOfQuestion);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Question question = listOfQuestion.get(i);
                Intent intent = new Intent(QuestionListActivity.this, AnswerActivity.class);
                intent.putExtra("question",question.getQuestion());
                intent.putExtra("answer",question.getAnswer());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Question question = listOfQuestion.get(i);
                questionOriginal = question.getQuestion().toString();
                showUpdateDialog(question.getId(),question.getAnswer());
                return true;
            }
        });

    }

    private void showUpdateDialog (final String questionId, String answer){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_answer, null);
        dialogBuilder.setView(dialogView);

        final EditText Answer = (EditText) dialogView.findViewById(R.id.editAnswer);
        final EditText QuestioN = (EditText) dialogView.findViewById(R.id.editQuestion);
        QuestioN.setText(questionOriginal);
        final Button Update = (Button) dialogView.findViewById(R.id.update);

        dialogBuilder.setTitle("Updating Answer");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newAnswer = Answer.getText().toString();
                String question = QuestioN.getText().toString();

                if (TextUtils.isEmpty(newAnswer)){
                    Answer.setError("Please enter the answer");
                    return;
                }

                updateAnswer(questionId,question,newAnswer);
                alertDialog.dismiss();

            }
        });


    }

    private boolean updateAnswer(String id, String Oldquestion, String answer) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post").child(id);
        Question question1 = new Question(id,Oldquestion,answer);
        databaseReference.setValue(question1);
        Toast.makeText(getApplicationContext(),"Done successfully",Toast.LENGTH_SHORT).show();
        return true;
    }
}
