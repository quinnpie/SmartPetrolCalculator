package com.example.smartpetrolcalculator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorFragment extends Fragment {

    Spinner spinnerPetrol;

    EditText editPrice, editUsage;

    RadioGroup radioGroup;
    RadioButton radioYes, radioNo;

    Button btnCalculate, btnShare;

    TextView txtTotal, txtRebate, txtFinal;

    TextView txtEligibilityNote;

    String shareMessage = "";

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_calculator,
                container,
                false);

        // CONNECT UI

        spinnerPetrol = view.findViewById(R.id.spinnerPetrol);

        editPrice = view.findViewById(R.id.editPrice);
        editUsage = view.findViewById(R.id.editUsage);

        radioGroup = view.findViewById(R.id.radioGroup);

        radioYes = view.findViewById(R.id.radioYes);
        radioNo = view.findViewById(R.id.radioNo);

        btnCalculate = view.findViewById(R.id.btnCalculate);
        btnShare = view.findViewById(R.id.btnShare);

        txtTotal = view.findViewById(R.id.txtTotal);
        txtRebate = view.findViewById(R.id.txtRebate);
        txtFinal = view.findViewById(R.id.txtFinal);
        txtEligibilityNote = view.findViewById(R.id.txtEligibilityNote);

        // SPINNER DATA

        String[] petrolTypes = {
                "RON95",
                "RON97",
                "Diesel"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        petrolTypes
                );

        spinnerPetrol.setAdapter(adapter);

        spinnerPetrol.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selectedPetrol =
                                spinnerPetrol.getSelectedItem().toString();

                        if (selectedPetrol.equals("RON95")) {

                            editPrice.setText("3.87");

                            radioYes.setEnabled(true);
                            radioNo.setEnabled(true);

                            txtEligibilityNote.setText("RON95 users may qualify for BUDI MADANI rebate.");

                        }

                        else if (selectedPetrol.equals("RON97")) {

                            editPrice.setText("4.70");

                            radioNo.setChecked(true);

                            radioYes.setEnabled(false);
                            radioNo.setEnabled(false);

                            txtEligibilityNote.setText("RON97 is not eligible for BUDI MADANI rebate.");

                        }

                        else if (selectedPetrol.equals("Diesel")) {

                            editPrice.setText("4.87");

                            radioNo.setChecked(true);

                            radioYes.setEnabled(false);
                            radioNo.setEnabled(false);

                            txtEligibilityNote.setText("Diesel is not eligible for BUDI MADANI rebate.");

                        }
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {

                    }
                });



        // CALCULATE BUTTON

        btnCalculate.setOnClickListener(v -> {

            try {

                String petrolType =
                        spinnerPetrol.getSelectedItem().toString();

                double price =
                        Double.parseDouble(
                                editPrice.getText().toString());

                double usage =
                        Double.parseDouble(
                                editUsage.getText().toString());

                // TOTAL COST

                double totalCost = usage * price;

                // REBATE

                double rebate = 0;

                if (petrolType.equals("RON95")
                        && radioYes.isChecked()) {

                    rebate = usage * 1.99;
                }

                // FINAL PAYABLE

                double finalPayable =
                        totalCost - rebate;

                // DISPLAY RESULTS

                txtTotal.setText(
                        "Total Cost: RM "
                                + String.format("%.2f",
                                totalCost));

                txtRebate.setText(
                        "BUDI Rebate: RM "
                                + String.format("%.2f",
                                rebate));

                txtFinal.setText(
                        "Total Savings: RM "
                                + String.format("%.2f",
                                finalPayable));

                // SHARE MESSAGE

                shareMessage =
                        "Smart Petrol Calculator\n\n"
                                + "Petrol Type: "
                                + petrolType
                                + "\nTotal Cost: RM "
                                + String.format("%.2f",
                                totalCost)
                                + "\nBUDI Rebate: RM "
                                + String.format("%.2f",
                                rebate)
                                + "\nFinal Payable: RM "
                                + String.format("%.2f",
                                finalPayable);

            }

            catch (Exception e) {

                Toast.makeText(
                        requireContext(),
                        "Please fill all fields correctly.",
                        Toast.LENGTH_SHORT).show();
            }

        });

        // SHARE BUTTON

        btnShare.setOnClickListener(v -> {

            if (shareMessage.isEmpty()) {

                Toast.makeText(
                        requireContext(),
                        "Please calculate first.",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            Intent sendIntent = new Intent();

            sendIntent.setAction(Intent.ACTION_SEND);

            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    shareMessage);

            sendIntent.setType("text/plain");

            Intent shareIntent =
                    Intent.createChooser(
                            sendIntent,
                            "Share via");

            startActivity(shareIntent);

        });

        return view;
    }
}