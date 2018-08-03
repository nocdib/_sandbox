package com.example.android.miwok;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class PhrasesActivity extends AppCompatActivity {
    final String TAG = "PhraseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        // Set the titlebar color
        Drawable colorDrawable = ContextCompat.getDrawable(getApplicationContext(), R.color.category_phrases);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        Word [] words = {   new Word("Where are you going?","minto wuksus?"),
                            new Word("What is your name?","tinnә oyaase'nә?"),
                            new Word("My name is...","oyaaset..."),
                            new Word("How are you feeling?","michәksәs?"),
                            new Word("I’m feeling good.","kuchi achit."),
                            new Word("Are you coming?","әәnәs'aa?"),
                            new Word("Yes, I’m coming.","hәә’ әәnәm."),
                            new Word("I’m coming.","әәnәm."),
                            new Word("Let’s go.","yoowutis."),
                            new Word("Come here.","әnni'nem.")
        };

        ArrayList<Word> wordsList = new ArrayList<Word>(Arrays.asList(words));
        WordAdapter itemsAdapter = new WordAdapter(this, wordsList,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
