package com.example.calculator;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements Fragmentlistener {

    FragmentCalculate fragmentCalculate;
    FragmentNumbers fragmentNumbers;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentCalculate = new FragmentCalculate();
        fragmentNumbers = new FragmentNumbers();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_calculate, fragmentCalculate);
        fragmentTransaction.add(R.id.fragment_numbers,fragmentNumbers);
        fragmentTransaction.commit();
    }

    @Override
    public void sendData(String name) {
        // Toast.makeText(MainActivity.this, "mainActvity" + name, Toast.LENGTH_SHORT).show();
        /*if(name!=null){
            fragmentCalculate.displayNumbers(name);
        }*/
        fragmentCalculate.displayNumbers(name);
    }

}
