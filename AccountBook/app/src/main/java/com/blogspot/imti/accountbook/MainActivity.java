package com.blogspot.imti.accountbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickedOn_main_b1_view(View view) {
        Intent goNotes = new Intent(this, Notes.class);
        startActivity(goNotes);
    }

    public void clickedOn_main_b2_add(View view) {
        Intent goAddNote = new Intent(this, AddNote.class);
        startActivity(goAddNote);
    }

    public boolean onCreateOptionsMenu(Menu menuType){
        super.onCreateOptionsMenu(menuType);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menuType);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem itemType){

        if(itemType.getItemId()==R.id.main_menu_help){
            AlertDialog dialog=new AlertDialog.Builder(this).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setTitle("How to use?");
            dialog.setMessage("Click on Add Note button to create new note. " +
                    "Click on Show Notes to view saved notes. " +
                    "You can search notes by note name. " +
                    "Click on note name to read that note details and " +
                    "You can edit or delete note from top right menu.");
            dialog.show();
            return true;

        }else if(itemType.getItemId()==R.id.main_menu_about){
            AlertDialog dialog=new AlertDialog.Builder(this).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setTitle("About developer");
            dialog.setMessage("Muhammad Touhidul Islam\n\nhttps://github.com/touhidul-mti\nEmail: mdtislam93@gmail.com");
            dialog.show();
            return true;

        }else if(itemType.getItemId()==R.id.main_menu_exit){
            finish();
            return true;

        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }
}
