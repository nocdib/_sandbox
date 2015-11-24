package com.nocdib.signupform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    public static String userObjectKey = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        User user = (User)getIntent().getSerializableExtra(userObjectKey);
        // Retrieve "welcome_format" from the Strings resource and use it to format the username
        String welcome = String.format(getResources().getString(R.string.welcome_format), user.getUsername());
        // Create a text view object to interface with the XML element in the activity_home layout
        TextView welcomeTextView = (TextView)findViewById(R.id.welcome_text);
        // Have the TextView object display the welcome text
        welcomeTextView.setText(welcome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
