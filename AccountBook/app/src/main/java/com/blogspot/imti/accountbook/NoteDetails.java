package com.blogspot.imti.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDetails extends AppCompatActivity {

    TextView tv1, tv2;
    MyDBHandler myDBHandler;
    int referenceId;
    String referenceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        myDBHandler = new MyDBHandler(this);

        tv1 = (TextView)findViewById(R.id.details_tv1);
        tv2 = (TextView)findViewById(R.id.details_tv2);

        displayNoteDetails();
    }
    public void displayNoteDetails(){
        referenceId = DataBackUpClass.id;
        referenceName = DataBackUpClass.name;

        String []noteArray = myDBHandler.getSingleNoteDatabaseToStringById(referenceId);
        //String []noteArray = myDBHandler.getSingleNoteDatabaseToStringByName(referenceName);

        tv1.setText(noteArray[0]);
        tv2.setText(noteArray[1]);
    }
    public boolean onCreateOptionsMenu(Menu menuType){
        super.onCreateOptionsMenu(menuType);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menuType);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem itemType){

        if(itemType.getItemId()==R.id.menu_edit){
            Intent goEdit = new Intent(this, EditNote.class);
            startActivity(goEdit);
            return true;

        }else if(itemType.getItemId()==R.id.menu_delete){
            new AlertDialog.Builder(this).setIcon
                    (android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("Want to delete this note?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDBHandler.deletePerson(referenceName);
                            finish();
                        }
                    }).setNegativeButton("NO", null).show();
            return true;

        }else if(itemType.getItemId()==R.id.menu_back){
            finish();
            return true;

        }else{
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayNoteDetails();
    }

    public void clickedOn_details_b1_back(View view) {
        finish();
    }
}
