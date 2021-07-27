package com.example.navigation3;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.listeners.RouteListener;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.example.navigation3.Notificare.CHANNEL_1_ID;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

// classes needed to launch navigation UI
//Notificare

/**
 * Use the Mapbox Core Library to receive updates when the device changes location.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        MapboxMap.OnMapClickListener, PermissionsListener, RouteListener {

    private MediaSessionCompat mediaSession;
    static List<Message> MESSAGES = new ArrayList<>();
    //Notif
    private NotificationManagerCompat notificationManager;
    public static Station statiePay = null;
    public static Point statiePoint = null;
    //toolbar
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    CardView cardView, cardViewExpanded, paymentCardView;

    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    private TrafficPlugin trafficPlugin;

    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button, expandButton, closeButton, paymentButton;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private CarmenFeature home;
    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Gson gson = new Gson();

    //Variabile globale
    private volatile List<Station> stationList;
    private static final String MARKER_SOURCE = "markers-source";
    private static final String MARKER_STYLE_LAYER = "markers-style-layer";
    private static final String MARKER_IMAGE = "custom-marker";
    private List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
    private List<Point> destinationPoints;
    private static Point chargingPortLocation;
    private Point originPoint;
    private boolean activityDeschis = false;
    private static Context mContext;
    private List<Car2> car2s = null;


    public static Context getAppContext() {
        return mContext;
    }

    public static void samoarafamiliamea(Intent intent) {
        mContext.startActivity(intent);
    }

    public static Point getChargingPortLocation() {
        return chargingPortLocation;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        notificationManager = NotificationManagerCompat.from(this);
        mediaSession = new MediaSessionCompat(this, "tag");
        MESSAGES.add(new Message("Introduceti kw consumati", null));
        Intent intent = getIntent();
        String authToken = intent.getStringExtra("authToken");
        System.out.println("123===" + authToken + "===321");
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //toolbar
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        //testJson();
        getData();

        paymentCardView = findViewById(R.id.payment_card_view);
        paymentCardView.setVisibility(View.GONE);

        cardView = findViewById(R.id.map_view_location_card);
        cardView.setVisibility(View.GONE);  //show layout2
        cardViewExpanded = findViewById(R.id.map_view_location_card_b);
        cardViewExpanded.setVisibility(View.GONE);  //show layout2
        stationList = new ArrayList<>();
        stationList.add(new Station(new Coordinates(String.valueOf(26.481266), String.valueOf(46.9465))));
        stationList.add(new Station(new Coordinates(String.valueOf(23.833329), String.valueOf(46.498392))));


        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/bogdanbogdan/ck8pl12710amu1invbixutesg")

// Add the SymbolLayer icon image to the map style
                        .withImage(ICON_ID, BitmapFactory.decodeResource(
                                MainActivity.this.getResources(), R.drawable.map_marker_dark))

// Adding a GeoJson source for the SymbolLayer icons.
                        .withSource(new GeoJsonSource(SOURCE_ID,
                                FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                        .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                                .withProperties(PropertyFactory.iconImage(ICON_ID),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[]{0f, -9f}))
                        ),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        //getNearbyStations();
                        initSearchFab();
                        trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);

// Enable the traffic view by default
                        trafficPlugin.setVisibility(true);
                        findViewById(R.id.traffic_toggle_fab).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mapView != null) {
                                    trafficPlugin.setVisibility(!trafficPlugin.isVisible());
                                }
                            }
                        });

                        enableLocationComponent(style);
                        addDestinationIconSymbolLayer(style);

                        // Create an empty GeoJSON source using the empty feature collection
                        setUpSource(style);

// Set up a new symbol layer for displaying the searched location's feature coordinates
                        setupLayer(style);

                        mapboxMap.addOnMapClickListener(MainActivity.this);
                        button = findViewById(R.id.startButton);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityDeschis = false;
                                TimerTask timerTask = new TimerTask() {

                                    @Override
                                    public void run() {
                                        Point aiciEsti = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                                                locationComponent.getLastKnownLocation().getLatitude());
                                        testSaVedemDeMerge(aiciEsti);
                                    }
                                };

                                Timer timer = new Timer("MyTimer");//create a new Timer

                                timer.scheduleAtFixedRate(timerTask, 30, 30000);


                                boolean simulateRoute = true;
                                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                        .directionsRoute(currentRoute)
                                        .shouldSimulateRoute(simulateRoute)
                                        .build();
// Call this method with Context from within an Activity
                                NavigationLauncher.startNavigation(MainActivity.this, options);
                                Log.i("testesttest", "merge");

                            }
                        });

                        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_battery_marker, null);
                        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);
//                style.addImage(MARKER_IMAGE, BitmapFactory.decodeResource(
//                        MainActivity.this.getResources(), R.drawable.ic_addchargingport));
                        style.addImage(MARKER_IMAGE, mBitmap);
                        getAllStations(style);
                    }


                });
    }

    private void addMarkers(@NonNull Style loadedMapStyle) {

        List<Feature> features = new ArrayList<>();
//        Thread loadAllStations = new Thread(){
//                @Override
//                public void run() {
//                    getAllStations();
//                }
//        };
//        loadAllStations.start();
        System.out.println("Statii incarcate: " + stationList.size());
        for (Station statie : stationList) {
            features.add(Feature.fromGeometry(Point.fromLngLat(Double.valueOf(statie.getCoordinates().getLongitude()), Double.valueOf(statie.getCoordinates().getLatitude()))));
        }

        /* Source: A data source specifies the geographic coordinate where the image marker gets placed. */

        loadedMapStyle.addSource(new GeoJsonSource(MARKER_SOURCE, FeatureCollection.fromFeatures(features)));

        /* Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. */
        loadedMapStyle.addLayer(new SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconImage(MARKER_IMAGE),
// Adjust the second number of the Float array based on the height of your marker image.
// This is because the bottom of the marker should be anchored to the coordinate point, rather
// than the middle of the marker being the anchor point on the map.
                        PropertyFactory.iconOffset(new Float[]{0f, -8f})
                ));
    }

    private void initSearchFab() {
        findViewById(R.id.fab_location_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

// Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

// Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
// Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

// Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                    onMapClick(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                            ((Point) selectedCarmenFeature.geometry()).longitude()));
                    Point destinationPoint = Point.fromLngLat(((Point) selectedCarmenFeature.geometry()).longitude(),
                            ((Point) selectedCarmenFeature.geometry()).latitude());

//                    getRouteAPI(, originPoint, destinationPoint);

                }
            }
        }
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    public Station getMarkerPosition(LatLng latLng, Double currentZoom) {

        double min = Double.MAX_VALUE;
        Station resultPoint = stationList.get(0);

        for (Station statie : stationList) {

            double lat = Double.valueOf(statie.getCoordinates().getLatitude());
            double lng = Double.valueOf(statie.getCoordinates().getLongitude());
            double distance = Math.sqrt(Math.pow(latLng.getLatitude() - lat, 2) + Math.pow(latLng.getLongitude() - lng, 2));

            if (distance < min) {
                min = distance;
                resultPoint = statie;
            }

        }
        System.out.println("Min este: " + min + "\n Zoom: " + 0.005 * Math.pow(10 / currentZoom, 6));
        if (min > 0.005 * Math.pow(10 / currentZoom, 6)) {
            resultPoint = null;
            System.out.println("Nu e in zoom");
        }
        return resultPoint;
    }


    @SuppressWarnings({"MissingPermission"})
    private void completeazaCard(Station isMarker) {
        TextView cardLocatie = findViewById(R.id.location_name_tv);
        TextView cardLocatieExtend = findViewById(R.id.location_name_tv_b);
        TextView cardPret = findViewById(R.id.location_price_tv);
        TextView cardPretExtend = findViewById(R.id.location_price_tv_b);
        TextView cardStatus = findViewById(R.id.status_tv);
        TextView cardStatusExtend = findViewById(R.id.location_status_tv_b);
        TextView cardViteza = findViewById(R.id.location_speed_tv_b);
        TextView cardIncarcator = findViewById(R.id.location_chargers_tv_b);


        cardLocatie.setText(isMarker.getPhysical_address().getStreet() + " " + isMarker.getPhysical_address().getNumber());
        cardLocatieExtend.setText(isMarker.getPhysical_address().getStreet() + " " + isMarker.getPhysical_address().getNumber());
        System.out.println("Pretul este: " + isMarker.getPrice_per_kw());
        cardPret.setText("Pret: " + String.valueOf(isMarker.getPrice_per_kw()));
        cardPretExtend.setText("Pret: " + String.valueOf(isMarker.getPrice_per_kw()));
        System.out.println("EVSES: " + isMarker.getEvse());
        cardStatus.setText(isMarker.getEvse()[0].getStatus().toString());
        cardStatusExtend.setText(isMarker.getEvse()[0].getStatus().toString());
        cardViteza.setText("Viteza: " + isMarker.getSpeed().toString());
        cardIncarcator.setText("Tip incarcator: " + isMarker.getEvse()[0].getConnectors()[0].getStandard().toString());
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        CameraPosition currentCameraPosition = mapboxMap.getCameraPosition();
        double currentZoom = currentCameraPosition.zoom;
        double currentTilt = currentCameraPosition.tilt;
        Station isMarker = getMarkerPosition(point, currentZoom);
        button.setVisibility(View.VISIBLE);
        if (isMarker != null) {
            cardViewExpanded.setVisibility(View.GONE);
            completeazaCard(isMarker);
            System.out.println("Debugging romanesc");
            destinationPoint = Point.fromLngLat(Double.valueOf(isMarker.getCoordinates().getLongitude()), Double.valueOf(isMarker.getCoordinates().getLatitude()));
            cardView = findViewById(R.id.map_view_location_card);
            cardView.setVisibility(View.VISIBLE);  //show layout2
            expandButton = findViewById(R.id.expand_button);
            expandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardView.setVisibility(View.GONE);
                    cardViewExpanded.setVisibility(View.VISIBLE);
                    closeButton = findViewById(R.id.close_button);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cardViewExpanded.setVisibility(View.GONE);
                            cardView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });

        } else {
            cardView.setVisibility(View.GONE);  //show layout2
            cardViewExpanded.setVisibility(View.GONE);
        }

        originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }
        BodyRoute bodyRoute = new BodyRoute(RoutingModule.getInstance());
