package com.forrealpeople.metoo.metoo;

import android.app.Activity;
import android.widget.AdapterView;

/**
 * Created by Shweta on 5/11/2018.
 */
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SpinnerInteraction extends Activity implements AdapterView.OnItemSelectedListener {

    JSONObject message;
    public JSONObject getMessage()
    {
         return message ;
    }
    public void onItemSelected(AdapterView<?> parent, View spinner, int pos, long id)
        {
            String item_name = Long.toString(id);
            String item = parent.getItemAtPosition(pos).toString();
            parent.getItemAtPosition(pos);
            //Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            try
            {

                if (message.get(item_name)!= null)
                    message.remove(item);
                message.put(item_name, item);
              //  Toast.makeText(parent.getContext(),message.toString(),Toast.LENGTH_LONG).show();
            }
            catch(org.json.JSONException jex)
            {
               //Toast.makeText(parent.getContext(),jex.getMessage(),Toast.LENGTH_LONG).show();
            }
            return ;
        }

        public void onNothingSelected(AdapterView<?> parent)
        {

        }

    }



