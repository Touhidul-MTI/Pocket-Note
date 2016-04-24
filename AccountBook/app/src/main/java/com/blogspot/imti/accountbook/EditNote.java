package com.blogspot.imti.accountbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNote extends AppCompatActivity {

    EditText et1, et2;
    TextView tv1;
    MyDBHandler myDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        myDBHandler = new MyDBHandler(this);

        et1 = (EditText)findViewById(R.id.edit_et1);
        et2 = (EditText)findViewById(R.id.edit_et2);
        tv1 = (TextView)findViewById(R.id.edit_tv1);
        tv1.setText(DataBackUpClass.name);
    }

    public void clickedOn_edit_b1_back(View view) {
        finish();
    }

    public void clickedOn_edit_b2_update(View view) {
        String date = et1.getText().toString();
        String details = et2.getText().toString();

        if(date.isEmpty() || details.isEmpty()){
            Toast.makeText(getApplicationContext(), "Write valid note!", Toast.LENGTH_SHORT).show();
        }
        else{
            details = date+"\n\n"+details;
            et1.setText("");
            et2.setText("");

            myDBHandler.updatePerson(DataBackUpClass.name, details);
            finish();
        }
    }
}
