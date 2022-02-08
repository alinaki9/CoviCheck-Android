package com.mc2022.template;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    private Button check;
    private Button back;
    private int yeses;
    private String res1;
    private String res2;
    private String res3;
    private String res4;
    private String res5;
    private String res6;
    private String res7;
    private SharedPreferences settings1;
    private SharedPreferences.Editor editor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        //loading Shared Values

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        yeses = (mSharedPreference.getInt("Yeses", 0));

        res1 = (mSharedPreference.getString("Response1", null));
        res2 = (mSharedPreference.getString("Response2", null));
        res3 = (mSharedPreference.getString("Response3", null));
        res4 = (mSharedPreference.getString("Response4", null));
        res5 = (mSharedPreference.getString("Response5", null));
        res6 = (mSharedPreference.getString("Response6", null));
        res7 = (mSharedPreference.getString("Response7", null));

        Log.i("lifecycle", "Number of Yeses: " + Integer.toString(yeses));


        //shared preferences for settings

        settings1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (settings1.getInt("onPause1", 0) == 1){

            Log.i("lifecycle", "State of Activity2 changed from Pause to Create!");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed from Pause to Create!", Toast.LENGTH_SHORT).show();
        }

        else if (settings1.getInt("onStop1", 0) == 1){

            Log.i("lifecycle", "State of Activity2 changed from Stop to Create!");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed from Stop to Create!", Toast.LENGTH_SHORT).show();

        }

        else {

            Log.i("lifecycle", "State of Activity2 changed to Create!");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed to Create!", Toast.LENGTH_SHORT).show();

        }

        editor2 = settings1.edit();
        editor2.putInt("onCreate1", 1);
        editor2.putInt("onStart1", 0);
        editor2.putInt("onResume1", 0);
        editor2.putInt("onPause1", 0);
        editor2.putInt("onStop1", 0);
        editor2.putInt("onRestart1", 0);
        editor2.putInt("onDestroy1", 0);
        editor2.commit();

        //assigning ids to objects
        check = findViewById(R.id.check);
        back = findViewById(R.id.go_back);
        back.setVisibility(View.INVISIBLE);


        TextView aans1 = findViewById(R.id.ans1);
        aans1.setText(res1);

        TextView aans2 = findViewById(R.id.ans2);
        aans2.setText(res2);

        TextView aans3 = findViewById(R.id.ans3);
        aans3.setText(res3);

        TextView aans4 = findViewById(R.id.ans4);
        aans4.setText(res4);

        TextView aans5 = findViewById(R.id.ans5);
        aans5.setText(res5);

        TextView aans6 = findViewById(R.id.ans6);
        aans6.setText(res6);

        TextView aans7 = findViewById(R.id.ans7);
        aans7.setText(res7);

        //transfer control to onClick()
        check.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.check:

                back.setVisibility(View.VISIBLE);
                TextView check_display = findViewById(R.id.check_display);
                if (yeses >= 3) {
                    check_display.setText("You might have Covid, please do a Covid Test!");
                    break;
                }

                check_display.setText("Congrats, you do not have Covid!");
                break;

            case R.id.go_back:

                Intent activityMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activityMainIntent);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle","onStart invoked");

        if (settings1.getInt("onCreate1", 0) == 1){

            Log.i("lifecycle","State of Activity2 changed from Create to Start! ");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed from Create to Start!", Toast.LENGTH_SHORT).show();

        }
        else if (settings1.getInt("onRestart1", 0) == 1){

            Log.i("lifecycle","State of Activity2 changed from Restart to Start! ");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed from Restart to Start!", Toast.LENGTH_SHORT).show();

        }

        editor2.putInt("onPause1", 0);
        editor2.putInt("onStart1", 1);
        editor2.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle","onResume invoked");

        if (settings1.getInt("onStart1", 0 ) == 1 ){

            Log.i("lifecycle","State of Activity2 changed from Start to Resume! ");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed from Start to Resume!", Toast.LENGTH_SHORT).show();

        }

        else if (settings1.getInt("onPause1", 0) == 1){

            Log.i("lifecycle","State of Activity2 changed from Pause to Resume! ");
            Toast.makeText(getApplicationContext(), "State of Activity2 changed from Pause to Resume!", Toast.LENGTH_SHORT).show();

        }

        editor2.putInt("onResume1", 1);
        editor2.commit();

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle","onPause invoked");


        Log.i("lifecycle","State of MainActivity changed from Resume to Pause! ");
        Toast.makeText(getApplicationContext(), "State of Activity2 changed from Resume to Pause!", Toast.LENGTH_SHORT).show();

        editor2.putInt("onStart1", 0);
        editor2.putInt("onStop1", 0);
        editor2.putInt("onPause1", 1);
        editor2.commit();

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle","onStop invoked");

        Log.i("lifecycle","State of Activity2 changed from Pause to Stop!");
        Toast.makeText(getApplicationContext(), "State of Activity2 changed from Pause to Stop!", Toast.LENGTH_SHORT).show();

        editor2.putInt("onPause1", 0);
        editor2.putInt("onStop1", 1);
        editor2.commit();

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifecycle","onRestart invoked");


        Log.i("lifecycle","State of Activity2 changed from Stop to Restart!");

        Toast.makeText(getApplicationContext(), "State of Activity2 changed from Stop to Restart!", Toast.LENGTH_SHORT).show();

        editor2.putInt("onCreate1", 0);
        editor2.putInt("onRestart1", 1);
        editor2.commit();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle","onDestroy invoked");


        Log.i("lifecycle","State of Activity2 changed from Stop to Destroy");
        Toast.makeText(getApplicationContext(), "State of Activity2 changed from Stop to Destroy!", Toast.LENGTH_SHORT).show();

    }

}
