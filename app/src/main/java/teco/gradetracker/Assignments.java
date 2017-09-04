package teco.gradetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import teco.gradetracker.Database.AssignmentValues;
import teco.gradetracker.Database.DbHelper;
import teco.gradetracker.Database.Scheme;
import teco.gradetracker.Database.UnitValues;

public class Assignments extends AppCompatActivity {

    final Context context = this;
    public String TAG = "Testing Assignment";

    DbHelper db;
    Fragment_Assignment fragment_assignment;
    String unitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        db = new DbHelper(context);
        unitName = getIntent().getStringExtra("unitId");
        Log.d(TAG,unitName);
        getSupportActionBar().setTitle(unitName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putString("currentUnit",unitName);
        fragment_assignment = new Fragment_Assignment();
        fragment_assignment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                                    .add(R.id.container_fragment_assignment,fragment_assignment,"fragment_assignment")
                                    .addToBackStack("fragment_assignment")
                                    .commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_assignment);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptView = li.inflate(R.layout.prompt_assignment,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompt.xml to alertDialog builder
                alertDialogBuilder.setView(promptView);

                final EditText edtAssName = (EditText) promptView.findViewById(R.id.edtAssName);
                final EditText edtWorth = (EditText) promptView.findViewById(R.id.edtWorth);
                final EditText edtGrade = (EditText) promptView.findViewById(R.id.edtGrade);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        String assName = edtAssName.getText().toString();
                                        String worth = edtWorth.getText().toString();
                                        String grade = edtGrade.getText().toString();
                                        AssignmentValues assignmentValues = new AssignmentValues();
                                        Cursor c = db.getUnit(unitName);
                                        if(c.moveToFirst()){
                                        assignmentValues.setUnitName(c.getInt(c.getColumnIndex(Scheme.Unit.ID)));}
                                        assignmentValues.setName(assName);
                                        assignmentValues.setGrade(Integer.parseInt(grade));
                                        assignmentValues.setWorth(Integer.parseInt(worth));
                                        db.createAssignment(assignmentValues);
                                        Fragment_Assignment fa = (Fragment_Assignment) getSupportFragmentManager().findFragmentByTag("fragment_assignment");
                                        fa.LoadListAssignment();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                //create alert dialog
                AlertDialog alertdialog = alertDialogBuilder.create();

                //show it
                alertdialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           //Respon to the action bar's Up/Home button
           case android.R.id.home:
               NavUtils.navigateUpFromSameTask((this));
               return true;
       }

        return super.onOptionsItemSelected(item);
    }
}

