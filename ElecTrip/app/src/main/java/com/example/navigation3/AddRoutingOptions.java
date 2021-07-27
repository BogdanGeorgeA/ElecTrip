package com.example.navigation3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddRoutingOptions extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner plugsSpinner, adaptersSpinner, carSpinner;
    private EditText maxKilometersEditText, kilowatsNowEditText, numberOfPassengersEditText, plugPowerEditText, adapterPowerEditText;
    private String plugsText, adaptersText, plugChargingPower, adapterChargingPower, maxKilometersText, kilowatsNowText, numberOfPassengersText, carText;
    private static List<Car2> userCars = ListOfUserCars.getUserCars();
    private static String[] carsArray = new String[userCars.size()];
    private static String[] carsId = new String[userCars.size()];
    private int carPosition;

    public static String[] getCarsArray() {
        return carsArray;
    }

    public static String[] getCarsId() {
        return carsId;
    }

    private Button submitButton, backButton, addPlugButton, addAdapterButton, viewPlugs, viewAdapters;
    private List<Plug> plugList;
    private List<Plug> adapterList;

    String[] plugListItems;
    boolean[] plugCheckedItems;
    ArrayList<Integer> mUserItems1 = new ArrayList<>();
    ArrayList<Integer> mUserItems2 = new ArrayList<>();
    String[] adapterListItems;
    boolean[] adapterCheckedItems;
    RoutingModule routingModule = RoutingModule.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing_module);

        plugList = new ArrayList<>();
        adapterList = new ArrayList<>();

        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);
        addPlugButton = findViewById(R.id.addPlug);
        addAdapterButton = findViewById(R.id.addAdapter);
        viewAdapters = findViewById(R.id.viewAdapters);
        viewPlugs = findViewById(R.id.viewPlugs);

        maxKilometersEditText = findViewById(R.id.maxKilometers);
        kilowatsNowEditText = findViewById(R.id.kilowatsNow);
        numberOfPassengersEditText = findViewById(R.id.number_of_passengers);
        plugPowerEditText = findViewById(R.id.plugChargingPower);
        adapterPowerEditText = findViewById(R.id.adapterChargingPower);


        plugsSpinner = findViewById(R.id.plugs);
        adaptersSpinner = findViewById(R.id.adapters);
