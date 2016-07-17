package com.nocdib.cookie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean mEaten = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(View view) {
        ImageView image = (ImageView) findViewById(R.id.android_cookie_image_view);
        TextView status = (TextView) findViewById(R.id.status_text_view);
        mEaten = !mEaten;
        if(mEaten){
            image.setImageResource(R.drawable.after_cookie);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            status.setText(R.string.after);
        }else{
            image.setImageResource(R.drawable.before_cookie);
            status.setText(R.string.before);
        }
    }
}
