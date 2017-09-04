package teco.gradetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.FeatureGroupInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import teco.gradetracker.Database.DbHelper;
import teco.gradetracker.Database.UnitValues;


public class MainActivity extends AppCompatActivity {

    final Context context = this;
    public String TAG = "Testing Main";

    private Fragment_Unit fragment_unit;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(context);

        //final TextView result = (TextView) findViewById(R.id.txtResult);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG,"start Fragment Manager");
        fragment_unit = new Fragment_Unit();
        getSupportFragmentManager().beginTransaction()
                                    .add(R.id.container_fragment_main,fragment_unit,"fragment_unit")
                                    .addToBackStack("fragment_unit")
                                    .commit();
        Log.d(TAG,"finish Fragment Manager");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                // get prompt.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptView = li.inflate(R.layout.prompt,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompt.xml to alertDialog builder
                alertDialogBuilder.setView(promptView);

                final EditText userInput = (EditText) promptView.findViewById(R.id.edtUnit);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         .setPositiveButton("OK",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        String temp = userInput.getText().toString();
                                        Log.d(TAG,temp);
                                        UnitValues unit = new UnitValues();
                                        unit.setName(temp);
                                        db.createUnit(unit);
                                        Fragment_Unit fu;
                                        fu = (Fragment_Unit) getSupportFragmentManager().findFragmentByTag("fragment_unit");
                                        fu.LoadListUnits();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