//        testBodyRoute(originPoint, destinationPoint);
        LLAddress origin = new LLAddress(String.valueOf(originPoint.latitude()), String.valueOf(originPoint.longitude()), "x");
        LLAddress destination = new LLAddress(String.valueOf(destinationPoint.latitude()), String.valueOf(destinationPoint.longitude()), "x");
        bodyRoute.setDestination(destination);
        bodyRoute.setOrigin(origin);
        System.out.println(bodyRoute);
        if (bodyRoute.getKilowats_now() != 0.0) {
            getRouteAPI(bodyRoute, originPoint, destinationPoint);
        } else {
            Toast.makeText(this, "Nu ai selectat optiunile de drum!", Toast.LENGTH_LONG).show();
            openRoutingModule();
        }
        chargingPortLocation = destinationPoint;
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapbox_blue);
        return true;

    }

    private void getRoute(Point origin, Point destination, Route newRoute) {
        destinationPoints = new ArrayList<>();
        Leg[] legs = newRoute.getLegs();
        if (legs != null) {
            for (int i = 0; i < legs.length; i++) {
                Station statie = getStationById(legs[i].getStationId());
                destinationPoints.add(Point.fromLngLat(Double.valueOf(statie.getCoordinates().getLongitude()), Double.valueOf(statie.getCoordinates().getLatitude())));
            }
        }

        NavigationRoute.Builder builder = NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .origin(origin)
                .destination(destination);

        for (Point point : destinationPoints) {
            builder.addWaypoint(point);
        }

        builder.build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);
