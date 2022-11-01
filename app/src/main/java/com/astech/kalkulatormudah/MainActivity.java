package com.astech.kalkulatormudah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonPlusMinus, buttonPersen;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAc, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.button_c);
        assignId(buttonPlusMinus, R.id.button_plus_minus);
        assignId(buttonPersen, R.id.button_persen);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonEquals, R.id.button_equals);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAc, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);

    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("±")) {
            if (dataToCalculate.endsWith(")")) {
                dataToCalculate = dataToCalculate.replace("(-", "");
                dataToCalculate = dataToCalculate.replace(")", "");
            } else if (dataToCalculate.isEmpty()){
                dataToCalculate = "";
            } else {
                dataToCalculate = "(-" + dataToCalculate + ")";
            }
        }

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if (buttonText.equals("=")) {

            if (dataToCalculate.contains("%")) {
                dataToCalculate = dataToCalculate.replace("%", "/100");

                String finalResult = getResult(dataToCalculate);

                if (!finalResult.equals("Err")) {
                    resultTv.setText(finalResult);
                    solutionTv.setText(resultTv.getText());
                } else {
                    resultTv.setText("Error");
                }
            } else {
                String finalResult = getResult(dataToCalculate);

                if (!finalResult.equals("Err")) {
                    resultTv.setText(finalResult);
                    solutionTv.setText(resultTv.getText());
                } else {
                    resultTv.setText("Error");
                }
            }

            return;
        }

        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
            }
        } else {
            if (!buttonText.equals("±")) {
                dataToCalculate = dataToCalculate + buttonText;
            }
        }

        if (dataToCalculate.startsWith("-") || dataToCalculate.startsWith("+") ||
                dataToCalculate.startsWith("*") || dataToCalculate.startsWith("/") ||
                dataToCalculate.startsWith("%")) {
            dataToCalculate = dataToCalculate.substring(1);
        }

        if (dataToCalculate.startsWith("0")) {
            if (!dataToCalculate.startsWith("0.")) {
                if (buttonText.equals("0")) {
                    if (!dataToCalculate.startsWith("0.0")) {
                        dataToCalculate = dataToCalculate.replace("00", "0");
                    }
                } else if (!buttonText.equals("0") && !buttonText.equals(".") ) {
                    dataToCalculate = dataToCalculate.substring(1);
                }
            }
        }

        solutionTv.setText(dataToCalculate);

        if (dataToCalculate.length() > 0) {

            if (dataToCalculate.contains("%")) {
                dataToCalculate = dataToCalculate.replace("%", "/100");

                String finalResult = getResult(dataToCalculate);

                if (!finalResult.equals("Err")) {
                    resultTv.setText(finalResult);
                }
            } else {
                String finalResult = getResult(dataToCalculate);

                if (!finalResult.equals("Err")) {
                    resultTv.setText(finalResult);
                }
            }
        }

    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1,null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}