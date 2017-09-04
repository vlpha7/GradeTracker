package teco.gradetracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import teco.gradetracker.Database.DbHelper;
import teco.gradetracker.Database.Scheme;
import teco.gradetracker.Database.UnitValues;

/**
 * Created by loc18 on 20/07/2017.
 */

public class UnitCursorAdapter extends CursorAdapter {

    public String TAG ="Testing CursorAdapter";

    private DbHelper dbHelper;
    private Context myContext;
    private FragmentManager myFragmentManager;
    //private String name;

    public UnitCursorAdapter(Context context, Cursor c, FragmentManager supportFragmentManager) {
        super(context,c,0);
        myContext = context;
        myFragmentManager = supportFragmentManager;
        Log.d(TAG,"start database");
        dbHelper = new DbHelper(myContext);
        Log.d(TAG,"finish constructor");
    }

    //get the data item associated with the specified position in the data set
    @Override
    public  Object getItem(int position){
        return super.getItem(position);
    }

    /*Make a new view to hold the data pointed to by cursor
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup){
        return LayoutInflater.from(context).inflate(R.layout)
    }*/

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_unit,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor c) {
        Log.d(TAG,"start bindView");
        final TextView unitName = (TextView) view.findViewById(R.id.txtUnitName);

        //Extract properties from cursor
        final String name = c.getString(c.getColumnIndex(Scheme.Unit.NAME));

        //populate field with extracted properties
        unitName.setText(name);
        //set onClick for the item
        unitName.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startAssignments(name);
            }
        });
        Log.d(TAG,"finish bindView");

    }

    public void startAssignments(String name) {
        Log.d(TAG,name);
        Intent intent = new Intent(myContext,Assignments.class);
        intent.putExtra("unitId",name);
        myContext.startActivity(intent);
    }
}
