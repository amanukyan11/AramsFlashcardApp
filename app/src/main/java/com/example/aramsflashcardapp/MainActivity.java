package com.example.aramsflashcardapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer1;
    TextView flashcardAnswer2;
    TextView flashcardAnswer3;
    ImageView eyeball;
    ImageView closed_eyeball;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
//        flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
//        flashcardAnswer3 = findViewById(R.id.flashcard_answer3);
        eyeball = findViewById(R.id.toggle_choices_visibility1);
        closed_eyeball = findViewById(R.id.toggle_choices_visibility2);
        findViewById(R.id.newActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer1)).setText(allFlashcards.get(0).getAnswer());
        }

//        //If choice is Barrack Obama make the button green
//        flashcardAnswer1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.flashcard_answer1).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, null));
//            }
//        });
//
//        //If choice is Bill Clinton make Barrack Obama Green and Bill Clinton Red
//        flashcardAnswer2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.flashcard_answer2).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark, null));
//                findViewById(R.id.flashcard_answer1).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, null));
//            }
//        });
//
//        //If choice is George W. Bush make Barrack Obama Green and Bill Clinton Red
//        flashcardAnswer3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.flashcard_answer3).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark, null));
//                findViewById(R.id.flashcard_answer1).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, null));
//            }
//        });
//
//        eyeball.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                eyeball.setVisibility(View.INVISIBLE);
//                flashcardAnswer1.setVisibility(View.INVISIBLE);
//                flashcardAnswer2.setVisibility(View.INVISIBLE);
//                flashcardAnswer3.setVisibility(View.INVISIBLE);
//                closedeyeball.setVisibility(View.VISIBLE);
//            }
//        });
//
//        closedeyeball.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closedeyeball.setVisibility(View.INVISIBLE);
//                flashcardAnswer1.setVisibility(View.VISIBLE);
//                flashcardAnswer2.setVisibility(View.VISIBLE);
//                flashcardAnswer3.setVisibility(View.VISIBLE);
//                eyeball.setVisibility(View.VISIBLE);
//            }
//        });
//
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer1.setVisibility(View.VISIBLE);
            }
        });

        flashcardAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardAnswer1.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.nexButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // don't try to go to next card if you have no cards to begin with
                if (allFlashcards.size() == 0)
                    return;
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.flashcard_answer1)).setText(flashcard.getQuestion());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String string1 = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("answer");
            flashcardQuestion.setText(string1);
            flashcardAnswer1.setText(string2);
//            flashcardAnswer2.setVisibility(View.INVISIBLE);
//            flashcardAnswer3.setVisibility(View.INVISIBLE);
            flashcardDatabase.insertCard(new Flashcard(string1, string2));
            allFlashcards = flashcardDatabase.getAllCards();

            if (allFlashcards != null && allFlashcards.size() > 0) {
                Flashcard firstCard = allFlashcards.get(0);
                flashcardQuestion.setText(firstCard.getQuestion());
                flashcardAnswer1.setText(firstCard.getAnswer());
            }
        }
    }

}