package teco.gradetracker;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import teco.gradetracker.Database.DbHelper;
import teco.gradetracker.Database.Scheme;

/**
 * Created by loc18 on 21/07/2017.
 */

public class AssCursorAdapter extends CursorAdapter {

    DbHelper db;
    Context myContext;
    FragmentManager myFragmentManager;

    public AssCursorAdapter(Context context, Cursor cursor, FragmentManager fragmentManager){
        super(context,cursor,0);
        myContext = context;
        myFragmentManager = fragmentManager;
        db = new DbHelper(myContext);

    }

    //get the data item associated with the specified position in the data set
    @Override
    public  Object getItem(int position){
        return super.getItem(position);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_assignment,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView txtAssName = (TextView) view.findViewById(R.id.txtAssName);
        final TextView txtWorth = (TextView) view.findViewById(R.id.txtWorth);
        final TextView txtGrade = (TextView) view.findViewById(R.id.txtGrade);

        //getting information form cursor
        final String assName = cursor.getString(cursor.getColumnIndex(Scheme.Assignment.NAME));
        final Integer grade = cursor.getInt(cursor.getColumnIndex(Scheme.Assignment.GRADE));
        final Integer worth = cursor.getInt(cursor.getColumnIndex(Scheme.Assignment.WORTH));

        //display
        txtAssName.setText(assName);
        txtWorth.setText(String.valueOf(worth));
        txtGrade.setText(String.valueOf(grade));
    }
}
