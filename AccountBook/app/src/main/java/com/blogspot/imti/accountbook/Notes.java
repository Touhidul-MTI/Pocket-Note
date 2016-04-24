package com.blogspot.imti.accountbook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
public class Notes extends AppCompatActivity {
    EditText et1;
    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().hide();

        et1 = (EditText)findViewById(R.id.notes_et1);

        myDBHandler = new MyDBHandler(this);
        databaseToListViewAllData();
    }

    public void clickedOn_notes_b2_back(View view) {
        finish();
    }
    //search button action
    public void clickedOn_notes_b1_search(View view) {
        String searchNameKey = et1.getText().toString();
        if(searchNameKey.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter name to search", Toast.LENGTH_SHORT).show();
        }else{
           Cursor cursor = myDBHandler.getDatabaseCursorForSearchData(searchNameKey);
            displayDataFromAnyCursor(cursor);
        }
    }

    //for all data printing cursor
    public void databaseToListViewAllData() {
        Cursor cursor = myDBHandler.getDatabaseCursorForAllData();
        displayDataFromAnyCursor(cursor);
    }
    //print all data or single data from cursor
    public void displayDataFromAnyCursor(Cursor curs){
        final Cursor cursor=curs;

        String []columnArray = new String[]{myDBHandler.getColumnName()};
        int []textViewIdArray = new int[]{R.id.lv_tv1};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.lvlayout,
                cursor, columnArray, textViewIdArray);

        ListView lv =(ListView)findViewById(R.id.notes_Lv1);
        lv.setAdapter(simpleCursorAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et1.setText("");
                cursor.moveToPosition(position);

                DataBackUpClass.id = cursor.getInt(cursor.getColumnIndex(myDBHandler.getColumnId()));
                DataBackUpClass.name = cursor.getString(cursor.getColumnIndex(myDBHandler.getColumnName()));

                Intent goNoteDetails = new Intent(getApplicationContext(), NoteDetails.class);
                startActivity(goNoteDetails);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseToListViewAllData();
    }
}
