package com.example.android.miwok;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FamilyActivity extends AppCompatActivity {
    final String TAG = "FamilyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        // Set the titlebar color
        Drawable colorDrawable = ContextCompat.getDrawable(getApplicationContext(), R.color.category_family);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        Word [] words = {   new Word("father","әpә",R.drawable.family_father),
                            new Word("mother","әṭa",R.drawable.family_mother),
                            new Word("son","angsi",R.drawable.family_son),
                            new Word("daughter","tune",R.drawable.family_daughter),
                            new Word("older brother","taachi",R.drawable.family_older_brother),
                            new Word("younger brother","chalitti",R.drawable.family_younger_brother),
                            new Word("older sister","teṭe",R.drawable.family_older_sister),
                            new Word("younger sister","kolliti",R.drawable.family_younger_sister),
                            new Word("grandmother","ama",R.drawable.family_grandmother),
                            new Word("grandfather","paapa",R.drawable.family_grandfather)
        };
        ArrayList<Word> wordsList = new ArrayList<Word>(Arrays.asList(words));
        WordAdapter itemsAdapter = new WordAdapter(this, wordsList,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
