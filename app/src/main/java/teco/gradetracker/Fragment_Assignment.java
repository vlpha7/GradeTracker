package teco.gradetracker;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import teco.gradetracker.Database.DbHelper;

/**
 * Created by loc18 on 21/07/2017.
 */

public class Fragment_Assignment extends Fragment {

    DbHelper db;
    LayoutInflater myInflater = null;
    String unitName;

    public Fragment_Assignment(){}

    @Override
    public void onCreate(Bundle savedInstacesState){
        super.onCreate(savedInstacesState);
        db = new DbHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstancesState){
        myInflater = inflater;
        View v = inflater.inflate(R.layout.fragment_assignment,viewGroup,false);
        unitName = getArguments().getString("currentUnit");
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        LoadListAssignment();
    }

    public void LoadListAssignment() {
        Cursor c = db.getAllAssignmentByUnit(unitName);
        ListView lvItems = (ListView) getActivity().findViewById((R.id.list_assginment));
//        Log.d("lolo",String.valueOf(c.getCount()));
        if(c.moveToFirst()){
            AssCursorAdapter uca = new AssCursorAdapter(getContext(), c, getActivity().getSupportFragmentManager());
            lvItems.setAdapter(uca);}
    }
}
