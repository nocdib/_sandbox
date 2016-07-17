package com.nocdib.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int count = 0;
    CheckBox[] toppings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox[] toppings = {
                (CheckBox) findViewById(R.id.whippedCream),
                (CheckBox) findViewById(R.id.chocolate),
                (CheckBox) findViewById(R.id.strawberries),
                (CheckBox) findViewById(R.id.sprinkles),
        };
        this.toppings = toppings;
        ((TextView)findViewById(R.id.quantity_text_view)).setText(Integer.toString(count));
        watchEmail((EditText) findViewById(R.id.emailText));
    }

    /**
     * Increase quantity
     */
    public void increaseQuantity(View view){
        display(++count);
    }

    /**
     * Decrease quantity
     */
    public void decreaseQuantity(View view){
        if(count > 0) {
            display(--count);
        }
    }

    /**
     * Enable the Email button when a valid email address is entered.
     */
    public void watchEmail(EditText email){
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches();
                Button emailButton = (Button) findViewById(R.id.email_button);
                if (isValidEmail){
                    emailButton.setEnabled(true);
                } else{
                    emailButton.setEnabled(false);
                }
            }
        });

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayPrice(count * 5);
    }

    /**
     * This method is called when the email button is clicked.
     */
    public void composeEmail(View view){
        String orderText = ((TextView) findViewById(R.id.order_summary_text_view)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "recipient@email.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your JustJava Order");
        intent.putExtra(Intent.EXTRA_TEXT, orderText);
        // Only start the intent if there is an app to handle it
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if(number%2==0){
            quantityTextView.setTextColor(Color.RED);
            quantityTextView.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            quantityTextView.setTextColor(Color.BLACK);
            quantityTextView.setTypeface(Typeface.DEFAULT);
        }

        quantityTextView.setText(String.format("%d",number));
    }

    private void displayPrice(int price) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setMovementMethod(new ScrollingMovementMethod());
        priceTextView.setHorizontallyScrolling(true);

        EditText nameTextView = (EditText) findViewById(R.id.nameText);

        String selectedToppings = "";
        boolean atLeastOneSelected = false; // determine if commas should be used for multiple toppings
        StringBuilder orderSummary = new StringBuilder();

        for(CheckBox topping: toppings){
            if(topping.isChecked()){
                price++;
                selectedToppings += atLeastOneSelected ? ", " +  topping.getText() : topping.getText();
                atLeastOneSelected = true;
            }
        }

        orderSummary.append(getString(R.string.order_summary_name) + ": ");
        orderSummary.append(nameTextView.getText().toString().length() > 0 ? nameTextView.getText().toString() : "");
        orderSummary.append("\n" + getString(R.string.order_summary_toppings) + ": ");
        orderSummary.append(atLeastOneSelected ? selectedToppings : "None");
        orderSummary.append("\n" + getString(R.string.order_summary_quantity) + ": ");
        orderSummary.append(count);
        orderSummary.append("\n" + getString(R.string.order_summary_total) + ": ");
        orderSummary.append(NumberFormat.getCurrencyInstance().format(price));

        priceTextView.setText(orderSummary.toString());
    }


}