package com.example.calculator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentNumbers extends Fragment implements View.OnClickListener {

    TextView one, two, three, four, five, six, seven, eight, nine, zero, dot, equal;
    ImageView clear, divide, multiply, subtract, plus;
    String TAG = "FragmentNumbers";
    private Fragmentlistener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numbers, container, false);

        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        four = view.findViewById(R.id.four);
        five = view.findViewById(R.id.five);
        six = view.findViewById(R.id.six);
        seven = view.findViewById(R.id.seven);
        eight = view.findViewById(R.id.eight);
        nine = view.findViewById(R.id.nine);
        zero = view.findViewById(R.id.zero);
        dot = view.findViewById(R.id.dot);
        equal = view.findViewById(R.id.equal);
        plus = view.findViewById(R.id.plus);
        multiply = view.findViewById(R.id.multiply);
        divide = view.findViewById(R.id.divide);
        subtract = view.findViewById(R.id.subtract);
        clear = view.findViewById(R.id.clear);

        clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClick();
                return true;
            }
        });

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        dot.setOnClickListener(this);
        divide.setOnClickListener(this);
        equal.setOnClickListener(this);
        plus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        subtract.setOnClickListener(this);
        clear.setOnClickListener(this);
        return view;
    }

    private void longClick() {
        mListener.sendData("clearAll");
    }


    @Override
    public void onClick(View view) {
       /* FragmentCalculate fragmentCalculate = new FragmentCalculate();
        Bundle bundle = new Bundle();
        bundle.putString("value", getResources().getResourceEntryName(view.getId()));
        fragmentCalculate.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.fragment_calculate, fragmentCalculate).commit();*/

        switch (view.getId()) {
            case R.id.divide:
                mListener.sendData("/");
                break;
            case R.id.plus:
                mListener.sendData("+");
                break;
            case R.id.multiply:
                mListener.sendData("x");
                break;
            case R.id.subtract:
                mListener.sendData("-");
                break;
            case R.id.equal:
                mListener.sendData("=");
                break;
            case R.id.clear:
                mListener.sendData("clear");
                break;
        }

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            mListener.sendData(textView.getText().toString());
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Fragmentlistener) {
            mListener = (Fragmentlistener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement fragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
