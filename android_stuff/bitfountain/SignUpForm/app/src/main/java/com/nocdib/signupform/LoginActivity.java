package com.nocdib.signupform;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button mSubmitButton;
    EditText mUsername;
    EditText mPassword;
    EditText mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSubmitButton = (Button)findViewById(R.id.submit_button);
        mUsername = (EditText)findViewById(R.id.username);
        mPassword = (EditText)findViewById(R.id.password);
        mConfirmPassword = (EditText)findViewById(R.id.confirm_password);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                if(password.equals(confirmPassword) && username.length()>4){ //if the passwords match
                    User newUser = new User(username,password); //create a new User object
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class); //move to the next screen
                    i.putExtra(HomeActivity.userObjectKey, newUser);
                    startActivity(i);
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
