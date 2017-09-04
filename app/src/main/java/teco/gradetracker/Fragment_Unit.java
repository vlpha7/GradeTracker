package teco.gradetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import teco.gradetracker.Database.DbHelper;

/**
 * Created by loc18 on 20/07/2017.
 */

public class Fragment_Unit extends Fragment {
    public String TAG = "Testing Fragment_Unit";
    DbHelper dbHelper;
    LayoutInflater myInflater = null;

    public Fragment_Unit(){
        // just empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        dbHelper = new DbHelper(getContext());
        Log.d(TAG,"finish onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesSate){
        //Infalte the layout for this fragment
        myInflater = inflater;
        View v = inflater.inflate(R.layout.fragment_unit,container,false);

        Log.d(TAG,"finish onCreateView");
        return v;

    }

    @Override
    public void onResume(){
        super.onResume();
        LoadListUnits();
        Log.d(TAG,"finish onResume");
    }

    public void LoadListUnits() {
        Log.d(TAG,"start LoadListUnits");
        Cursor c = dbHelper.getAllUnits();
        ListView lvItems = (ListView) getActivity().findViewById((R.id.list_units));

        UnitCursorAdapter uca = new UnitCursorAdapter(getContext() , c ,getActivity().getSupportFragmentManager());
        lvItems.setAdapter(uca);
    }
}
