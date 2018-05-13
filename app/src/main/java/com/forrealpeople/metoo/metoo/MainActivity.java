package com.forrealpeople.metoo.metoo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    JSONObject message;
    SpinnerInteraction spinAction1;
    SpinnerInteraction spinAction2;
    Location myLocation;
    LocationRequests ourLocListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    Long userid;
    Random randId;
    Spinner dropdown1;
    Spinner dropdown2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        ourLocListener = new LocationRequests(this);
        randId = new Random();
        userid = randId.nextLong();
         myLocation = ourLocListener.getMyLastKnownLocation(this);
        message = new JSONObject();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpinner();
        buttonClick();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                             FirebaseUser user = mAuth.getCurrentUser();
                         } else {
                            // If sign in fails, display a message to the user.
                         }

                        // ...
                    }
                });    }

    protected void onResume() {
        super.onResume();
        ourLocListener.resume();
    }
    protected void onPause() {
        super.onPause();
        ourLocListener.stopLocationUpdates();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //ourLocListener.requestLocationUpdateKeys(outState);
        super.onSaveInstanceState(outState);
    }
    public void setSpinner() {

        dropdown1 = findViewById(R.id.spinner_wh);
        ArrayAdapter<CharSequence> dd1_adapter = ArrayAdapter.createFromResource(this, R.array.spin_whq, R.layout.support_simple_spinner_dropdown_item);
        dropdown1.setAdapter(dd1_adapter);
        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                 public void onItemSelected(AdapterView<?> parent, View spinner, int pos, long id)
                {
                    String item_name = Long.toString(id);
                    String item = parent.getItemAtPosition(pos).toString();
                    parent.getItemAtPosition(pos);
                    //Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
                    try
                    {

                        //if (message.get(item_name)!= null)
                        message.remove(item_name);
                        message.put(item_name, item);
                        //  Toast.makeText(parent.getContext(),message.toString(),Toast.LENGTH_LONG).show();
                    }
                    catch(org.json.JSONException jex)
                    {
                        //Toast.makeText(parent.getContext(),jex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    return ;
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        Spinner dropdown2 = findViewById(R.id.spin_how);
        ArrayAdapter<CharSequence> dd2_adapter = ArrayAdapter.createFromResource(this, R.array.feel, R.layout.support_simple_spinner_dropdown_item);
        dropdown2.setAdapter(dd2_adapter);

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinner, int pos, long id)
            {
                String item_name = Long.toString(id);
                String item = parent.getItemAtPosition(pos).toString();
                parent.getItemAtPosition(pos);
                //Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
                try
                {

                //    if (message.get(item_name)!= null)
                    message.remove(item_name);
                    message.put(item_name, item);
                    //  Toast.makeText(parent.getContext(),message.toString(),Toast.LENGTH_LONG).show();
                }
                catch(org.json.JSONException jex)
                {
                    //Toast.makeText(parent.getContext(),jex.getMessage(),Toast.LENGTH_LONG).show();
                }
                return ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
     }

    public void buttonClick()
    {
        final Button button = findViewById(R.id.Report);
        final Activity thisActivity = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String gender = "girl";
                String place = "home";

                CheckBox c1 = findViewById(R.id.girl);
                CheckBox c2 = findViewById(R.id.home);
                EditText age = findViewById(R.id.myAge);

                if (!c1.isChecked())
                    gender = "boy";

                if (!c1.isChecked())
                    place = "not_home";
                try
                {
  /*                  if (message.get("gender") != null)
                        message.remove("gender");
                    if (message.get("place") != null)
                        message.remove("place");
                    if (message.get("age") != null)
                        message.remove("age");
                    if (message.get("location") != null)
                        message.remove("location");
*/
                    message.put("gender", gender);
                    message.put("place", place);
                    message.put("age", age.getText());
                    message.put("location",ourLocListener.getMyLastKnownLocation(thisActivity));
                    send(message);
                } catch (JSONException jex) {
                    Toast.makeText(thisActivity,jex.getMessage(), Toast.LENGTH_LONG).show();
                }
            /*    Iterator keys = message.keys();
                while(keys.hasNext())
                    message.remove(keys.next().toString());
*/
            }
        });

    }

        public void send(JSONObject message)
        {
// Write a message to the database
            Long timeNow = System.currentTimeMillis();
            Map<String, String> userMap= new HashMap<>();
            userMap.put(timeNow.toString(), message.toString());
 //           userMap.put("timestamp", timeNow.toString());
   //         userMap.put("userid", userid.toString());
            mDatabase = FirebaseDatabase.getInstance();

            DatabaseReference mref = mDatabase.getReference(userid.toString());
            if (mref != null) {
                mref = mref.push();

                mref.setValue(userMap);
            }else
            {
                mref = mDatabase.getReference();
                mref = mref.push();
                mref.setValue(userMap);
            }


        }

    }



