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
import android.widget.Spinner;

import java.util.ArrayList;

import uff.simonalzheimer.app.Condition;
import uff.simonalzheimer.app.R;

;

/**
 * Created by Juan Lucas Vieira on 07/09/2017.
 */

public class ActionPopUp extends DialogFragment {

    private Bundle args;

    Spinner action_spinner;

    ArrayAdapter<String> values_adapter;
    ArrayList<String> valuesOfKey;

    Button ok_pick_btn;

    public interface ActionPopUpListener {
        void onFinishAction(String action);
    }

    public ActionPopUp(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        args = getArguments();

        ArrayList<String> keys = args.getStringArrayList("actions");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.action_popup, null);

        action_spinner = (Spinner) v.findViewById(R.id.action_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, keys);
        action_spinner.setAdapter(adapter);

        ok_pick_btn = (Button) v.findViewById(R.id.ok_act_btn);

        ok_pick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResultToActivity();
                dismiss();
            }
        });

        Button cancel_btn = (Button) v.findViewById(R.id.cancel_act_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(v);

        return builder.create();
    }

    private void sendResultToActivity(){
        ActionPopUpListener activity = (ActionPopUpListener) getActivity();
        activity.onFinishAction(action_spinner.getSelectedItem().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
