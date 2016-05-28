package com.overcoretech.troski.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.overcoretech.troski.template.Destination;
import com.overcoretech.troski.template.TerminalRoutesTemplate;
import com.overcoretech.troski.template.TerminalTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakabah on 27/05/16.
 */
public class DBHelper {


    Troski troski;

    public DBHelper(Context context)
    {
        troski = new Troski(context);
    }

    public boolean deleteAllAgencies()
    {
        SQLiteDatabase db = troski.getWritableDatabase();
        db.delete(Troski.TABLE_TERMINALS,null,null);
        db.close();
        return true;
    }

    public boolean deleteAllDestination()
    {
        SQLiteDatabase db = troski.getWritableDatabase();
        db.delete(Troski.TABLE_DESTINATION,null,null);
        db.close();
        return true;
    }

    public boolean deleteAllTerminalROutes()
    {
        SQLiteDatabase db = troski.getWritableDatabase();
        db.delete(Troski.TABLE_TERMINAL_ROUTES,null,null);
        db.close();
        return true;
    }


    public void insertAgency(String AgencyId, String AgencyName)
    {
        SQLiteDatabase db = troski.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Troski.COLUMN_AGENCY_NAME,AgencyName);
        contentValues.put(Troski.COLUMN_AGENCY_ID,AgencyId);

