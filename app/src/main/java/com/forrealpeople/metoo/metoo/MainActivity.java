package com.forrealpeople.metoo.metoo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpinner();


    }
    public void setSpinner()
    {
        Spinner dropdown1 = findViewById(R.id.spinner_wh);
        ArrayAdapter<CharSequence> dd1_adapter = ArrayAdapter.createFromResource(this,R.array.spin_whq, R.layout.support_simple_spinner_dropdown_item);
        dropdown1.setAdapter(dd1_adapter);
        Spinner dropdown2 = findViewById(R.id.spin_how);
        ArrayAdapter<CharSequence> dd2_adapter = ArrayAdapter.createFromResource(this,R.array.feel,R.layout.support_simple_spinner_dropdown_item);
        dropdown2.setAdapter(dd2_adapter);

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
}