//        carSpinner = findViewById(R.id.cars);

        ArrayAdapter<CharSequence> plugsAdapter, adaptersAdapter, carsAdapter;

        for (int i = 0; i < userCars.size(); i++) {
            carsArray[i] = userCars.get(i).getCarModel();
            carsId[i] = userCars.get(i).getId();
        }
        for(int i=0;i<userCars.size();i++){
            System.out.println("ID: "+carsId[i]+" model: "+carsArray[i]);
        }

        carSpinner = findViewById(R.id.cars);
        ArrayAdapter<String> carAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, carsArray);
        carSpinner.setAdapter(carAdapter);
        carSpinner.setSelection(0);

        carSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carText = parent.getItemAtPosition(position).toString();
                carPosition=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        plugsAdapter = ArrayAdapter.createFromResource(this, R.array.PlugStandard, android.R.layout.simple_spinner_item);
        plugsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plugsSpinner.setAdapter(plugsAdapter);
        plugsSpinner.setSelection(0);

        adaptersAdapter = ArrayAdapter.createFromResource(this, R.array.AdapterStandard, android.R.layout.simple_spinner_item);
        adaptersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptersSpinner.setAdapter(adaptersAdapter);
        adaptersSpinner.setSelection(0);

        System.out.println(plugList);
        System.out.println(adapterList);

        if ((routingModule.getMax_kilometers() != null) && (Float.valueOf(routingModule.getKilowats_now()) != 0.0) && (routingModule.getNumber_of_passengers() != null)) {

            maxKilometersEditText.setText(routingModule.getMax_kilometers().toString());
            kilowatsNowEditText.setText(Float.valueOf(routingModule.getKilowats_now()).toString());
            numberOfPassengersEditText.setText(routingModule.getNumber_of_passengers().toString());
//            plugPowerEditText.setText(routingModule.getPlugs()[routingModule.getPlugs().length-1].getChargingPower().toString());
//            adapterPowerEditText.setText(routingModule.getAdapters()[routingModule.getAdapters().length-1].getChargingPower().toString());
            System.out.println(plugList);
            System.out.println(adapterList);
        }

        plugsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                plugsText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adaptersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adaptersText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addPlugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count1 = 0;
                if (!plugsText.equals("Plug")) {
                    count1++;
                }
                plugChargingPower = plugPowerEditText.getText().toString();
                if (!plugChargingPower.equals("")) {
                    count1++;
                }
                if (count1 == 2) {
                    int ok = 0;
                    for (Plug currentPlug : plugList) {
                        if (currentPlug.getStandard().toString().equals(plugsText)) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        Standard standard = Enum.valueOf(Standard.class, plugsText);
                        Plug newPlug = new Plug(standard, Integer.valueOf(plugChargingPower));
                        plugList.add(newPlug);
                        plugPowerEditText.setText("");
                        plugsSpinner.setSelection(0);
                        System.out.println(plugList);
                    } else {
                        Toast.makeText(addPlugButton.getContext(), "Nu poti adauga duplicate", Toast.LENGTH_LONG).show();
                        plugsSpinner.setSelection(0);
                    }
                }
            }
        });

        addAdapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count2 = 0;
                if (!adaptersText.equals("Adapter")) {
                    count2++;
                }
                adapterChargingPower = adapterPowerEditText.getText().toString();
                if (!adapterChargingPower.equals("")) {
                    count2++;
                }
                if (count2 == 2) {
                    int ok = 0;
                    for (Plug currentAdapter : adapterList) {
                        if (currentAdapter.getStandard().toString().equals(adaptersText)) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        Standard standard = Enum.valueOf(Standard.class, adaptersText);
                        Plug newAdapter = new Plug(standard, Integer.valueOf(adapterChargingPower));
                        adapterList.add(newAdapter);
                        adapterPowerEditText.setText("");
                        adaptersSpinner.setSelection(0);
                        System.out.println(adapterList);
                    } else {
                        Toast.makeText(addAdapterButton.getContext(), "Nu poti adauga duplicate", Toast.LENGTH_LONG).show();
                        adaptersSpinner.setSelection(0);
                    }

                }
            }
        });

        viewAdapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCheckedItems = new boolean[adapterList.size()];
                adapterListItems = new String[adapterList.size()];
                for (int k = 0; k < adapterList.size(); k++) {
                    adapterListItems[k] = adapterList.get(k).getStandard().toString();
                    System.out.println(adapterListItems[k]);
                }

                System.out.println("-------------------------------------------------------------------");


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(viewAdapters.getContext());
                mBuilder.setTitle("Your Adapters");
                mBuilder.setMultiChoiceItems(adapterListItems, adapterCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems1.add(position);
                        } else {
                            mUserItems1.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Remove adapter", new DialogInterface.OnClickListener() {
                    int counter = 0;

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        int size = adapterList.size();
                        for (int i = 0; i < size; i++) {
                            if (adapterCheckedItems[i] == true) {
                                adapterList.remove(i - counter);
                                counter++;
                            }
                        }
                        System.out.println(adapterList);
                        adapterListItems = new String[adapterList.size()];
                        for (int k = 0; k < adapterList.size(); k++) {
                            adapterListItems[k] = adapterList.get(k).getStandard().toString();
                            System.out.println(adapterListItems[k]);
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });

        viewPlugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plugCheckedItems = new boolean[plugList.size()];
                plugListItems = new String[plugList.size()];
                for (int k = 0; k < plugList.size(); k++) {
                    plugListItems[k] = plugList.get(k).getStandard().toString();
                    System.out.println(plugListItems[k]);
                }

                System.out.println("-------------------------------------------------------------------");


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(viewPlugs.getContext());
                mBuilder.setTitle("Your Plugs");
                mBuilder.setMultiChoiceItems(plugListItems, plugCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems2.add(position);
                        } else {
                            mUserItems2.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Remove plug", new DialogInterface.OnClickListener() {
                    int counter1 = 0;

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        int size = plugList.size();
                        for (int i = 0; i < size; i++) {
                            if (plugCheckedItems[i] == true) {
                                plugList.remove(i - counter1);
                                counter1++;
                            }
                        }
                        System.out.println(plugList);
                        plugListItems = new String[plugList.size()];
                        for (int k = 0; k < plugList.size(); k++) {
                            plugListItems[k] = plugList.get(k).getStandard().toString();
                            System.out.println(plugListItems[k]);
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;

                maxKilometersText = maxKilometersEditText.getText().toString();
                if (!maxKilometersText.equals("")) {
                    count++;
                }
                kilowatsNowText = kilowatsNowEditText.getText().toString();
                if (!kilowatsNowText.equals("")) {
                    count++;
                }
                numberOfPassengersText = numberOfPassengersEditText.getText().toString();
                if (!numberOfPassengersText.equals("")) {
                    count++;
                }
                if (count == 3 && plugList.size() > 0 && adapterList.size() > 0) {
                    Float kilowatsNow = Float.valueOf(kilowatsNowText);
                    Integer maxKilometers = Integer.valueOf(maxKilometersText);
                    Integer numberOfPassengers = Integer.valueOf(numberOfPassengersText);
                    Plug[] plugs = new Plug[plugList.size()];
                    plugList.toArray(plugs);
                    Plug[] adapters = new Plug[adapterList.size()];
                    adapterList.toArray(adapters);
                    routingModule.setCarId(carsId[carPosition]);
                    routingModule.setMax_kilometers(maxKilometers);
                    routingModule.setKilowats_now(kilowatsNow);
                    routingModule.setPlugs(plugs);
                    routingModule.setAdapters(adapters);
//                    routingModule.addPlug(new ArrayList<>(plugList));
//                    routingModule.addAdapter(new ArrayList<>(adapterList));
                    routingModule.setNumber_of_passengers(numberOfPassengers);
                    System.out.println(routingModule);
                    openMainActivity();
                }
                //listele de plug si adapter
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
