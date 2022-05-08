package com.example.quizz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizz.R;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 1;

    private static final String USER_DATA = "USER_DATA";
    private static final String USER_DATA_NAME = "USER_DATA_NAME";
    private static final String USER_DATA_PLACE = "USER_DATA_PLACE";

    private TextView mTitleTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private LinearLayout mSeptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeptime = findViewById(R.id.main_linearlayout_septime);
        mTitleTextView = findViewById(R.id.main_textview_title);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences(USER_DATA, MODE_PRIVATE)
                        .edit()
                        .putString(USER_DATA_NAME, mNameEditText.getText().toString())
                        .apply();
                startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), GAME_ACTIVITY_REQUEST_CODE);
            }
        });

        mSeptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this, SeptimeActivity.class));            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            String place = data.getStringExtra(SearchActivity.BUNDLE_EXTRA_SCORE);

            getSharedPreferences(USER_DATA, MODE_PRIVATE)
                    .edit()
                    .putString(USER_DATA_PLACE, place)
                    .apply();

            greetUser();
        }
    }

    private void greetUser() {
        String firstName = getSharedPreferences(USER_DATA, MODE_PRIVATE).getString(USER_DATA_NAME, "");
        String place = getSharedPreferences(USER_DATA, MODE_PRIVATE).getString(USER_DATA_PLACE, "");

        if (!firstName.equals("")) {
            if (!place.equals("")) {
                mTitleTextView.setText(getString(R.string.last_place, firstName, place));
            }
        }
    }

}