package com.example.cas.adw1t3;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] mEyes = new int[]{
        R.drawable.d1,
        R.drawable.d2,
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d5,
        R.drawable.d6
    };

    private int mCurrentEyes = 1;
    private int mCurrentScore = 0;
    private int mHighscore = 0;

    
    private LinkedList mLinkedListLatest;
    ArrayAdapter mAdapter;

    TextView mTextViewScore;
    TextView mTextViewHighScore;
    ListView mListLatestScores;
    Button mButtonHigher;
    Button mButtonLower;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewScore = findViewById(R.id.textViewScore);
        mTextViewHighScore = findViewById(R.id.textViewHighScore);
        mListLatestScores = findViewById(R.id.listView);
        mButtonHigher = findViewById(R.id.buttonHigher);
        mButtonLower = findViewById(R.id.buttonLower);
        mImageView = findViewById(R.id.imageView);

        mLinkedListLatest = new LinkedList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLinkedListLatest);
        mListLatestScores.setAdapter(mAdapter);

        mTextViewHighScore.setText("Highscore: 0");

        CharSequence text = "Incorrect!";
    }

    private int generateNewRandomNumber() {
        Random random = new Random();
        int newValue = mCurrentEyes;
        while (newValue == mCurrentEyes){
            newValue = random.nextInt(6) + 1;
        }
        mImageView.setImageResource(mEyes[newValue-1]);
        return newValue;
    }

    public void guessHigher(View V){
        mAdapter.notifyDataSetChanged();
        int newValue = generateNewRandomNumber();
        mImageView.setImageResource(mEyes[newValue-1]);
        mImageView.setImageResource(mEyes[newValue-1]);
        if (newValue > mCurrentEyes) {
            notifyUser(true);
            addToList(true, true);
            mCurrentScore++;
            if (mCurrentScore > mHighscore){
                mHighscore = mCurrentScore;
                mTextViewHighScore.setText("Highscore: " + mHighscore);
            }
        }
        else {
            notifyUser(false);
            addToList(false, true);
            mCurrentScore = 0;
        }
        mCurrentEyes = newValue;
        mTextViewScore.setText("Score: " + mCurrentScore);
    }

    public void guessLower(View V){
        int newValue = generateNewRandomNumber();
        mImageView.setImageResource(mEyes[newValue-1]);
        if (newValue < mCurrentEyes) {
            notifyUser(true);
            addToList(true, false);
            mCurrentScore++;
            if (mCurrentScore < mHighscore){
                mHighscore = mCurrentScore;
                mTextViewHighScore.setText("Highscore: " + mHighscore);
            }
        }
        else {
            notifyUser(false);
            addToList(false, false);
            mCurrentScore = 0;
        }
        mCurrentEyes = newValue;
        mTextViewScore.setText("Score: " + mCurrentScore);
    }

    private void notifyUser(Boolean right){
        Context context = getApplicationContext();

        CharSequence text = "Incorrect!";
        if (right) {
            text = "Correct!";
        }

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void addToList(Boolean right, boolean higher){
        StringBuilder stringBuilder = new StringBuilder();

        if (right) {
            stringBuilder.append("Correct: \t");
        }
        else {
            stringBuilder.append("Incorrect: \t");
        };

        if (higher) {
            stringBuilder.append("Higher then ");
        }
        else {
            stringBuilder.append("Lower then ");
        }
        stringBuilder.append(mCurrentEyes);
        mLinkedListLatest.add(stringBuilder.toString());
        mAdapter.notifyDataSetChanged();
    }
}
