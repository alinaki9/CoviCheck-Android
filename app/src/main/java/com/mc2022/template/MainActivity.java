package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private Model nameModel;
    private EditText editName;
    private Button submit;
    private RadioGroup radioGroup;
    private RadioButton yes;
    private RadioButton no;
    private Button next;
    private Button clear;
    private Button final_submit;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor1;
    int[] questions = {R.string._fever, R.string._cough, R.string._tired, R.string._pain, R.string._sore, R.string._taste, R.string._heads};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("lifecycle", "onCreate invoked");
        setContentView(R.layout.activity_main);


        //shared preferences for settings
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (settings.getInt("onPause", 0) == 1){

            Log.i("lifecycle", "State of MainActivity changed from Pause to Create!");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed from Pause to Create!", Toast.LENGTH_SHORT).show();
        }

        else if (settings.getInt("onStop", 0) == 1){

            Log.i("lifecycle", "State of MainActivity changed from Stop to Create!");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed from Stop to Create!", Toast.LENGTH_SHORT).show();

        }

        else {

            Log.i("lifecycle", "State of MainActivity changed to Create!");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed to Create!", Toast.LENGTH_SHORT).show();

        }

        editor1 = settings.edit();
        editor1.putInt("onCreate", 1);
        editor1.putInt("onStart", 0);
        editor1.putInt("onResume", 0);
        editor1.putInt("onPause", 0);
        editor1.putInt("onStop", 0);
        editor1.putInt("onRestart", 0);
        editor1.putInt("onDestroy", 0);
        editor1.commit();


        nameModel = new Model();
        nameModel.addObserver(this);

        //assigning ids to objects
        submit = findViewById(R.id.submit_area);
        radioGroup = findViewById(R.id.radio_group);
        View questions1 = findViewById(R.id.ques);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        next = findViewById(R.id.next);
        clear = findViewById(R.id.clear_all);
        final_submit = findViewById(R.id.final_submit);
        editName = findViewById(R.id.edit_text);

        //hiding other views
        questions1.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        final_submit.setVisibility(View.INVISIBLE);
        clear.setVisibility(View.INVISIBLE);

        //transfer control to onClick()

        submit.setOnClickListener(this);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        next.setOnClickListener(this);
        clear.setOnClickListener(this);
        final_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.submit_area:

                nameModel.setName(editName.getText().toString());
                break;

            case R.id.yes:


                TextView ques2 = findViewById(R.id.ques);
                int index = 0;

                for (int i = 0 ; i < questions.length ; ++i){

                    if (ques2.getText().toString().equals(getResources().getString(questions[i]))){

                        index = i;
                    }

                }
                nameModel.clearNoArray(index);
                nameModel.setYesArray(index);
                break;

            case R.id.no:

                TextView ques3 = findViewById(R.id.ques);
                int index1 = 0;

                for (int i = 0 ; i < questions.length ; ++i){

                    if (ques3.getText().toString().equals(getResources().getString(questions[i]))){

                        index1 = i;
                    }

                }
                nameModel.clearYesArray(index1);
                nameModel.setNoArray(index1);
                break;


            case R.id.next:



                if (nameModel.getName().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter your name first! ",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!yes.isChecked() && !no.isChecked()){

                    Toast.makeText(getApplicationContext(),"Please select either Yes or No!",Toast.LENGTH_SHORT).show();
                    return;
                }
                nameModel.setNext(true);


                //changing the textview to the next question
                TextView ques = findViewById(R.id.ques);
                for ( int i = 0 ; i < questions.length - 1 ; ++i){

                    if (ques.getText().toString().equals(getResources().getString(questions[i]))) {

                        ques.setText(getResources().getString(questions[i+1]));
                        break;
                    }
                }

                //removing the next button at the last symptom
                if (ques.getText().toString().equals(getResources().getString(R.string._heads))){

                    next.setVisibility(View.INVISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    final_submit.setVisibility(View.VISIBLE);
                }

                radioGroup.clearCheck();
                break;

            case R.id.clear_all:

                next.setVisibility(View.VISIBLE);
                nameModel.clearAll();
                TextView tv1 = findViewById(R.id.textView7);
                tv1.setText(getResources().getString(R.string.enter_name));

                View namebar = findViewById(R.id.edit_text);
                View sub = findViewById(R.id.submit_area);
                namebar.setVisibility(View.VISIBLE);
                sub.setVisibility(View.VISIBLE);
                editName.setText("");
                TextView questions1 = findViewById(R.id.ques);
                questions1.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                final_submit.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);
                radioGroup.clearCheck();

                TextView ques1 = findViewById(R.id.ques);
                ques1.setText(getResources().getString(questions[0]));
                break;

            case R.id.final_submit:

                if (!yes.isChecked() && !no.isChecked()) {

                    Toast.makeText(getApplicationContext(), "Please select either Yes or No!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //sharedpreferences for using in activity 2

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();

                //saving the number of yeses
                editor.putInt("Yeses", nameModel.getNumberOfYes() );

                //saving the yes array
                editor.putString("Response1", nameModel.getYesString(0));
                editor.putString("Response2", nameModel.getYesString(1));
                editor.putString("Response3", nameModel.getYesString(2));
                editor.putString("Response4", nameModel.getYesString(3));
                editor.putString("Response5", nameModel.getYesString(4));
                editor.putString("Response6", nameModel.getYesString(5));
                editor.putString("Response7", nameModel.getYesString(6));
                editor.apply();

                Intent activity2Intent = new Intent(getApplicationContext(), Activity2.class);
                startActivity(activity2Intent);
                break;

        }


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update(Observable observable, Object o) {

        if (nameModel.getName().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter your name first! ", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getApplicationContext(),"Welcome " + nameModel.getName() + " !",Toast.LENGTH_SHORT).show();

        //hiding edit text and submit button
        View namebar = findViewById(R.id.edit_text);
        namebar.setVisibility(View.INVISIBLE);

        View sub = findViewById(R.id.submit_area);
        sub.setVisibility(View.INVISIBLE);

        TextView tv1 = findViewById(R.id.textView7);
        tv1.setText("Hello " + nameModel.getName() + " !");

        //showing questions,RadioGroup, clear and submit

        View questions2 = findViewById(R.id.ques);
        questions2.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        clear.setVisibility(View.VISIBLE);


    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle","onStart invoked");

        if (settings.getInt("onCreate", 0) == 1){

            Log.i("lifecycle","State of MainActivity changed from Create to Start! ");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed from Create to Start!", Toast.LENGTH_SHORT).show();

        }
        else if (settings.getInt("onRestart", 0) == 1){

            Log.i("lifecycle","State of MainActivity changed from Restart to Start! ");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed from Restart to Start!", Toast.LENGTH_SHORT).show();

        }

        editor1.putInt("onPause", 0);
        editor1.putInt("onStart", 1);
        editor1.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle","onResume invoked");


        if (settings.getInt("onStart", 0 ) == 1 ){

            Log.i("lifecycle","State of MainActivity changed from Start to Resume! ");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed from Start to Resume!", Toast.LENGTH_SHORT).show();

        }

        else if (settings.getInt("onPause", 0) == 1){

            Log.i("lifecycle","State of MainActivity changed from Pause to Resume! ");
            Toast.makeText(getApplicationContext(), "State of MainActivity changed from Pause to Resume!", Toast.LENGTH_SHORT).show();

        }

        editor1.putInt("onResume", 1);
        editor1.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle","onPause invoked");


        Log.i("lifecycle","State of MainActivity changed from Resume to Pause! ");
        Toast.makeText(getApplicationContext(), "State of MainActivity changed from Resume to Pause!", Toast.LENGTH_SHORT).show();

        editor1.putInt("onStart", 0);
        editor1.putInt("onStop", 0);
        editor1.putInt("onPause", 1);
        editor1.commit();

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle","onStop invoked");

        Log.i("lifecycle","State of MainActivity changed from Pause to Stop!");
        Toast.makeText(getApplicationContext(), "State of MainActivity changed from Pause to Stop!", Toast.LENGTH_SHORT).show();

        editor1.putInt("onPause", 0);
        editor1.putInt("onStop", 1);
        editor1.commit();

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifecycle","onRestart invoked");


        Log.i("lifecycle","State of MainActivity changed from Stop to Restart!");

        Toast.makeText(getApplicationContext(), "State of MainActivity changed from Stop to Restart!", Toast.LENGTH_SHORT).show();

        editor1.putInt("onCreate", 0);
        editor1.putInt("onRestart", 1);
        editor1.commit();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle","onDestroy invoked");


        Log.i("lifecycle","State of MainActivity changed from Stop to Destroy");
        Toast.makeText(getApplicationContext(), "State of MainActivity changed from Stop to Destroy!", Toast.LENGTH_SHORT).show();

    }

}