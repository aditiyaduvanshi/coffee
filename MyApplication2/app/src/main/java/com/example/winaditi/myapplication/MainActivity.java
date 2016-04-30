package com.example.winaditi.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends ActionBarActivity {

    int quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void submitOrder(View view) {
        EditText name= (EditText)findViewById(R.id.album_view);
        String value= name.getText().toString();

        CheckBox notifyMe= (CheckBox)findViewById(R.id.notify_me_checkbox);
        boolean hasCream= notifyMe.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price= calculatePrice(hasChocolate, hasCream);

        String priceMessage= createOrderSummary(price,value,hasCream,hasChocolate);
        displayMessage(priceMessage);

        String address="androidapp_coffeeorder@gmail.com";

        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }




    private int calculatePrice(boolean addChocolate,boolean addCream){
        int base=30;
        if(addChocolate)
            base=base+5;
        if(addCream)
            base=base+3;
        return base*quantity;
    }

    private String createOrderSummary(int x,String y,boolean istrue,boolean ishas){
        String priceMessage="Name:  "+y+"\nQuantity:  "+quantity;
        priceMessage+="\nAdd whipped cream?  "+istrue+"\nAdd chocolate?  "+ishas;
        priceMessage+="\nTotal Amount:  Rs "+x+"\nThank You!";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void increment(View view) {
        quantity=quantity+1;
        display(quantity);

    }

    public void decrement(View view) {
        if(quantity==1)
        {
            display(1);
        }
        else {
            quantity = quantity - 1;
            display(quantity);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