//                        System.out.println("Ruta contine: "+currentRoute.distance()+ " "+currentRoute.duration());

// Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                        TextView cardDistanta = findViewById(R.id.location_distance_tv_b);
                        double distantaTraseu = currentRoute.distance();
                        double distantaTraseuKm = distantaTraseu / 1000;
                        cardDistanta.setText("Distanta: " + String.valueOf((int) (distantaTraseuKm)) + "km");
                        TextView cardDurata = findViewById(R.id.location_duration_tv_b);
                        double durataTraseu = currentRoute.duration() / 3600;
                        int durataTraseuOre = (int) durataTraseu;
                        double durataTraseuMin = durataTraseu - durataTraseuOre;
                        double durataTraseuMinFix = durataTraseuMin * 60;
                        System.out.println(durataTraseu);
                        cardDurata.setText("Durata: " + String.valueOf(durataTraseuOre) + "hr" + " " + (int) durataTraseuMinFix + "min");
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    private Station getStationById(String id) {
        for (Station station : stationList) {
            if (station.getId().equals(id)) {
                return station;
            }
        }
        return null;
    }

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
// Activate the MapboxMap LocationComponent to show user location
// Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                openLoginActivity();
                break;
            case R.id.RoutingOptions:
                openRoutingModule();
                break;
            case R.id.AddNewChargingPort:
                openAddChargingPort();
                break;
            case R.id.profile:
                openProfile();
                break;
        }
        return true;
    }

    public void openLoginActivity() {
        Toast.makeText(this, "AI DAT PE LOGIN GIGELE", Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openRoutingModule() {
        Intent intent = new Intent(this, AddRoutingOptions.class);
        startActivity(intent);
    }

    public void openAddChargingPort() {
        Intent intent = new Intent(this, AddChargingPort.class);
        startActivity(intent);
    }

    public void openProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void getNearbyStations() {
        System.out.println("getNearbyStations");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();
        System.out.println("Auth-key: " + utilizator.getAuthentificationKey());

        Call<ResponseNearbyStations> call = jsonPlaceHolderApi.getNearbyStations(utilizator.getAuthentificationKey(), 47.1744354, 27.5746688);
        try {
            Response<ResponseNearbyStations> raspuns = call.execute();
            if (raspuns.code() == 401) {
                System.out.println("Token expirat");
                NewToken.getNewToken();
                getNearbyStations();
            }
            ResponseNearbyStations body = raspuns.body();
            JsonObject jsonObject = body.getData();
            System.out.println(jsonObject);
            JsonArray ja_data = jsonObject.getAsJsonArray("stationAround");
            System.out.println(ja_data);
            Station[] statiiPrimite = gson.fromJson(ja_data, Station[].class);
            stationList = new ArrayList<>(Arrays.asList(statiiPrimite));
            System.out.println("AFISARE STATII:");
            for (Station station : stationList) {
                System.out.println(station);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAllStations(Style style) {
        System.out.println("getAllStations");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();
        System.out.println("getAllStations Auth-key: " + utilizator.getAuthentificationKey());
        System.out.println("Refresh-key: " + utilizator.getRefreshKey());
        Call<ResponseNearbyStations> call = jsonPlaceHolderApi.getAllStations(utilizator.getAuthentificationKey());
        call.enqueue(new Callback<ResponseNearbyStations>() {
            @Override
            public void onResponse(Call<ResponseNearbyStations> call, Response<ResponseNearbyStations> response) {
                //Token expirat
                if (response.code() == 401) {
                    System.out.println("Token expirat");
                    NewToken.getNewToken();
                    getAllStations(style);
                } else if (response.isSuccessful()) {
                    ResponseNearbyStations body = response.body();
                    JsonObject jsonObject = body.getData();
                    System.out.println(jsonObject);
                    JsonArray ja_data = jsonObject.getAsJsonArray("stationList");
                    System.out.println(ja_data);
                    Station[] statiiPrimite = gson.fromJson(ja_data, Station[].class);
                    stationList = new ArrayList<>(Arrays.asList(statiiPrimite));
                    System.out.println("AFISARE STATII getAllStations:");
                    for (Station station : stationList) {
                        System.out.println(station);
                    }
                    addMarkers(style);
                }
            }

            @Override
            public void onFailure(Call<ResponseNearbyStations> call, Throwable t) {
                System.out.println("Fail la getAllStations");
            }
        });
    }


    private void testJson() {
        String adresa = "Focsani,Primarie";
        String oras = "Focsani";
        Float pret = 0.5f;
        StatusEVSE status = StatusEVSE.AVAILABLE;
        Connector[] connectors = new Connector[1];
        Standard standard = Standard.DOMESTIC_D;
        Format format = Format.SOCKET;
        PowerType power_type = PowerType.DC;
        Integer max_amperage = 45;
        Integer max_voltage = 450;
        Integer max_electric_power = 200000;
        Integer power = 20;
        connectors[0] = new Connector(standard, format, power_type, max_amperage, max_voltage, max_electric_power, power);
        Coordinates geolocation = new Coordinates("45.6966782", "27.1825576");
        AddChargingStation newChargingStation = new AddChargingStation(adresa, oras, pret, status, connectors, geolocation, "");
        Gson gson = new Gson();
        String jsonCreat = gson.toJson(newChargingStation);
        System.out.println("Json creat: " + jsonCreat);
        //adaugaStatiiBoss(newChargingStation);
    }

    private void testBodyRoute(Point originPoint, Point destinationPoint) {
        String carId = "5d161beac9eef4a1f1d9d21a";
        Integer max_kilometers = 400;
        Float kilowats_now = 2.5f;
        Standard standard = Standard.IEC_62196_T2_COMBO;
        Integer chargingPower = 150;
        Standard standard2 = Standard.IEC_62196_T2;
        Integer chargingPower2 = 150;
        Standard standard3 = Standard.CHADEMO;
        Integer chargingPower3 = 150;
        Plug[] plugs = {new Plug(standard, chargingPower), new Plug(standard2, chargingPower2), new Plug(standard3, chargingPower3)};
        Plug[] adapters = {new Plug(standard, chargingPower)};
        Integer number_of_passengers = 1;
        String latitude = "45.6917946";
        String longitude = "27.1916718";
        String address = "Focsani, Romania";
        String latitude2 = "44.837452";
        String longitude2 = "24.883163";
        String address2 = "Pitesti, Romania";
        LLAddress origin = new LLAddress(String.valueOf(originPoint.latitude()), String.valueOf(originPoint.longitude()), address);
        LLAddress destination = new LLAddress(String.valueOf(destinationPoint.latitude()), String.valueOf(destinationPoint.longitude()), address2);
        BodyRoute bodyRoute = new BodyRoute(carId, max_kilometers, kilowats_now, plugs, adapters, number_of_passengers, origin, destination);
        System.out.println("Body route: " + bodyRoute);
        getRouteAPI(bodyRoute, originPoint, destinationPoint);
    }

    private void getRouteAPI(BodyRoute bodyRoute, Point originPoint, Point destinationPoint) {

        System.out.println("Obtain new Route");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();
        Call<ResponseRoutingModule> call = jsonPlaceHolderApi.getRoute(utilizator.getAuthentificationKey(), bodyRoute);
        call.enqueue(new Callback<ResponseRoutingModule>() {
            @Override
            public void onResponse(Call<ResponseRoutingModule> call, Response<ResponseRoutingModule> response) {
                if (response.code() == 401) {
                    NewToken.getNewToken();
                    getRouteAPI(bodyRoute, originPoint, destinationPoint);
                }
                if (response.isSuccessful()) {
                    System.out.println("Succes la primirea rutei");
                    ResponseRoutingModule body = response.body();
                    Route newRoute = null;
                    if (body.isSuccess()) {
                        newRoute = body.getRoute();
                        System.out.println("=======" + newRoute.toString());
                    }
                    getRoute(originPoint, destinationPoint, newRoute);
                }
            }

            @Override
            public void onFailure(Call<ResponseRoutingModule> call, Throwable t) {
                System.out.println("Fail la obtinerea rutei");
            }
        });
    }

    @Override
    public boolean allowRerouteFrom(Point offRoutePoint) {
        return false;
    }

    @Override
    public void onOffRoute(Point offRoutePoint) {

    }

    @Override
    public void onRerouteAlong(DirectionsRoute directionsRoute) {

    }

    @Override
    public void onFailedReroute(String errorMessage) {

    }

    @Override
    public void onArrival() {
        Toast.makeText(this, "You have arrived!", Toast.LENGTH_SHORT).show();
    }

    public void testSaVedemDeMerge(Point point) {
        double min = Double.MAX_VALUE;

        //47.149790, 27.593522
        //TODO: DE SCOS PUNCTUL ASTA
        Point locatiaMea = Point.fromLngLat(originPoint.longitude(), originPoint.latitude());
        if (!destinationPoints.contains(locatiaMea)) {
            System.out.println("A ADAUGAT LOCATIA MEA");
            destinationPoints.add(locatiaMea);
        }
        for (Point statie : destinationPoints) {

            double lat = Double.valueOf(statie.latitude());
            double lng = Double.valueOf(statie.longitude());
            double distance = Math.sqrt(Math.pow(point.latitude() - lat, 2) + Math.pow(point.longitude() - lng, 2));
            System.out.println("sunt in bucla============================");
            if (distance < min) {
                min = distance;
                statiePay = getStationIdByCoords(statie);
                //statiePoint=statie;
            }
        }

        //System.out.println("Min este: " + min + "\n Zoom: " + 0.005 * Math.pow(10 / currentZoom, 6));
        if (min < 0.005 && !activityDeschis) {
            Log.i("eBine", "ai ajuns la statie");
            activityDeschis = true;
            sendOnChannel1();
        } else if (min >= 0.005) {
            activityDeschis = false;
        }
    }

    private Station getStationIdByCoords(Point station) {
        for (Station statie : stationList) {
            if (Double.valueOf(statie.getCoordinates().getLatitude()).equals(station.latitude()) && Double.valueOf(statie.getCoordinates().getLongitude()).equals(station.longitude())) {
                return statie;
            }
        }
        return null;
    }


    public void sendOnChannel1() {
        sendChannel1Notification(this);
    }

    public static void sendChannel1Notification(Context context) {
        Intent activityIntent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, activityIntent, FLAG_UPDATE_CURRENT);
        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();
        Intent replyIntent;
        PendingIntent replyPendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context,
                    0, replyIntent, 0);
        } else {
            //start chat activity instead (PendingIntent.getActivity)
            //cancel notification with notificationManagerCompat.cancel(id)
        }
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();
        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Pay");
        NotificationCompat.MessagingStyle.Message notificationMessage = null;
        for (Message chatMessage : MESSAGES) {
            notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(
                            chatMessage.getText(),
                            chatMessage.getTimestamp(),
                            chatMessage.getSender()
                    );
        }
        messagingStyle.addMessage(notificationMessage);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_plata_statie)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }


    private void getData() {
        JsonApi jsonApi;
        String connectURL = "https://uber-electric.herokuapp.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);
        Call<ApiResponse> callProfile = jsonApi.getProfile(Utilizator.getUtilizatorInstance().getAuthentificationKey());
        callProfile.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    myToast.show();
                    return;
                }

                if (response.body() != null) {
                    User user = response.body().getUser();
                    car2s = user.getListOfCars();
                    System.out.println("MASINILE USERULUI: "+car2s);
                    ListOfUserCars.setUserCars(car2s);
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG);
                myToast.show();
            }
        });

    }
}