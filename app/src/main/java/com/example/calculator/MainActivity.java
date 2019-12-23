package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout s_frag;
    TextView res;
    String str = "";
    boolean canFloat;
    final static String key = "viewString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s_frag = findViewById(R.id.s_frag_LL);
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

    public void ChangeMode(View view){
        if (s_frag.getVisibility() == View.INVISIBLE)
            s_frag.setVisibility(View.VISIBLE);
        else
            s_frag.setVisibility(View.INVISIBLE);
    }

    public void ClearBut(View view){
        str = "";
        canFloat = true;
        res.setText(str);
    }

    public void onNumClick(View view){
        Button button = (Button)view;
        if (str.length() == 0 || (str.charAt(str.length()-1) != 'e' && str.charAt(str.length()-1) != 'i')) {
            str += button.getText();
            res.setText(str);
        }
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
        else if (!get.equals(",") && str.length() > 0 && (CheckNum(str) ||
                str.charAt(str.length()-1) == '!' ||
                str.charAt(str.length()-1) == ')' ||
                str.charAt(str.length()-1) == 'e' ||
                str.charAt(str.length()-1) == 'i')){
            str += button.getText();
            canFloat = true;
        }
        res.setText(str);
    }

    public void onParenClick(View view){
        Button button = (Button)view;
        String get = "";
        get += button.getText();
        if (str.length() == 0 || str.charAt(str.length()-1) != '.') {
            str += get;
            res.setText(str);
        }
    }

    public void onTrigClick(View view){
        Button button = (Button)view;
        String get = "";
        get += button.getText();
        if (str.length() == 0 || (!CheckNum(str) && str.charAt(str.length()-1) != '.')){
            if (get.equals("√"))
                get = "sqrt";
            str += get + "(";
            res.setText(str);
        }
    }

    public void onConstClick(View view){
        Button button = (Button)view;
        if (str.length() == 0 || (!CheckNum(str) && str.charAt(str.length()-1) != '.')){
            str += button.getText();
            res.setText(str);
        }
    }

    public void onFactClick(View view){
        Button button = (Button)view;
        if (str.length() > 0 && CheckNum(str)){
            str += button.getText();
            res.setText(str);
        }
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
