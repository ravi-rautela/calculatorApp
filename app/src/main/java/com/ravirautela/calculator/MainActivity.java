package com.ravirautela.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView one,two,three,four,five,six,seven,eight,nine,zero,equal,point,divide,mul,add,minus,AC,clear;
    TextView operation, result;
    String input = "", output;
    ArrayList<String> operands = new ArrayList<String>();
    double finalResult= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        defining the hooks
        defineHook();
        // Set listener here.
        setListener();
        result.setVisibility(View.GONE);
        operation.setText("0");
    }

    private void setListener() {
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        equal.setOnClickListener(this);
        divide.setOnClickListener(this);
        mul.setOnClickListener(this);
        minus.setOnClickListener(this);
        add.setOnClickListener(this);
        point.setOnClickListener(this);
        clear.setOnClickListener(this);
        AC.setOnClickListener(this);
    }

    private void defineHook() {
//         Creating the hook here.
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        equal = findViewById(R.id.equal);
        divide = findViewById(R.id.divide);
        mul = findViewById(R.id.multiplication);
        point = findViewById(R.id.point);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        AC = findViewById(R.id.AllClear);
        clear = findViewById(R.id.Arrow);
//      Result part is here now.
        operation = findViewById(R.id.operation);
        result = findViewById(R.id.result);
    }

    @Override
    public void onClick(View view) {
        if(view == zero){
            operation("0");
        }
        else if(view == one){
            operation("1");
        }
        else if(view == two){
            operation("2");
        }
        else if(view == three){
            operation("3");
        }
        else if(view == four){
            operation("4");
        }
        else if(view == five){
            operation("5");
        }
        else if(view == six){
            operation("6");
        }
        else if(view == seven){
            operation("7");
        }
        else if(view == eight){
            operation("8");
        }
        else if(view == nine){
            operation("9");
        }
        else if(view == equal){
            operation("result");
        }
        else if(view == divide){
            operation(" / ");
        }
        else if(view == mul){
            operation(" x ");
        }
        else if(view == add){
            operation(" + ");
        }
        else if(view == minus){
            operation(" - ");
        }
        else if(view == point){
            operation(".");
        }
        else if(view == clear){
            operation("<-");
        }
        else if(view == AC){
            operation("AC");
        }
    }

    private void operation(String s) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(80);
        if(s.equals("AC")){
            input = "";
        }
        else if(s.equals("<-")){
            // 1. Debug-ing the code here
            if(!input.equals("")){
                Character last = input.charAt(input.length()-1);
                if(last.equals(' ')){
                    input =  input.substring(0,input.length()-2);
                }
                input =  input.substring(0,input.length()-1);
            }
        }else if(s.equals("result")){
            getResult();
        }
        else{
            boolean isTwiceOperator = false;
            String prev = "";
            if(input.length()>1){
                prev = input.substring(input.length()-1);
            }
            if((prev.equals(" ") || prev.equals(".")) && (s.equals(" + ") || s.equals(" - ") ||s.equals(" / ") ||s.equals(" X ") ||s.equals(".") )) {
                isTwiceOperator = true;
            }
            if(input.equals("") && (s.equals(" + ") || s.equals(" - ") ||s.equals(" % ") ||s.equals(" X ") ||s.equals(".") )){
                isTwiceOperator=true;
            }
            if(!isTwiceOperator){
                input = input + s;
            }
        }
        operation.setText(input);
    }

    private void getResult() {
        result.setVisibility(View.VISIBLE);
        operands.clear();
        String copy = input;
        copy = copy + " ";
        int len = copy.length();
        String value = "";
        Character ch;
        for (int i = 0; i < len; i++) {
            ch = copy.charAt(i);
            if(ch.equals(' ')){
                value = value.trim();
                operands.add(value);
                value = "";
            }
            value = value + ch;
        }
        getAnswer("/");
        getAnswer("X");
        getAnswer("+");
        getAnswer("-");
        finalResult=Double.parseDouble(operands.get(0));
        DecimalFormat formatter = new DecimalFormat("#.##########");
        result.setText(String.valueOf(formatter.format(finalResult)));
    }

    private void getAnswer(String operator) {
        int len = operands.size();
        for(int j=0;j<len;j++){
            int size = operands.size();
            for(int i=0;i<size;i++) {
                if (operands.get(i).equals(operator)) {
                    if (operands.get(i).equals("/")) {
                        //perform division
                        finalResult = Double.parseDouble(operands.get(i - 1)) / Double.parseDouble(operands.get(i + 1));
                    } else if (operands.get(i).equals("X")) {
                        //perform multiplication
                        finalResult = Double.parseDouble(operands.get(i - 1)) * Double.parseDouble(operands.get(i + 1));
                    } else if (operands.get(i).equals("+")) {
                        //perform addition
                        finalResult = Double.parseDouble(operands.get(i - 1)) + Double.parseDouble(operands.get(i + 1));
                    } else if (operands.get(i).equals("-")) {
                        //perform subtraction
                        finalResult = Double.parseDouble(operands.get(i - 1)) - Double.parseDouble(operands.get(i + 1));
                    }
                    operands.remove(i - 1);
                    operands.add(i-1,String.valueOf(finalResult));
                    operands.remove(i + 1);
                    operands.remove(i);
                    break;
                }
            }
        }
    }
}