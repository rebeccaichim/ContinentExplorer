package com.example.continentexplorer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.continentexplorer.dto.Coordinates;
import com.example.continentexplorer.dto.VisitedCountryRequest;
import com.example.continentexplorer.network.ApiService;
import com.example.continentexplorer.network.RetrofitClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourMapsActivityEuropa extends AppCompatActivity {
    private WebView webView;
    private Button newPinButton;
    private Button addVisitedCountryButton, addCurrentCountryButton;
    private LinearLayout optionsLayout;
    private Long userId;
    private ApiService apiService;
    private RecyclerView visitedCountriesRecyclerView;
    private VisitedCountriesAdapter adapter;
    private LinearLayout historyContainer;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final Map<String, String> countryFullNames = new HashMap<>();

    static {
        countryFullNames.put("EU-RU", "Rusia");
        countryFullNames.put("EU-NL", "Olanda");
        countryFullNames.put("EU-CY", "Cipru");
        countryFullNames.put("EU-UA", "Ucraina");
        countryFullNames.put("EU-TR", "Turcia");
        countryFullNames.put("EU-SE", "Suedia");
        countryFullNames.put("EU-SI", "Slovenia");
        countryFullNames.put("EU-SK", "Slovakia");
        countryFullNames.put("EU-RS", "Serbia");
        countryFullNames.put("EU-RO", "Romania");
        countryFullNames.put("EU-PT", "Portugalia");
        countryFullNames.put("EU-PL", "Polonia");
        countryFullNames.put("EU-NO", "Norvegia");
        countryFullNames.put("EU-ME", "Muntenegru");
        countryFullNames.put("EU-MK", "Macedonia");
        countryFullNames.put("EU-MD", "Moldova");
        countryFullNames.put("EU-LV", "Letonia");
        countryFullNames.put("EU-LT", "Lituania");
        countryFullNames.put("EU-XK", "Kosovo");
        countryFullNames.put("EU-IS", "Islanda");
        countryFullNames.put("EU-IE", "Irlanda");
        countryFullNames.put("EU-HU", "Ungaria");
        countryFullNames.put("EU-HR", "Croația");
        countryFullNames.put("EU-GR", "Grecia");
        countryFullNames.put("EU-GE", "Georgia");
        countryFullNames.put("EU-FR", "Franța");
        countryFullNames.put("EU-FI", "Finlanda");
        countryFullNames.put("EU-EE", "Estonia");
        countryFullNames.put("EU-ES", "Spania");
        countryFullNames.put("EU-GB", "Regatul Unit");
        countryFullNames.put("EU-DK", "Danemarca");
        countryFullNames.put("EU-DE", "Germania");
        countryFullNames.put("EU-CZ", "Cehia");
        countryFullNames.put("EU-CH", "Elveția");
        countryFullNames.put("EU-BY", "Belarus");
        countryFullNames.put("EU-BA", "Bosnia și Herțegovina");
        countryFullNames.put("EU-BG", "Bulgaria");
        countryFullNames.put("EU-BE", "Belgia");
        countryFullNames.put("EU-AZ", "Azerbaidjan");
        countryFullNames.put("EU-AT", "Austria");
        countryFullNames.put("EU-AM", "Armenia");
        countryFullNames.put("EU-AL", "Albania");
        countryFullNames.put("EU-IT", "Italia");
        countryFullNames.put("EU-KZ", "Kazahstan");
        countryFullNames.put("EU-AD", "Andorra");
        countryFullNames.put("EU-SM", "San Marino");
        countryFullNames.put("EU-VA", "Vatican");
        countryFullNames.put("EU-MC", "Monaco");
        countryFullNames.put("EU-LI", "Liechtenstein");
        countryFullNames.put("EU-MT", "Malta");
        countryFullNames.put("EU-LU", "Luxemburg");
    }

    private static final Map<String, Coordinates> countryCoordinates = new HashMap<>();

    static {
        countryCoordinates.put("EU-RU", new Coordinates(61.5240, 105.3188));
        countryCoordinates.put("EU-NL", new Coordinates(52.1326, 5.2913));
        countryCoordinates.put("EU-CY", new Coordinates(35.1264, 33.4299));
        countryCoordinates.put("EU-UA", new Coordinates(48.3794, 31.1656));
        countryCoordinates.put("EU-TR", new Coordinates(38.9637, 35.2433));
        countryCoordinates.put("EU-SE", new Coordinates(60.1282, 18.6435));
        countryCoordinates.put("EU-SI", new Coordinates(46.1512, 14.9955));
        countryCoordinates.put("EU-SK", new Coordinates(48.6690, 19.6990));
        countryCoordinates.put("EU-RS", new Coordinates(44.0165, 21.0059));
        countryCoordinates.put("EU-RO", new Coordinates(45.9432, 24.9668));
        countryCoordinates.put("EU-PT", new Coordinates(39.3999, -8.2245));
        countryCoordinates.put("EU-PL", new Coordinates(51.9194, 19.1451));
        countryCoordinates.put("EU-NO", new Coordinates(60.4720, 8.4689));
        countryCoordinates.put("EU-ME", new Coordinates(42.7087, 19.3744));
        countryCoordinates.put("EU-MK", new Coordinates(41.6086, 21.7453));
        countryCoordinates.put("EU-MD", new Coordinates(47.4116, 28.3699));
        countryCoordinates.put("EU-LV", new Coordinates(56.8796, 24.6032));
        countryCoordinates.put("EU-LT", new Coordinates(55.1694, 23.8813));
        countryCoordinates.put("EU-XK", new Coordinates(42.6026, 20.9028));
        countryCoordinates.put("EU-IS", new Coordinates(64.9631, -19.0208));
        countryCoordinates.put("EU-IE", new Coordinates(53.4129, -8.2439));
        countryCoordinates.put("EU-HU", new Coordinates(47.1625, 19.5033));
        countryCoordinates.put("EU-HR", new Coordinates(45.1, 15.2));
        countryCoordinates.put("EU-GR", new Coordinates(39.0742, 21.8243));
        countryCoordinates.put("EU-GE", new Coordinates(42.3154, 43.3569));
        countryCoordinates.put("EU-FR", new Coordinates(46.6034, 1.8883));
        countryCoordinates.put("EU-FI", new Coordinates(61.9241, 25.7482));
        countryCoordinates.put("EU-EE", new Coordinates(58.5953, 25.0136));
        countryCoordinates.put("EU-ES", new Coordinates(40.4637, -3.7492));
        countryCoordinates.put("EU-GB", new Coordinates(55.3781, -3.4360));
        countryCoordinates.put("EU-DK", new Coordinates(56.2639, 9.5018));
        countryCoordinates.put("EU-DE", new Coordinates(51.1657, 10.4515));
        countryCoordinates.put("EU-CZ", new Coordinates(49.8175, 15.4730));
        countryCoordinates.put("EU-CH", new Coordinates(46.8182, 8.2275));
        countryCoordinates.put("EU-BY", new Coordinates(53.9006, 27.5590));
        countryCoordinates.put("EU-BA", new Coordinates(43.9159, 17.6791));
        countryCoordinates.put("EU-BG", new Coordinates(42.7339, 25.4858));
        countryCoordinates.put("EU-BE", new Coordinates(50.8503, 4.3517));
        countryCoordinates.put("EU-AZ", new Coordinates(40.1431, 47.5769));
        countryCoordinates.put("EU-AT", new Coordinates(47.5162, 14.5501));
        countryCoordinates.put("EU-AM", new Coordinates(40.0691, 45.0382));
        countryCoordinates.put("EU-AL", new Coordinates(41.1533, 20.1683));
        countryCoordinates.put("EU-IT", new Coordinates(41.8719, 12.5674));
        countryCoordinates.put("EU-KZ", new Coordinates(48.0196, 66.9237));
        countryCoordinates.put("EU-AD", new Coordinates(42.5078, 1.5211));
        countryCoordinates.put("EU-SM", new Coordinates(43.9336, 12.4500));
        countryCoordinates.put("EU-VA", new Coordinates(41.9029, 12.4534));
        countryCoordinates.put("EU-MC", new Coordinates(43.7384, 7.4246));
        countryCoordinates.put("EU-LI", new Coordinates(47.1660, 9.5554));
        countryCoordinates.put("EU-MT", new Coordinates(35.8997, 14.5146));
        countryCoordinates.put("EU-LU", new Coordinates(49.8153, 6.1296));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_maps_europa);

        this.userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        ImageView backArrow = findViewById(R.id.backButton);
        backArrow.setOnClickListener(view -> {
            onBackPressed();
        });

        historyContainer = findViewById(R.id.historyContainer);
        visitedCountriesRecyclerView = findViewById(R.id.visitedCountriesRecyclerView);

        visitedCountriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadVisitedCountries(Math.toIntExact(userId));

        loadVisitedCountriesDesc((long) Math.toIntExact(userId));

        webView = findViewById(R.id.europaMap);
        newPinButton = findViewById(R.id.newPinButton);
        addVisitedCountryButton = findViewById(R.id.addOldCountryButton);
        addCurrentCountryButton = findViewById(R.id.addCurrentCountryButton);
        optionsLayout = findViewById(R.id.optionsLayout);

        setupMap();

        newPinButton.setOnClickListener(v -> {
            toggleLayouts(true);
        });

        addVisitedCountryButton.setOnClickListener(v -> {
            Toast.makeText(this, "Select a country to add as visited", Toast.LENGTH_SHORT).show();
            enableMapInteractivity();
        });

        addCurrentCountryButton.setOnClickListener(v -> {
            addCurrentLocationToDatabase();
            toggleLayouts(false);
        });
    }


    private void loadVisitedCountries(int userId) {
        apiService.getVisitedCountries(userId).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> visitedCountries = response.body();
                    if (!visitedCountries.isEmpty()) {
                        for (String country : visitedCountries) {
                            addPinToMap(country);
                        }
                    } else {
                        Log.d("YourMapsActivityEuropa", "No visited countries found for user.");
                    }
                } else {
                    Log.e("YourMapsActivityEuropa", "Failed to fetch visited countries: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("YourMapsActivityEuropa", "Error fetching visited countries: " + t.getMessage());
            }
        });
    }

    private void loadVisitedCountriesDesc(Long userId) {
        apiService.getVisitedCountries(userId).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> visitedCountries = response.body();

                    List<String> fullNamesWithDates = new ArrayList<>();
                    for (String countryWithDate : visitedCountries) {
                        String[] parts = countryWithDate.split(" ");
                        String countryCode = parts[0];
                        addPinToMap(countryCode);
                        String visitedDate = parts[1];

                        String countryName = countryFullNames.getOrDefault(countryCode, countryCode);
                        fullNamesWithDates.add(countryName + " " + visitedDate);
                    }

                    Collections.reverse(fullNamesWithDates);

                    adapter = new VisitedCountriesAdapter(fullNamesWithDates);
                    visitedCountriesRecyclerView.setAdapter(adapter);

                    toggleLayouts(false);
                } else {
                    historyContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("YourMapsActivityEuropa", "Error fetching visited counties: " + t.getMessage());
                historyContainer.setVisibility(View.GONE);
            }
        });
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private void getCurrentLocationAndSave() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        String currentCountryId = getCountryFromCoordinates(location.getLatitude(), location.getLongitude());
                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        saveVisitedCountryToDatabase(currentCountryId, currentDate);
                    } else {
                        Toast.makeText(this, "Unable to get location.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addCurrentLocationToDatabase() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        String currentCountryId = getCountryFromCoordinates(latitude, longitude);

                        if (currentCountryId.equals("Unknown")) {
                            Toast.makeText(this, "Unable to identify country from location.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                        saveVisitedCountryToDatabase(currentCountryId, currentDate);
                    } else {
                        Toast.makeText(this, "Unable to get location. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationAndSave();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getCountryFromCoordinates(double latitude, double longitude) {
        String closestCountryId = "Unknown";
        double shortestDistance = Double.MAX_VALUE;

        for (Map.Entry<String, Coordinates> entry : countryCoordinates.entrySet()) {
            Coordinates coordinates = entry.getValue();
            double distance = calculateDistance(latitude, longitude, coordinates.getLatitude(), coordinates.getLongitude());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closestCountryId = entry.getKey();
            }
        }
        return closestCountryId;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupMap() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.addJavascriptInterface(new MapJavaScriptInterface(), "Android");
        webView.loadUrl("file:///android_res/raw/your_maps_europa.svg");
    }

    private void enableMapInteractivity() {
        webView.evaluateJavascript("enableInteractivity()", value -> {
            Log.d("YourMapsActivityEuropa", "Interactivity enabled");
        });
    }

    private void showDatePickerDialog(String countryId) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            saveVisitedCountryToDatabase(countryId, selectedDate);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void saveVisitedCountryToDatabase(String countryId, String date) {
        String formattedDate = formatDate(date);
        String visitedCountryName = countryId;

        Long continentId = 3L;

        VisitedCountryRequest visitedCountryRequest = new VisitedCountryRequest(visitedCountryName, userId, formattedDate, continentId);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.addVisitedCountry(visitedCountryRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(YourMapsActivityEuropa.this, "Country saved successfully!", Toast.LENGTH_SHORT).show();
                    addPinToMap(countryId);
                    loadVisitedCountriesDesc(userId);
                    toggleLayouts(false);

                } else {
                    Toast.makeText(YourMapsActivityEuropa.this, "Failed to save country.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(YourMapsActivityEuropa.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleLayouts(boolean showOptions) {
        if (showOptions) {
            optionsLayout.setVisibility(View.VISIBLE);
            historyContainer.setVisibility(View.GONE);
        } else {
            optionsLayout.setVisibility(View.GONE);
            historyContainer.setVisibility(View.VISIBLE);
        }
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return outputFormat.format(inputFormat.parse(date));
        } catch (ParseException e) {
            return date;
        }
    }

    private void addPinToMap(String countryId) {
        String jsCommand = "addPin('" + countryId + "')";
        webView.evaluateJavascript(jsCommand, null);
    }

    private class MapJavaScriptInterface {
        @JavascriptInterface
        public void onCountrySelected(String countryId) {
            runOnUiThread(() -> {
                Toast.makeText(YourMapsActivityEuropa.this, "Country " + countryId + " selected", Toast.LENGTH_SHORT).show();
                showDatePickerDialog(countryId);
            });
        }
    }
}
