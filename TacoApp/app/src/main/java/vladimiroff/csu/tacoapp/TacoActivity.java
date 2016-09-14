package vladimiroff.csu.tacoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TacoActivity extends Activity {

    RadioButton large;
    RadioButton medium;

    RadioButton corn;
    RadioButton flour;

    CheckBox beef;
    CheckBox chicken;
    CheckBox fish;
    CheckBox cheese;
    CheckBox seafood;
    CheckBox rice;
    CheckBox beans;
    CheckBox pico;
    CheckBox guac;
    CheckBox lbt;

    CheckBox soda;
    CheckBox margarita;
    CheckBox cerveza;
    CheckBox tequila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taco);

        large = (RadioButton) findViewById(R.id.largeRadioButton);
        medium = (RadioButton) findViewById(R.id.mediumRadioButton);
        corn = (RadioButton) findViewById(R.id.cornRadioButton);
        flour = (RadioButton) findViewById(R.id.flourRadioButton);

        beef = (CheckBox) findViewById(R.id.beefCheckBox);
        chicken = (CheckBox) findViewById(R.id.chickenCheckBox);
        fish = (CheckBox) findViewById(R.id.whiteFishCheckBox);
        cheese = (CheckBox) findViewById(R.id.cheeseCheckBox);
        seafood = (CheckBox) findViewById(R.id.seafoodCheckBox);
        rice = (CheckBox) findViewById(R.id.riceCheckBox);
        beans = (CheckBox) findViewById(R.id.beansCheckBox);
        pico = (CheckBox) findViewById(R.id.picoCheckBox);
        guac = (CheckBox) findViewById(R.id.guacCheckBox);
        lbt = (CheckBox) findViewById(R.id.lbtCheckBox);

        soda = (CheckBox) findViewById(R.id.sodaCheckBox);
        margarita = (CheckBox) findViewById(R.id.margaritaCheckBox);
        cerveza = (CheckBox) findViewById(R.id.cervezaCheckBox);
        tequila = (CheckBox) findViewById(R.id.tequilaCheckBox);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_taco, menu);
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

    public void onClick(View view) {
        Intent next = new Intent(this, CustomerInfo.class);
        next.putExtra("SMS", getSMS());
        startActivity(next);

    }

    public String getSMS() {
        String sms = "";

        if(large.isChecked()) {
            sms += "large ";
        } else if(medium.isChecked()) {
            sms += "medium";
        }

        if(corn.isChecked()) {
            sms += "corn ";
        } else if(flour.isChecked()) {
            sms += "flour ";
        }

        if(beef.isChecked()) {
            sms += "beef ";
        }

        if(chicken.isChecked()) {
            sms += "chicken ";
        }

        if(fish.isChecked()) {
            sms += "fish ";
        }

        if(cheese.isChecked()) {
            sms += "cheese ";
        }

        if(seafood.isChecked()) {
            sms += "seafood ";
        }

        if(rice.isChecked()) {
            sms += "rice ";
        }

        if(beans.isChecked()) {
            sms += "beans ";
        }

        if(pico.isChecked()) {
            sms += "pico ";
        }

        if(guac.isChecked()) {
            sms += "guac ";
        }

        if(lbt.isChecked()) {
            sms += "lbt ";
        }

        if(soda.isChecked()) {
            sms += "soda ";
        }

        if(margarita.isChecked()) {
            sms += "margarita ";
        }

        if(cerveza.isChecked()) {
            sms += "cerveza ";
        }

        if(tequila.isChecked()) {
            sms += "tequila ";
        }

        return sms;
    }
}
