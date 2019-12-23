package com.example.calculator.demo;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.R;

public class DemoActivity extends AppCompatActivity {

    TextView res;
    String str = "";
    boolean canFloat;
    final static String key = "viewString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        res = findViewById(R.id.resultView);
        canFloat = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(key, str);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        str = savedInstanceState.getString(key);
        res = findViewById(R.id.resultView);
        res.setText(str);
    }

    public void ClearBut(View view){
        str = "";
        canFloat = true;
        res.setText(str);
    }

    public void onNumClick(View view){
        Button button = (Button)view;
        str += button.getText();
        res.setText(str);
    }

    public void onButClick(View view){
        Button button = (Button)view;
        String get = "";
        get += button.getText();
        if (get.equals(",") && canFloat && str.length() > 0 && CheckNum(str)){
            canFloat = false;
            str += ".";
        }
        else if (get.equals("-") && str.length() == 0)
        {
            str += button.getText();
            canFloat = true;
        }
        else if (!get.equals(",") && str.length() > 0 && CheckNum(str)){
            str += button.getText();
            canFloat = true;
        }
        res.setText(str);
    }

    public void onResClick(View view){
        Expression expression = new Expression(str);
        String resault = String.valueOf(expression.calculate());
        res.setText(resault);
        str = resault;
        canFloat = false;
    }

    public boolean CheckNum(String string){
        return (string.charAt(string.length()-1) >= '0' && string.charAt(string.length()-1) <= '9');
    }
}
