package com.example.aramsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
        TextView flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        TextView flashcardAnswer3 = findViewById(R.id.flashcard_answer3);
        ImageView eyeball = findViewById(R.id.toggle_choices_visibility1);
        ImageView closedeyeball = findViewById(R.id.toggle_choices_visibility2);

    //If choice is Barrack Obama make the button green
    flashcardAnswer1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v) {
            findViewById(R.id.flashcard_answer1).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, null));
            }
        });

    //If choice is Bill Clinton make Barrack Obama Green and Bill Clinton Red
    flashcardAnswer2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v) {
            findViewById(R.id.flashcard_answer2).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark, null));
            findViewById(R.id.flashcard_answer1).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, null));
            }
        });

    //If choice is George W. Bush make Barrack Obama Green and Bill Clinton Red
    flashcardAnswer3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick (View v) {
        findViewById(R.id.flashcard_answer3).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark, null));
        findViewById(R.id.flashcard_answer1).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, null));
        }
    });

    eyeball.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick (View v) {
        eyeball.setVisibility(View.INVISIBLE);
        flashcardAnswer1.setVisibility(View.INVISIBLE);
        flashcardAnswer2.setVisibility(View.INVISIBLE);
        flashcardAnswer3.setVisibility(View.INVISIBLE);
        closedeyeball.setVisibility(View.VISIBLE);
        }
    });

    closedeyeball.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick (View v) {
        closedeyeball.setVisibility(View.INVISIBLE);
        flashcardAnswer1.setVisibility(View.VISIBLE);
        flashcardAnswer2.setVisibility(View.VISIBLE);
        flashcardAnswer3.setVisibility(View.VISIBLE);
        eyeball.setVisibility(View.VISIBLE);
        }
    });

    }

}