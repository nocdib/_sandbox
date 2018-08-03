package com.example.android.miwok;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NumbersActivity extends AppCompatActivity {
    final String TAG = "NumbersActivity";
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        // Set the titlebar color
        Drawable colorDrawable = ContextCompat.getDrawable(getApplicationContext(), R.color.category_numbers);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        Word [] words = {   new Word("one","lutti",R.drawable.number_one),
                            new Word("two","otiiko",R.drawable.number_two),
                            new Word("three","tolookosu",R.drawable.number_three),
                            new Word("four","oyyisa",R.drawable.number_four),
                            new Word("five","massokka",R.drawable.number_five),
                            new Word("six","temmokka",R.drawable.number_six),
                            new Word("seven","kenekaku",R.drawable.number_seven),
                            new Word("eight","kawinta",R.drawable.number_eight),
                            new Word("nine","wo'e",R.drawable.number_nine),
                            new Word("ten","na'aacha",R.drawable.number_ten)
                        };
        ArrayList<Word> wordsList = new ArrayList<Word>(Arrays.asList(words));
        //LinearLayout numbersLayout = (LinearLayout)findViewById(R.id.rootView);
        /*for(int x=0;x<wordsList.size();x++){
            Log.v(TAG, String.format("Word at index %1$d: %2$s", x, wordsList.get(x)));
        }*/

        WordAdapter itemsAdapter = new WordAdapter(this, wordsList,R.color.category_numbers);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Word number = ((Word)listView.getAdapter().getItem(position));
                //String toastText = number.getMiwokTranslation() + " / " + number.getDefaultTranslation();
                //Toast.makeText(NumbersActivity.this, toastText, Toast.LENGTH_SHORT).show();
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_one);
                mMediaPlayer.start();
            }
        });

    }
}
