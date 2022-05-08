package com.example.quizz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz.R;
import com.example.quizz.model.Question;
import com.example.quizz.model.QuestionBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private TextView mQuestionTextView;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mRemainingQuestionCount;
    private String mResult;
    private List<String> mQuestionResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionTextView = findViewById(R.id.game_textview_question);
        mAnswer1Button = findViewById(R.id.game_button_answer1);
        mAnswer2Button = findViewById(R.id.game_button_answer2);
        mAnswer3Button = findViewById(R.id.game_button_answer3);
        mAnswer4Button = findViewById(R.id.game_button_answer4);

        mAnswer1Button.setOnClickListener(this);
        mAnswer2Button.setOnClickListener(this);
        mAnswer3Button.setOnClickListener(this);
        mAnswer4Button.setOnClickListener(this);

        mQuestionBank = generateQuestions();
        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        mRemainingQuestionCount = mQuestionBank.getQuestionList().size();
        displayQuestion(mCurrentQuestion);
        mResult = "";
        mQuestionResponseList = new ArrayList();
    }

    @Override
    public void onClick(View view) {
        int index;

        if (view == mAnswer1Button) {
            index = 0;
        } else if (view == mAnswer2Button) {
            index = 1;
        } else if (view == mAnswer3Button) {
            index = 2;
        } else if (view == mAnswer4Button) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        mQuestionResponseList.add(getResponse(mCurrentQuestion, index));
        Toast.makeText(this, "Send !", Toast.LENGTH_SHORT).show();

        mRemainingQuestionCount--;
        if (mRemainingQuestionCount > 0) {
            mCurrentQuestion = mQuestionBank.getNextQuestion();
            displayQuestion(mCurrentQuestion);
        } else {
            String[][] places = {
                    {"le Septime", "Diner", "moins de 30€"},
                    {"les buttes-chaumont", "Boire un verre", "gratuit"},
                    {"la gallerie perrin", "Faire une sorie culturelle", "gratuit"},
                    {"le badaboum", "Faire la fête toute la nuit", "ILLIMITE !"}
            };

            for(int i = 0; i < places.length; i++) {
                    if(places[i][1].equals(mQuestionResponseList.get(0)) && places[i][2].equals(mQuestionResponseList.get(1))) {
                        mResult = places[i][0];
                    };
            }

            String message;
            if (mResult.equals("")) {
                message = "Aucun resultat trouvé";
            }
            else {
                message = "On a trouvé " + mResult + " pour vous !";

            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Resultat :")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, mResult);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private String getResponse(final Question question, int index) {
        return question.getChoiceList().get(index);
    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswer1Button.setText(question.getChoiceList().get(0));
        mAnswer2Button.setText(question.getChoiceList().get(1));
        mAnswer3Button.setText(question.getChoiceList().get(2));
        mAnswer4Button.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question(
                "Vous cherchez un lieu pour... ?",
                Arrays.asList(
                        "Diner",
                        "Faire une sorie culturelle",
                        "Boire un verre",
                        "Faire la fête toute la nuit"
                )
        );

        Question question2 = new Question(
                "Quel est votre budget ?",
                Arrays.asList(
                        "gratuit",
                        "moins de 15€",
                        "moins de 30€",
                        "ILLIMITE !"
                )
        );

        return new QuestionBank(Arrays.asList(question1, question2));
    }
}