package com.example.quizzlerapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestiontextview;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mScore;
    int mIndex;
    int mQuestion;

    // check for github...



    // Create question bank using the TrueFalse class for each item in the array
    @NonNull
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    final int PROGGRESSBAR_INCREMENT = (int) Math.ceil(100 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        }else {
            mScore = 0;
            mIndex = 0;
        }

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestiontextview = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestiontextview.setText(mQuestion);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQuestion();
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQuestion();
                checkAnswer(false);
            }
        });
    }

    private void UpdateQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.length;

        if (mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("Your Score is " + mScore + " Points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestiontextview.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGGRESSBAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    };

    private void checkAnswer(boolean userselection){

        boolean checkAnswer = mQuestionBank[mIndex].isAnswer();
        if(userselection == checkAnswer){
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    public  void savedInstanceState(Bundle outState){
        
        outState.putInt("SoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }
}