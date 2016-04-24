package com.blogspot.imti.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Touhidul_MTI on 04-Mar-16.
 */
public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Persons.db";
    private static final String TABLE_NAME = "PersonsTable";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DETAILS = "details";


    Context context;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryToCreateTable = "CREATE TABLE "+TABLE_NAME+
                " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME+" TEXT, "+COLUMN_DETAILS+" TEXT)";

        db.execSQL(queryToCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //add/insert data/person
    public long addPerson(String name, String details){
        SQLiteDatabase sqldb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DETAILS, details);

        long longNumber = sqldb.insert(TABLE_NAME, null, values);

        sqldb.close();
        return longNumber;
    }
    //delete data/person
    public void deletePerson(String personName){
        SQLiteDatabase sqldb = getWritableDatabase();

        //first retrieve value of that row and try check if data exists there or not
        Cursor cursor = sqldb.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + personName + "'", null);
        if(cursor.getCount()!=0){
            //if data exists then delete
            //existing check is just for message showing, not mandatory, delete query can handle if don't exist
            //so only below delete quey is enough for deleting row
            sqldb.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + personName + "'");
            Toast.makeText(context,"Successfully deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,personName+" not found", Toast.LENGTH_LONG).show();
        }
    }

    //printout all data the databse data in string
    public String getDatabaseToString(){
        String dbString1 = "", dbString2 = "";

        SQLiteDatabase sqldb = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        //cursor point to a location in result
        Cursor cursor = sqldb.rawQuery(query, null);

        if(cursor.getCount() == 0){
            Toast.makeText(context,"No data available", Toast.LENGTH_SHORT).show();
        }

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()){
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
            int detailsColumnIndex = cursor.getColumnIndex(COLUMN_DETAILS);
            //upper two line getting column index is not mandatory as I know that which column is what like
            //id 0, name 1, details 2. I can directly put 0,1,2 inside below line like: cursor.getString(1)..;
            stringBuffer.append(cursor.getString(nameColumnIndex)+":  ");
            stringBuffer.append(cursor.getString(detailsColumnIndex)+"\n");
        }
        sqldb.close();
        return stringBuffer.toString();
    }
    //for printing single note data retrieve by name
    public String[] getSingleNoteDatabaseToStringByName(String noteName){
        String []noteArray = new String[2];

        SQLiteDatabase sqldb = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_NAME+" = '"+noteName+"'";

        //cursor point to a location in result
        Cursor cursor = sqldb.rawQuery(query, null);

        if(cursor.getCount() == 0){
            Toast.makeText(context,"No data available", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()){
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
            int detailsColumnIndex = cursor.getColumnIndex(COLUMN_DETAILS);
            //upper two line getting column index is not mandatory as I know that which column is what like
            //id 0, name 1, details 2. I can directly put 0,1,2 inside below line like: cursor.getString(1)..;

            noteArray[0] = cursor.getString(nameColumnIndex);
            noteArray[1] = cursor.getString(detailsColumnIndex);
        }
        sqldb.close();
        return noteArray;
    }
    //for printing single note data retrieve by id
    public String[] getSingleNoteDatabaseToStringById(int noteId){
        String []noteArray = new String[2];

        SQLiteDatabase sqldb = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = '"+noteId+"'";

        //cursor point to a location in result
        Cursor cursor = sqldb.rawQuery(query, null);

        if(cursor.getCount() == 0){
            Toast.makeText(context,"No data available", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()){
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
            int detailsColumnIndex = cursor.getColumnIndex(COLUMN_DETAILS);
            //upper two line getting column index is not mandatory as I know that which column is what like
            //id 0, name 1, details 2. I can directly put 0,1,2 inside below line like: cursor.getString(1)..;

            noteArray[0] = cursor.getString(nameColumnIndex);
            noteArray[1] = cursor.getString(detailsColumnIndex);
        }
        sqldb.close();
        return noteArray;
    }
    public void updatePerson(String personName, String personDetails){
        SQLiteDatabase sqldb = getWritableDatabase();

        //first retrieve value of that row and try check if data exists there or not
        Cursor cursor = sqldb.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + personName + "'", null);
        if(cursor.getCount()!=0){
            //if data exists then update
            //existing check is just for message showing, not mandatory, update query can handle if don't exist
            //so only below update query is enough for updating row
            sqldb.execSQL("UPDATE " + TABLE_NAME + " SET "+COLUMN_DETAILS+" = '"+personDetails+"' WHERE "+COLUMN_NAME+" = '"+personName+"'");
            Toast.makeText(context,"Successfully updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, personName+" not found", Toast.LENGTH_LONG).show();
        }
    }
    //return all data cursor
    public Cursor getDatabaseCursorForAllData(){
        SQLiteDatabase sqldb = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        //cursor point to a location in result
        Cursor cursor = sqldb.rawQuery(query, null);
//        if (cursor!=null){
//            cursor.moveToFirst();
//        }
        return cursor;
    }
    //takes search string and return only search data cursor
    public Cursor getDatabaseCursorForSearchData(String searchNameKey){
        SQLiteDatabase sqldb = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_NAME+" LIKE '%"+searchNameKey+"%'";

        //cursor point to a location in result
        Cursor cursor = sqldb.rawQuery(query, null);
        if(cursor.getCount()==0){
            Toast.makeText(context, "No match found", Toast.LENGTH_LONG).show();
        }
        return cursor;
    }
    public String getColumnId(){
        return COLUMN_ID;
    }
    public String getColumnName(){
        return COLUMN_NAME;
    }
    public String getColumnDetails(){
        return COLUMN_DETAILS;
    }
}
