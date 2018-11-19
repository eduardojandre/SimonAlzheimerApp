package uff.simonalzheimer.app.Fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import uff.simonalzheimer.app.Condition;
import uff.simonalzheimer.app.R;;

/**
 * Created by Juan Lucas Vieira on 07/09/2017.
 */

public class ConditionPopUp extends DialogFragment {

    private Bundle args;

    Spinner type_spinner;
    Spinner value_spinner;

    ArrayAdapter<String> values_adapter;
    ArrayList<String> valuesOfKey;

    Button ok_pick_btn;

    public interface ConditionPopUpListener {
        void onFinishCondition(String condition, String value);
    }

    public ConditionPopUp(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        args = getArguments();

        String selectedKey = "";
        ArrayList<String> keys = args.getStringArrayList("keys");

        valuesOfKey = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.condition_popup, null);

        type_spinner = (Spinner) v.findViewById(R.id.type_spinner);
        value_spinner = (Spinner) v.findViewById(R.id.value_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, keys);
        type_spinner.setAdapter(adapter);

        retrieveValues(type_spinner.getSelectedItem().toString());
        values_adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, valuesOfKey);
        value_spinner.setAdapter(values_adapter);

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retrieveValues(type_spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ok_pick_btn = (Button) v.findViewById(R.id.ok_cond_btn);

        ok_pick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResultToActivity();
                dismiss();
            }
        });

        Button cancel_btn = (Button) v.findViewById(R.id.cancel_cond_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(v);

        return builder.create();
    }

    private void retrieveValues(String s) {
        Bundle valuesBundle = args.getBundle("values");
        ArrayList<String> values_aux = valuesBundle.getStringArrayList(s);
        valuesOfKey.clear();
        valuesOfKey.addAll(values_aux);
        Log.d("Key Specified:", s);
        Log.d("Values of Key", valuesOfKey.toString());
        values_adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, valuesOfKey);
        value_spinner.setAdapter(values_adapter);
    }

    private void sendResultToActivity(){
        ConditionPopUpListener activity = (ConditionPopUpListener) getActivity();
        activity.onFinishCondition(type_spinner.getSelectedItem().toString(), value_spinner.getSelectedItem().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
