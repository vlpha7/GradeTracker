package teco.gradetracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DbHelper extends SQLiteOpenHelper{

    public String TAG = "Testing DbHelper";
    private final static String DB_NAME = "Database.db";
    private final static int DB_VERSION = 1;

    private final static String ASSIGNMENT_TABLE_CREATE =
            "CREATE TABLE " +
                    Scheme.Assignment.TABLE_NAME + " (" +
                    Scheme.Assignment.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Scheme.Assignment.NAME + " TEXT, " +
                    Scheme.Assignment.WORTH + " INTEGER, " +
                    Scheme.Assignment.GRADE + " INTEGER, " +
                    Scheme.Assignment.UNIT_NAME + " TEXT);";

    private final static String UNIT_TABLE_CREATE =
            "CREATE TABLE " +
                    Scheme.Unit.TABLE_NAME + " (" +
                    Scheme.Unit.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Scheme.Unit.NAME + " TEXT);";


    public DbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG,"start onCreate");
        sqLiteDatabase.execSQL(ASSIGNMENT_TABLE_CREATE);
        sqLiteDatabase.execSQL(UNIT_TABLE_CREATE);
        Log.d(TAG,"finish onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG,"start onUpgrade");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UNIT_TABLE_CREATE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENT_TABLE_CREATE);
        onCreate(sqLiteDatabase);
        Log.d(TAG,"finish onUpgrade");
    }

    // -------------------------------- "Unit" table method-------------------------------- //

    /**
     * Creating a Unit
     */
     public void createUnit(UnitValues unit){
         SQLiteDatabase db = this.getWritableDatabase();

         Log.d(TAG,unit.getName());
         ContentValues values = new ContentValues();
         values.put(Scheme.Unit.NAME, unit.getName());

         //insert row
         db.insert(Scheme.Unit.TABLE_NAME,null,values);
         //db.close();
         //return unit_id;
     }

    /**
     * get single unit by Unit_name
     */
    public Cursor getUnit (String name){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Scheme.Unit.TABLE_NAME + " WHERE "
                + Scheme.Unit.NAME + " = " + "'"+name+"'";

        Cursor c = db.rawQuery(selectQuery,null);

        /*if(c !=null){
            c.moveToFirst();
        }
        AssignmentValues as = new AssignmentValues();
        as.setId(c.getInt(c.getColumnIndex(Scheme.Assignment.ID)));
        as.setName(c.getString(c.getColumnIndex(Scheme.Assignment.NAME)));
        as.setGrade(c.getInt(c.getColumnIndex(Scheme.Assignment.GRADE)));
        as.setWorth(c.getInt(c.getColumnIndex(Scheme.Assignment.WORTH)));
        as.setUnitName(c.getString(c.getColumnIndex(Scheme.Assignment.UNIT_NAME)));*/

        //db.close();
        return c;
    }

    /**
     * getting all units
     */
    public Cursor getAllUnits(){
        List<UnitValues> units = new ArrayList<UnitValues>();

        String selectQuery = "SELECT * FROM " + Scheme.Unit.TABLE_NAME;
        Log.d(TAG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);
        Log.d(TAG,"done rawQuery");
        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                UnitValues unit = new UnitValues();
                unit.setId(c.getInt(c.getColumnIndex(Scheme.Unit.ID)));
                unit.setName(c.getString(c.getColumnIndex((Scheme.Unit.NAME))));

                // adding to Units list
                Log.d(TAG,unit.getName());
            }while(c.moveToNext());
        }
        //db.close();
        return c;
    }

    /**
     * Updating an unit
     */
    public long updateUnit(UnitValues unit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Scheme.Unit.NAME,unit.getName());

        //db.close();
        //updating row
        return db.update(Scheme.Unit.TABLE_NAME,values, Scheme.Unit.ID + " = ?",
                new String[] {String.valueOf(unit.getId())});
    }

    /**
     * Deleting an unit
     */
    public void deleteUnit(UnitValues unit){
        SQLiteDatabase db = this.getWritableDatabase();

        //before deleting an unit check if it appears in some Assignment
        Cursor c = getAllAssignmentByUnit(unit.getName());
        List<AssignmentValues> allAssignments = new ArrayList<AssignmentValues>();
        if(c.moveToFirst()){
            do{
                AssignmentValues as = new AssignmentValues();
                as.setId(c.getInt(c.getColumnIndex(Scheme.Assignment.ID)));
                as.setName(c.getString(c.getColumnIndex(Scheme.Assignment.NAME)));
                as.setWorth(c.getInt(c.getColumnIndex(Scheme.Assignment.WORTH)));
                as.setGrade(c.getInt(c.getColumnIndex(Scheme.Assignment.GRADE)));
                as.setUnitName(c.getInt(c.getColumnIndex(Scheme.Assignment.UNIT_NAME)));

                allAssignments.add(as);
            } while(c.moveToNext());

        }


        //delete all the Assignment
        for(AssignmentValues assignments : allAssignments){
            deleteAssignment(assignments.getId());
        }

        //db.close();
        // now delete the unit
        db.delete(Scheme.Unit.TABLE_NAME, Scheme.Unit.ID + " = ?",
                new String[] {String.valueOf(unit.getId())});
    }

    // -------------------------------- "Assignment" table method-------------------------------- //

    /**
     * Creating an assignment
     */
    public long createAssignment(AssignmentValues assignment){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Scheme.Assignment.NAME, assignment.getName());
        values.put(Scheme.Assignment.GRADE, assignment.getGrade());
        values.put(Scheme.Assignment.WORTH, assignment.getWorth());
        values.put(Scheme.Assignment.UNIT_NAME, assignment.getUnitName());
        Log.d(TAG, "createAssignment"+Scheme.Assignment.UNIT_NAME);

        //db.close();
        return db.insert(Scheme.Assignment.TABLE_NAME,null,values);
    }

    /**
     * get single assignment
     */
    public Cursor getAssignment (long id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Scheme.Assignment.TABLE_NAME + " WHERE "
                + Scheme.Assignment.ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery,null);

        /*if(c !=null){
            c.moveToFirst();
        }
        AssignmentValues as = new AssignmentValues();
        as.setId(c.getInt(c.getColumnIndex(Scheme.Assignment.ID)));
        as.setName(c.getString(c.getColumnIndex(Scheme.Assignment.NAME)));
        as.setGrade(c.getInt(c.getColumnIndex(Scheme.Assignment.GRADE)));
        as.setWorth(c.getInt(c.getColumnIndex(Scheme.Assignment.WORTH)));
        as.setUnitName(c.getString(c.getColumnIndex(Scheme.Assignment.UNIT_NAME)));*/

        //db.close();
        return c;
    }

    /**
     * get all assignment
     */
    public Cursor getAllAssignment() {
        List<AssignmentValues> assignments = new ArrayList<AssignmentValues>();
        String selectQuery = "SELECT * FROM " + Scheme.Assignment.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do{
                AssignmentValues as = new AssignmentValues();
                as.setId(c.getInt(c.getColumnIndex(Scheme.Assignment.ID)));
                as.setName(c.getString(c.getColumnIndex(Scheme.Assignment.NAME)));
                as.setWorth(c.getInt(c.getColumnIndex(Scheme.Assignment.WORTH)));
                as.setGrade(c.getInt(c.getColumnIndex(Scheme.Assignment.GRADE)));
                as.setUnitName(c.getInt(c.getColumnIndex(Scheme.Assignment.UNIT_NAME)));

                assignments.add(as);
            } while(c.moveToNext());
        }
        //db.close();
        return c;
    }

    /**
     * get all assignment under an unit
     */
    public Cursor getAllAssignmentByUnit(String unit_name){



        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c_unit = getUnit(unit_name);
        String selectQuery="";
        //String whereArgs = new String[];
        if(c_unit.moveToFirst()){
            Log.d(TAG,"by " + unit_name);
        selectQuery = "SELECT * FROM " + Scheme.Assignment.TABLE_NAME
                + " WHERE " + Scheme.Assignment.UNIT_NAME + " = " + c_unit.getInt(c_unit.getColumnIndex(Scheme.Unit.ID)) ;}
        Cursor c = db.rawQuery(selectQuery,null);
        //Cursor c = db.query(Scheme.Assignment.TABLE_NAME,null,Scheme.Assignment.UNIT_NAME + "=?",unit_name,null,null,null);

        /*if(c.moveToFirst()){
            do{
                AssignmentValues as = new AssignmentValues();
                as.setId(c.getInt(c.getColumnIndex(Scheme.Assignment.ID)));
                as.setName(c.getString(c.getColumnIndex(Scheme.Assignment.NAME)));
                as.setWorth(c.getInt(c.getColumnIndex(Scheme.Assignment.WORTH)));
                as.setGrade(c.getInt(c.getColumnIndex(Scheme.Assignment.GRADE)));
                as.setUnitName(c.getString(c.getColumnIndex(Scheme.Assignment.UNIT_NAME)));

                assignments.add(as);
            } while(c.moveToNext());

        }*/
        Log.d(TAG,String.valueOf(c.getCount()));
        return c;
    }

    /**
     * get assignment's count
     */
    public long assignmentCount(){
        String countQuery = "SELECT * FROM "+Scheme.Assignment.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(countQuery,null);

        long count = c.getCount();

        return count;
    }

    /**
     * update assignment
     */
    public long updateAssignment(AssignmentValues as){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Scheme.Assignment.GRADE,as.getGrade());
        values.put(Scheme.Assignment.UNIT_NAME,as.getUnitName());
        values.put(Scheme.Assignment.NAME,as.getName());
        values.put(Scheme.Assignment.WORTH,as.getWorth());

        //db.close();
        return db.update(Scheme.Assignment.TABLE_NAME,values,Scheme.Assignment.ID + " =?",
                new String[] {String.valueOf(as.getId())});

    }

    /**
     * delete an assignment
     */
    public void deleteAssignment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Scheme.Assignment.TABLE_NAME,Scheme.Assignment.ID + " =?",
                new String[] {String.valueOf(id)});
        //db.close();
    }


}
