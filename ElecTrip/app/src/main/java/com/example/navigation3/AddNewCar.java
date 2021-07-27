package com.example.navigation3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.validation.Validator;


public class AddNewCar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String manufacturerText, modelText, plateText, capacityText, yearOfFabricationText, averageConsumptionText, typeText, subTypeText;
    TextView result, title;
    AutoCompleteTextView manufacturerEdit, modelEdit;
    EditText plateEdit, capacityEdit, yearOfFabricationEdit, averageConsumptionEdit;
    Button submitButton, backButton;

    Spinner typesSpinner;
    Spinner subTypesSpinner;

    ArrayAdapter<CharSequence> chargeTypesAdapter;
    ArrayAdapter<CharSequence> chargeSubTypesAdapter;
    ArrayAdapter<String> manufacturersAdapter;
    ArrayAdapter<String> modelsAdapter;
    String[] manufacturers;
    String[] models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        manufacturerEdit = findViewById(R.id.manufacturerEdit);
        modelEdit = findViewById(R.id.modelEdit);
        plateEdit = findViewById(R.id.plateEdit);
        capacityEdit = findViewById(R.id.capacityEdit);
        result = findViewById(R.id.tvResult);
        typesSpinner = findViewById(R.id.chargeTypeSpinner);
        subTypesSpinner = findViewById(R.id.chargeSubTypeSpinner);
        yearOfFabricationEdit = findViewById(R.id.yearOfFabricationEdit);
        averageConsumptionEdit = findViewById(R.id.averageConsumptionEdit);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);

        manufacturers = getResources().getStringArray(R.array.manufacturers);
        models = getResources().getStringArray(R.array.No_model);

        manufacturersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, manufacturers);
        manufacturerEdit.setAdapter(manufacturersAdapter);

        manufacturerEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                manufacturerText = manufacturerEdit.getText().toString();
                Toast.makeText(parent.getContext(), manufacturerText, Toast.LENGTH_SHORT).show();

                switch (manufacturerText) {
                    case "Audi":
                        models = getResources().getStringArray(R.array.Audi);
                        break;
                    case "BMW":
                        models = getResources().getStringArray(R.array.BMW);
                        break;
                    case "Chevrolet":
                        models = getResources().getStringArray(R.array.Chevrolet);
                        break;
                    case "Citroen":
                        models = getResources().getStringArray(R.array.Citroen);
                        break;
                    case "Honda":
                        models = getResources().getStringArray(R.array.Honda);
                        break;
                    case "Hyundai":
                        models = getResources().getStringArray(R.array.Hyundai);
                        break;
                    case "Jaguar_Land_Rover":
                        models = getResources().getStringArray(R.array.Jaguar_Land_Rover);
                        break;
                    case "Kia":
                        models = getResources().getStringArray(R.array.Kia);
                        break;
                    case "Kyburz":
                        models = getResources().getStringArray(R.array.Kyburz);
                        break;
                    case "Lightning":
                        models = getResources().getStringArray(R.array.Lightning);
                        break;
                    case "Mahindra":
                        models = getResources().getStringArray(R.array.Mahindra);
                        break;
                    case "Mercedes_Benz":
                        models = getResources().getStringArray(R.array.Mercedes_Benz);
                        break;
                    case "Micro_Mobility_Systems":
                        models = getResources().getStringArray(R.array.Micro_Mobility_Systems);
                        break;
                    case "Mitsubishi":
                        models = getResources().getStringArray(R.array.Mitsubishi);
                        break;
                    case "Motores_Limpios":
                        models = getResources().getStringArray(R.array.Motores_Limpios);
                        break;
                    case "MW_Motors":
                        models = getResources().getStringArray(R.array.MW_Motors);
                        break;
                    case "NIO":
                        models = getResources().getStringArray(R.array.NIO);
                        break;
                    case "Nissan":
                        models = getResources().getStringArray(R.array.Nissan);
                        break;
                    case "ECOmove":
                        models = getResources().getStringArray(R.array.ECOmove);
                        break;
                    case "Peugeot":
                        models = getResources().getStringArray(R.array.Peugeot);
                        break;
                    case "Rayttle":
                        models = getResources().getStringArray(R.array.Rayttle);
                        break;
                    case "Renault":
                        models = getResources().getStringArray(R.array.Renault);
                        break;
                    case "SEAT":
                        models = getResources().getStringArray(R.array.SEAT);
                        break;
                    case "Skoda":
                        models = getResources().getStringArray(R.array.Skoda);
                        break;
                    case "Smart":
                        models = getResources().getStringArray(R.array.Smart);
                        break;
                    case "Sono_Motors":
                        models = getResources().getStringArray(R.array.Sono_Motors);
                        break;
                    case "Stevens":
                        models = getResources().getStringArray(R.array.Stevens);
                        break;
                    case "Tesla":
                        models = getResources().getStringArray(R.array.Tesla);
                        break;
                    case "Venturi":
                        models = getResources().getStringArray(R.array.Venturi);
                        break;
                    case "Volkswagen":
                        models = getResources().getStringArray(R.array.Volkswagen);
                        break;
                }


                modelsAdapter = new ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_list_item_1, models);
                modelEdit.setAdapter(modelsAdapter);

            }


        });


        chargeTypesAdapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        chargeTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typesSpinner.setAdapter(chargeTypesAdapter);
        typesSpinner.setSelection(0);

        //typesSpinner.setOnItemSelectedListener(this);

        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                typeText = parentView.getItemAtPosition(position).toString();

                if (position == 1) {
                    chargeSubTypesAdapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.rapid, android.R.layout.simple_spinner_item);
                    chargeSubTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else if (position == 2) {
                    chargeSubTypesAdapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.fast, android.R.layout.simple_spinner_item);
                    chargeSubTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else if (position == 3) {
                    chargeSubTypesAdapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.slow, android.R.layout.simple_spinner_item);
                    chargeSubTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }

                subTypesSpinner.setAdapter(chargeSubTypesAdapter);
                subTypesSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        subTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> childView, View selectedItemView, int position, long id) {
                subTypeText = childView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                Toast.makeText(submitButton.getContext(), "SUBMIT", Toast.LENGTH_SHORT).show();
                manufacturerText = manufacturerEdit.getText().toString();
                if (!manufacturerText.equals("")) {
                    count++;
                }
                modelText = modelEdit.getText().toString();
                if (!modelText.equals("")) {
                    count++;
                }
                plateText = plateEdit.getText().toString();
                if (!plateText.equals("")) {
                    count++;
                }
                capacityText = capacityEdit.getText().toString();
                if (!capacityText.equals("")) {
                    count++;
                }
                yearOfFabricationText = yearOfFabricationEdit.getText().toString();
                if (!yearOfFabricationText.equals("")) {
                    count++;
                }
                averageConsumptionText = averageConsumptionEdit.getText().toString();
                if (!averageConsumptionText.equals("")) {
                    count++;
                }

                if (count < 6) {
                    System.out.println("INVALID==============================");
                } else {
                    Car myCar = new Car(manufacturerText, modelText, plateText, capacityText, yearOfFabricationText, averageConsumptionText, typeText, subTypeText);
                    myCar.afisare();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