        long mid = db.insert(Troski.TABLE_TERMINALS,null,contentValues);
        db.close();
    }

    public void insertTerminalROutes(String From ,String To,String Terminal)
    {
        SQLiteDatabase db = troski.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Troski.COLUMN_T_ROUTE_NAME_FROM,From);
        contentValues.put(Troski.COLUMN_T_ROUTE_NAME_TO,To);
        contentValues.put(Troski.COLUMN_T_AGENCY_NAME,Terminal);

        long mid = db.insert(Troski.TABLE_TERMINAL_ROUTES,null,contentValues);
        db.close();
    }

    public void insertDestination(String Destination, String lat, String lon)
    {
        SQLiteDatabase db = troski.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Troski.DESTINATION_LAT,lat);
        contentValues.put(Troski.DESTINATION_LONG,lon);
        contentValues.put(Troski.DESTINATION_NAME,Destination);
        Log.e("Inserting",Destination);
        long mid = db.insert(Troski.TABLE_DESTINATION,null,contentValues);
        db.close();
    }

    public List<Destination> getDestination(String searchString)
    {
        List<Destination> destinations = new ArrayList<>();
        SQLiteDatabase db = troski.getWritableDatabase();
        String[] columns = {Troski.DESTINATION_LONG, Troski.DESTINATION_LAT, Troski.DESTINATION_NAME};
        Cursor cursor = db.query(Troski.TABLE_DESTINATION,columns, Troski.DESTINATION_NAME + " LIKE '%" + searchString + "%'",null,null,null,null);
        while(cursor.moveToNext())
        {
            int index  = cursor.getColumnIndex(Troski.DESTINATION_NAME);

            Destination destination = new Destination();
            destination.name = cursor.getString(index);
            destinations.add(destination);
        }
        db.close();
        return destinations;
    }

    public List<Destination> getDestinationDetails(String searchString)
    {
        List<Destination> destinations = new ArrayList<>();
        SQLiteDatabase db = troski.getWritableDatabase();
        String[] columns = {Troski.DESTINATION_LONG, Troski.DESTINATION_LAT, Troski.DESTINATION_NAME};
        Cursor cursor = db.query(Troski.TABLE_DESTINATION,columns, Troski.DESTINATION_NAME + " ='" + searchString + "'",null,null,null,null);
        while(cursor.moveToNext())
        {
            int index  = cursor.getColumnIndex(Troski.DESTINATION_NAME);
            int index1 = cursor.getColumnIndex(Troski.DESTINATION_LAT);
            int index2 = cursor.getColumnIndex(Troski.DESTINATION_LONG);

            Destination destination = new Destination();
            destination.name = cursor.getString(index);
            destination.lat = cursor.getString(index1);
            destination.lon = cursor.getString(index2);
            destinations.add(destination);
        }
        db.close();
        return destinations;
    }

    public ArrayList<TerminalTemplate> getAllTerminals()
    {
        ArrayList<TerminalTemplate> terminalTemplates = new ArrayList<>();
        SQLiteDatabase db = troski.getWritableDatabase();
        String[] columns = {Troski.COLUMN_AGENCY_NAME,Troski.COLUMN_AGENCY_ID};
        Cursor cursor = db.query(Troski.TABLE_TERMINALS,columns,null,null,null,null,null);
        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(Troski.COLUMN_AGENCY_NAME);
            int index1 = cursor.getColumnIndex(Troski.COLUMN_AGENCY_ID);

            TerminalTemplate terminalTemplate = new TerminalTemplate();
            terminalTemplate.TerminalName = cursor.getString(index);
            terminalTemplate.TerminalId = cursor.getString(index1);
            terminalTemplates.add(terminalTemplate);
        }
        db.close();
        return terminalTemplates;
    }

    public ArrayList<TerminalRoutesTemplate> getAllTerminalRoutes(String Terminal)
    {
        ArrayList<TerminalRoutesTemplate> terminalRoutesTemplates = new ArrayList<>();
        SQLiteDatabase db = troski.getWritableDatabase();
        String[] columns = {Troski.COLUMN_T_ROUTE_NAME_FROM,Troski.COLUMN_T_AGENCY_NAME,Troski.COLUMN_T_ROUTE_NAME_TO};
        Cursor cursor = db.query(Troski.TABLE_TERMINAL_ROUTES,columns,Troski.COLUMN_T_AGENCY_NAME + "= '"+Terminal+"'",null,null,null,null);
        while(cursor.moveToNext()) {
            int index  = cursor.getColumnIndex(Troski.COLUMN_T_ROUTE_NAME_FROM);
            int index1 = cursor.getColumnIndex(Troski.COLUMN_T_ROUTE_NAME_TO);

            TerminalRoutesTemplate terminalRoutesTemplate = new TerminalRoutesTemplate();
            terminalRoutesTemplate.RouteName = cursor.getString(index);
            terminalRoutesTemplate.RouteId  = cursor.getString(index);
            terminalRoutesTemplate.RouteTo  = cursor.getString(index1);

            terminalRoutesTemplates.add(terminalRoutesTemplate);
        }
        db.close();
        return terminalRoutesTemplates;
    }

    class Troski extends SQLiteOpenHelper
    {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "troski.db";

        public static final String TABLE_TERMINALS = "terminals";
        public static final String COLUMN_TERMINAL_ID = "_id";
        public static final String COLUMN_AGENCY_NAME = "agency_name";
        public static final String COLUMN_AGENCY_ID = "agency_id";


        public static final String DATABASE_AGENCY = " create table "
                + TABLE_TERMINALS + "("+ COLUMN_TERMINAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AGENCY_ID + " text , "
                + COLUMN_AGENCY_NAME + " text);";

        public static final String TABLE_TERMINAL_ROUTES = "terminal_routes";
        public static final String COLUMN_T_AGENCY_NAME = "terminal_name";
        public static final String COLUMN_T_ROUTE_NAME_TO = "route_name";
        public static final String COLUMN_T_ROUTE_NAME_FROM = "route_from";
        public static final String COLUMN_T_ID = "_id";

        public static  final String DATABASE_T_ROUTES = " create table "
                + TABLE_TERMINAL_ROUTES + "(" + COLUMN_T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_T_AGENCY_NAME + " text ,"
                + COLUMN_T_ROUTE_NAME_FROM + " text ,"
                + COLUMN_T_ROUTE_NAME_TO + " text);";

        public static final String TABLE_DESTINATION = "destination";
        public static final String DESTINATION_NAME = "destination_name";
        public static final String DESTINATION_LONG = "destination_long";
        public static final String DESTINATION_LAT = "destination_lat";
        public static final String DESTINATION_ID = "_id";


        public static final String DATABASE_DESTINATION = " create table "
                + TABLE_DESTINATION + "(" + DESTINATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DESTINATION_NAME + " text ,"
                + DESTINATION_LAT + " text ,"
                + DESTINATION_LONG + " text);";


        public Troski(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_AGENCY);
            db.execSQL(DATABASE_T_ROUTES);
            db.execSQL(DATABASE_DESTINATION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMINALS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMINAL_ROUTES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESTINATION);
            this.onCreate(db);
        }
    }
}
