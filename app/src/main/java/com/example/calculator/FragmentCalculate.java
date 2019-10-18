package com.example.calculator;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class FragmentCalculate extends Fragment {

    EditText editText;
    static String TAG = "FragmentCalculate";
    private String operator;
    private int cursorPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        editText = view.findViewById(R.id.showNumbers);
        editText.setShowSoftInputOnFocus(false);
        return view;
    }

    public void displayNumbers(String name) {
        //  Toast.makeText(getContext(), "Fragment Calculte" + name, Toast.LENGTH_SHORT).show();
        cursorPosition = editText.getSelectionStart();
        String editTextValue = editText.getText().toString();
        switch (name) {
            case "clear":
                if (cursorPosition >= 1) {
                    // editText.getText().delete(num - 1, num);
                    StringBuilder stringBuilder = new StringBuilder(editText.getText());
                    stringBuilder.replace(cursorPosition - 1, cursorPosition, "");
                    editText.setText(stringBuilder);
                    editText.setSelection(cursorPosition - 1);
                }
                break;
            case "+":
            case "-":
            case "x":
            case "/":
                operator = name;
                boolean checkSame = true;
                if (editTextValue.equals("")) {
                    break;
                }
                String arrOperator[] = {"+", "-", "/", "x"};
                for (String i : arrOperator) {
                    if (editTextValue.charAt(0) == '-') {

                    }
                    if (editTextValue.contains(i)) {
                        checkSame = true;
                        int checkSameOperator = editTextValue.indexOf(i);
                        editTextValue = editTextValue.replace(String.valueOf(editTextValue.charAt(checkSameOperator)), operator);
                        editText.setText(editTextValue);
                        editText.setSelection(editText.getText().length());
                        break;
                    } else {
                        checkSame = false;
                    }
                }
                if (!checkSame) {
                    editText.getText().insert(cursorPosition, name);
                }
                break;
            case "=":
//                Log.d(TAG, operator);
                getOperatorandOperand(operator);
                editText.setSelection(editText.getText().length());

                break;
            case "clearAll":
                editText.setText("");
                break;
            case ".":
                try {
                    if ((editTextValue.equals("") || !editTextValue.contains(".") ||
                            (!editTextValue.contains("."))) && !editTextValue.contains(operator)) {
                        if (!editTextValue.equals("")) {
                            editText.getText().insert(cursorPosition, name);
                        } else {
                            editText.getText().insert(cursorPosition, "0" + name);
                        }
                        break;
                    }

                    if (editTextValue.contains(operator)) {
                        int operatorIndex = editText.getText().toString().indexOf(operator);
                        String dot = editTextValue.substring(operatorIndex + 1);
                        if (!dot.contains(".")) {
                            if (!dot.equals("")) {
                                editText.getText().insert(cursorPosition, name);
                                break;
                            } else {
                                editText.getText().insert(cursorPosition, "0" + name);
                                break;
                            }
                        }
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case "0":
                try {
                    if (editTextValue.equals("") || (!isNumeric(editTextValue) && !editTextValue.equals("0") || !editTextValue.contains(operator))) {
                        editText.getText().insert(cursorPosition, name);
                        break;
                    }
                    if (!editTextValue.equals("0") && !editTextValue.contains(operator)) {
                        editText.getText().insert(cursorPosition, name);
                        break;
                    }
                    if (editTextValue.contains(operator)) {
                        int operatorIndex = editTextValue.indexOf(operator);
                        String zeros = editTextValue.substring(operatorIndex + 1);
                        if (!zeros.equals("0")) {
                            editText.getText().insert(cursorPosition, name);
                            break;
                        }
                    }
                    //Toast.makeText(getContext(), "Else block", Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            default:
                editText.getText().insert(cursorPosition, name);
                break;
        }
    }

    public static boolean isNumeric(String strNum) {
        Log.d(TAG, "is Numeric checking");
        return strNum.matches("-\\d+(\\.\\d+)");
    }

    public void getOperatorandOperand(String operator) {
        try {
            BigDecimal operand1, operand2;
            String numberFromEdittext = editText.getText().toString();
            int index = numberFromEdittext.indexOf(operator);
            if (numberFromEdittext.contains(".")) {
                operand1 = new BigDecimal(numberFromEdittext.substring(0, index));
                operand2 = new BigDecimal(numberFromEdittext.substring(index + 1));
            } else {
                operand1 = new BigDecimal(numberFromEdittext.substring(0, index));
                operand2 = new BigDecimal(numberFromEdittext.substring(index + 1));
            }

            performCalculation(operand1, operand2, operator);
        } catch (Exception e) {

        }
    }

    public void performCalculation(BigDecimal operand1, BigDecimal operand2, String operator) {
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        boolean notDivisible = false;
        // Toast.makeText(getContext(), "operand1" + " " + operand1 + "operand2" + " " + operand2 + "operator" + " " + operator, Toast.LENGTH_LONG).show();
        if (operator.equals("+")) {
            result = operand1.add(operand2);
            format(result);
        } else if (operator.equals("-")) {
            result = operand1.subtract(operand2);
            format(result);
        } else if (operator.equals("x")) {
            if (operand2.equals(BigDecimal.ZERO)) {
                result = new BigDecimal(BigInteger.ZERO);
            } else {
                result = operand1.multiply(operand2);
                format(result);
            }
        } else if (operator.equals("/")) {
            BigDecimal b1 = new BigDecimal(1);
            if (b1.multiply(operand2).equals(BigDecimal.ZERO)) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Division by zero not applicable", Snackbar.LENGTH_SHORT).show();
                notDivisible = true;
            } else {
                result = operand1.divide(operand2, MathContext.DECIMAL32);
                format(result);
            }
        }
        if (notDivisible) {
            editText.setText(" ");
        } else {
            editText.setText(String.valueOf(result));
        }
        //Toast.makeText(getContext(), "Result is" + result, Toast.LENGTH_SHORT).show();
    }

    String format(BigDecimal num) {
        NumberFormat formatter = new DecimalFormat("0.0E0");
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        formatter.setMinimumFractionDigits((num.scale() > 0) ? num.precision() : num.scale());
        return formatter.format(num);
    }

}
