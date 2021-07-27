package com.example.navigation3;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddChargingPort extends Activity implements OnItemSelectedListener {
    Spinner formatSpinner, powerTypeSpinner, standardSpinner;
    Button buttonSubmit, buttonBack, buttonAddConnector, buttonViewConnectors;
    String addressText, cityText, priceText, maxAmperageText, maxVoltageText, maxElectricPowerText, powerText, formatText, powerTypeText, standardText;
    EditText addressEditText, cityEditText, priceEditText, maxAmperageEditText, maxVoltageEditText, maxElectricPowerEditText, powerEditText;
    Connector[] connectors;
    List<Connector> connectorList = new ArrayList<>();

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter3;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_charging_port);

        addressEditText = findViewById(R.id.address);
        cityEditText = findViewById(R.id.city);
        priceEditText = findViewById(R.id.price);
        maxAmperageEditText = findViewById(R.id.maxAmperage);
        maxVoltageEditText = findViewById(R.id.maxVoltage);
        maxElectricPowerEditText = findViewById(R.id.maxElectricPower);
        powerEditText = findViewById(R.id.power);

        formatSpinner = findViewById(R.id.format);
        powerTypeSpinner = findViewById(R.id.powerType);
        standardSpinner = findViewById(R.id.standard);

        ArrayAdapter<CharSequence> formatAdapter, powerTypeAdapter, standardAdapter;

        formatAdapter = ArrayAdapter.createFromResource(this, R.array.Format, android.R.layout.simple_spinner_item);
        formatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formatSpinner.setAdapter(formatAdapter);
        formatSpinner.setSelection(0);

        powerTypeAdapter = ArrayAdapter.createFromResource(this, R.array.PowerType, android.R.layout.simple_spinner_item);
        powerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        powerTypeSpinner.setAdapter(powerTypeAdapter);
        powerTypeSpinner.setSelection(0);

        standardAdapter = ArrayAdapter.createFromResource(this, R.array.Standard, android.R.layout.simple_spinner_item);
        standardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        standardSpinner.setAdapter(standardAdapter);
        standardSpinner.setSelection(0);

        formatSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formatText = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        powerTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                powerTypeText = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        standardSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                standardText = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonAddConnector = findViewById(R.id.addConnector);

        buttonAddConnector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count2 = 0;
                maxAmperageText = maxAmperageEditText.getText().toString();
                if (!maxAmperageText.equals("")) {
                    count2++;
                }
                maxVoltageText = maxVoltageEditText.getText().toString();
                if (!maxVoltageText.equals("")) {
                    count2++;
                }
                maxElectricPowerText = maxElectricPowerEditText.getText().toString();
                if (!maxElectricPowerText.equals("")) {
                    count2++;
                }
                powerText = powerEditText.getText().toString();
                if (!powerText.equals("")) {
                    count2++;
                }
                if (!formatText.equals("Format")) {
                    count2++;
                }
                if (!powerTypeText.equals("Power type")) {
                    count2++;
                }
                if (!standardText.equals("Standard")) {
                    count2++;
                }

                if (count2 == 7) {
                    Connector newConnector = new Connector();
                    Standard standard = Enum.valueOf(Standard.class, standardText);
                    Format format = Enum.valueOf(Format.class, formatText);
                    PowerType powerType = Enum.valueOf(PowerType.class, powerTypeText);
                    newConnector = new Connector(standard, format, powerType, Integer.valueOf(maxAmperageText), Integer.valueOf(maxVoltageText), Integer.valueOf(maxElectricPowerText), Integer.valueOf(powerText));
                    connectorList.add(newConnector);

                    maxAmperageEditText.setText("");
                    maxVoltageEditText.setText("");
                    maxElectricPowerEditText.setText("");
                    powerEditText.setText("");
                    formatSpinner.setSelection(0);
                    powerTypeSpinner.setSelection(0);
                    standardSpinner.setSelection(0);
                    System.out.println(connectorList);
                    //cast de la lista la array
                }

            }
        });


        buttonViewConnectors = findViewById(R.id.viewConnectors);


        buttonViewConnectors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedItems = new boolean[connectorList.size()];
                listItems = new String[connectorList.size()];
                for (k = 0; k < connectorList.size(); k++) {
                    listItems[k] = connectorList.get(k).getStandard().toString();
                    System.out.println(listItems[k]);
                }

                System.out.println("-------------------------------------------------------------------");


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(buttonViewConnectors.getContext());
                mBuilder.setTitle("Your connectors");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Remove connector", new DialogInterface.OnClickListener() {
                    int counter = 0;
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        int size = connectorList.size();
                        for (int i = 0; i < size; i++) {
                            if (checkedItems[i] == true) {
                                connectorList.remove(i-counter);
                                counter++;
                            }
                        }
                        System.out.println(connectorList);
                        listItems = new String[connectorList.size()];
                        for (k = 0; k < connectorList.size(); k++) {
                            listItems[k] = connectorList.get(k).getStandard().toString();
                            System.out.println(listItems[k]);
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });

        buttonBack = findViewById(R.id.backButton);
        buttonSubmit = findViewById(R.id.submitButton);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count1 = 0;
                addressText = addressEditText.getText().toString();
                if (!addressText.equals("")) {
                    count1++;
                }
                cityText = cityEditText.getText().toString();
                if (!cityText.equals("")) {
                    count1++;
                }
                priceText = priceEditText.getText().toString();
                if (!priceText.equals("")) {
                    if(Double.parseDouble(priceText)<=3) {
                        count1++;
                    }else{
                        Toast.makeText(buttonBack.getContext(),"Pretul nu poate depasi 3 lei",Toast.LENGTH_SHORT).show();
                    }

                }

                if (count1 == 3 && connectorList.size()>0) { // daca s-au introdus datele alea de sus si exista macar un connector in array, atunci trimit datele
                    Float price = Float.valueOf(priceText);
                    StatusEVSE status = StatusEVSE.AVAILABLE;
                    //adaug un connector nou in array la fiecare apasare de buton, NU AICI
                    Point chargingPortPoint = MainActivity.getChargingPortLocation();
                    Coordinates geoLocation = new Coordinates(String.valueOf(chargingPortPoint.latitude()), String.valueOf(chargingPortPoint.longitude()));
                    Connector[] connector = new Connector[connectorList.size()];
                    connectorList.toArray(connector);
                    AddChargingStation chargingStation = new AddChargingStation(addressText, cityText, price, status, connector, geoLocation,"");
                    StationData stationData= StationData.getInstance();
                    stationData.setAddChargingStation(chargingStation);
                    System.out.println("Charging station==========================" + chargingStation);
//                    adaugaStatiiBoss(chargingStation);
                    openConfirmPassword();
                } else {
                    Toast.makeText(buttonSubmit.getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }



    private void adaugaStatiiBoss(AddChargingStation jsonCreat) {
        System.out.println("Obtain new Token");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();

        Call<ResponseAddStation> call = jsonPlaceHolderApi.addStation(utilizator.getAuthentificationKey(), jsonCreat);
        call.enqueue(new Callback<ResponseAddStation>() {
            @Override
            public void onResponse(Call<ResponseAddStation> call, Response<ResponseAddStation> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Nu s-a putut adauga statie, eroare cu cod: " + response.code());
                }
                ResponseAddStation raspunsBody = response.body();
                if (raspunsBody.isSuccess()) {
                    System.out.println("Added Station succesfully!");
                }
            }

            @Override
            public void onFailure(Call<ResponseAddStation> call, Throwable t) {
                System.out.println("Fail la addStation");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openConfirmPassword(){
        Intent intent = new Intent(this, ConfirmPassword.class);
        startActivity(intent);
    }

    public enum StationSpeedType {
        slow, fast, turbo;
    }
}
