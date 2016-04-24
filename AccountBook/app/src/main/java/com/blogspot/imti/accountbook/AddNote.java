package com.blogspot.imti.accountbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    EditText et1, et2, et3;
    MyDBHandler myDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().hide();

        myDBHandler = new MyDBHandler(this);

        et1 = (EditText)findViewById(R.id.add_et1);
        et2 = (EditText)findViewById(R.id.add_et2);
        et3 = (EditText)findViewById(R.id.add_et3);
    }

    public void clickedOn_add_b1_back(View view) {
        finish();
    }

    public void clickedOn_add_b2_save(View view) {
        String name = et1.getText().toString();
        String date = et2.getText().toString();
        String details = et3.getText().toString();

        if(name.isEmpty() || date.isEmpty() || details.isEmpty()){
            Toast.makeText(getApplicationContext(),"Write valid note!", Toast.LENGTH_SHORT).show();
        }
        else{
            details = date+"\n\n"+details;
            et1.setText("");
            et2.setText("");
            et3.setText("");

            long longNumber = myDBHandler.addPerson(name, details);
            if(longNumber<0){
                Toast.makeText(getApplicationContext(), "Cannot be saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
