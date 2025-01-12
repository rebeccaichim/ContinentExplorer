    package com.example.continentexplorer;

    import android.annotation.SuppressLint;
    import android.app.DatePickerDialog;
    import android.content.Intent;
    import android.content.SharedPreferences;
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
    import com.example.continentexplorer.dto.VisitedCountyRequest;
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

    public class YourMapsActivityRomania extends AppCompatActivity {
        private WebView webView;
        private Button newPinButton;
        private ApiService apiService;
        private RetrofitClient retrofitClient;
        private LinearLayout optionsLayout;
        private Button addOldCountyButton, addCurrentCountyButton;
        private Long userId; // Variabila pentru userId-ul utilizatorului conectat
        private RecyclerView visitedCountiesRecyclerView;
        private VisitedCountiesAdapter adapter;
        private LinearLayout historyContainer;

        static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        private static final Map<String, String> countyFullNames = new HashMap<>();

        static {
            countyFullNames.put("RO-AB", "Alba");
            countyFullNames.put("RO-AG", "Argeș");
            countyFullNames.put("RO-AR", "Arad");
            countyFullNames.put("RO-BC", "Bacău");
            countyFullNames.put("RO-BH", "Bihor");
            countyFullNames.put("RO-BN", "Bistrița-Năsăud");
            countyFullNames.put("RO-BR", "Brăila");
            countyFullNames.put("RO-BT", "Botoșani");
            countyFullNames.put("RO-B", "București");
            countyFullNames.put("RO-BV", "Brașov");
            countyFullNames.put("RO-BZ", "Buzău");
            countyFullNames.put("RO-CJ", "Cluj");
            countyFullNames.put("RO-CL", "Călărași");
            countyFullNames.put("RO-CS", "Caraș-Severin");
            countyFullNames.put("RO-CT", "Constanța");
            countyFullNames.put("RO-CV", "Covasna");
            countyFullNames.put("RO-DB", "Dâmbovița");
            countyFullNames.put("RO-DJ", "Dolj");
            countyFullNames.put("RO-GJ", "Gorj");
            countyFullNames.put("RO-GL", "Galați");
            countyFullNames.put("RO-GR", "Giurgiu");
            countyFullNames.put("RO-HD", "Hunedoara");
            countyFullNames.put("RO-HR", "Harghita");
            countyFullNames.put("RO-IF", "Ilfov");
            countyFullNames.put("RO-IS", "Iasi");
            countyFullNames.put("RO-IL", "Ialomita");
            countyFullNames.put("RO-MS", "Mureș");
            countyFullNames.put("RO-MH", "Mehedinti");
            countyFullNames.put("RO-MM", "Maramures");
            countyFullNames.put("RO-NT", "Neamț");
            countyFullNames.put("RO-OT", "Olt");
            countyFullNames.put("RO-PH", "Prahova");
            countyFullNames.put("RO-SB", "Sibiu");
            countyFullNames.put("RO-SJ", "Sălaj");
            countyFullNames.put("RO-SM", "Satu Mare");
            countyFullNames.put("RO-SV", "Suceava");
            countyFullNames.put("RO-TL", "Tulcea");
            countyFullNames.put("RO-TM", "Timiș");
            countyFullNames.put("RO-TR", "Teleorman");
            countyFullNames.put("RO-VL", "Vâlcea");
            countyFullNames.put("RO-VN", "Vrancea");
            countyFullNames.put("RO-VS", "Vaslui");
        }


        // Adaugă aici map-ul pentru coordonatele județelor
        private static final Map<String, Coordinates> countyCoordinates = new HashMap<>();

        static {
            // Coordonatele centrale pentru județe existente
            countyCoordinates.put("RO-AB", new Coordinates(45.9979, 23.5704)); // Alba
            countyCoordinates.put("RO-AG", new Coordinates(45.0400, 24.8914)); // Argeș
            countyCoordinates.put("RO-AR", new Coordinates(46.1866, 21.3123)); // Arad
            countyCoordinates.put("RO-BC", new Coordinates(46.3691, 26.9658)); // Bacău
            countyCoordinates.put("RO-BH", new Coordinates(46.9740, 22.0480)); // Bihor
            countyCoordinates.put("RO-BN", new Coordinates(47.1333, 24.5000)); // Bistrița-Năsăud
            countyCoordinates.put("RO-BR", new Coordinates(45.2692, 27.9575)); // Brăila
            countyCoordinates.put("RO-BT", new Coordinates(47.8333, 26.8333)); // Botoșani
            countyCoordinates.put("RO-B", new Coordinates(44.4328, 26.1043)); // București
            countyCoordinates.put("RO-BV", new Coordinates(45.6579, 25.6012)); // Brașov
            countyCoordinates.put("RO-BZ", new Coordinates(45.1510, 26.8256)); // Buzău
            countyCoordinates.put("RO-CJ", new Coordinates(46.7712, 23.6236)); // Cluj
            countyCoordinates.put("RO-CL", new Coordinates(44.1816, 27.3314)); // Călărași
            countyCoordinates.put("RO-CS", new Coordinates(45.2959, 22.0132)); // Caraș-Severin
            countyCoordinates.put("RO-CT", new Coordinates(44.1733, 28.6383)); // Constanța
            countyCoordinates.put("RO-CV", new Coordinates(45.8500, 26.0476)); // Covasna
            countyCoordinates.put("RO-DB", new Coordinates(44.9247, 25.4591)); // Dâmbovița
            countyCoordinates.put("RO-DJ", new Coordinates(44.3302, 23.7949)); // Dolj
            countyCoordinates.put("RO-GJ", new Coordinates(45.0300, 23.2700)); // Gorj
            countyCoordinates.put("RO-GL", new Coordinates(45.4233, 27.9644)); // Galați
            countyCoordinates.put("RO-GR", new Coordinates(44.1500, 25.9500)); // Giurgiu
            countyCoordinates.put("RO-MS", new Coordinates(46.5476, 24.5584)); // Mureș
            countyCoordinates.put("RO-NT", new Coordinates(46.9751, 26.3818)); // Neamț
            countyCoordinates.put("RO-OT", new Coordinates(44.1670, 24.5124)); // Olt
            countyCoordinates.put("RO-PH", new Coordinates(45.0967, 25.9174)); // Prahova
            countyCoordinates.put("RO-SB", new Coordinates(45.7969, 24.1519)); // Sibiu
            countyCoordinates.put("RO-SJ", new Coordinates(47.2333, 23.0500)); // Sălaj
            countyCoordinates.put("RO-SM", new Coordinates(47.7833, 22.8833)); // Satu Mare
            countyCoordinates.put("RO-SV", new Coordinates(47.6513, 25.9119)); // Suceava
            countyCoordinates.put("RO-TL", new Coordinates(45.1733, 28.8013)); // Tulcea
            countyCoordinates.put("RO-TM", new Coordinates(45.7537, 21.2257)); // Timiș
            countyCoordinates.put("RO-TR", new Coordinates(43.9000, 25.3167)); // Teleorman
            countyCoordinates.put("RO-VL", new Coordinates(45.1048, 24.3755)); // Vâlcea
            countyCoordinates.put("RO-VN", new Coordinates(45.7003, 27.0122)); // Vrancea
            countyCoordinates.put("RO-VS", new Coordinates(46.6333, 27.7333)); // Vaslui
                    }

        private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
            final int R = 6371; // Raza Pământului în kilometri
            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return R * c;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_your_maps_romania);

            // Preia userId
            this.userId = getIntent().getLongExtra("userId", -1);
            if (userId == -1) {
                Log.e("YourMapsActivityRomania", "User ID not found. Redirecting to Login.");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            // Inițializează ApiService
            apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

            ImageView backArrow = findViewById(R.id.backButton);
            backArrow.setOnClickListener(view -> {
                // Navighează înapoi
                onBackPressed();
            });

            // Preia istoricul
            historyContainer = findViewById(R.id.historyContainer);
            visitedCountiesRecyclerView = findViewById(R.id.visitedCountiesRecyclerView);

            // Configurare RecyclerView
            visitedCountiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            loadVisitedCounties(Math.toIntExact(userId));

            // Încarcă județele vizitate din baza de date
            loadVisitedCountiesDesc((long) Math.toIntExact(userId));

            Log.d("YourMapsActivityRomania", "User ID received: " + userId);

            webView = findViewById(R.id.romaniaMap);
            newPinButton = findViewById(R.id.newPinButton);
            optionsLayout = findViewById(R.id.optionsLayout);
            addOldCountyButton = findViewById(R.id.addOldCountyButton);
            addCurrentCountyButton = findViewById(R.id.addCurrentCountyButton);

            setupMap();

            // Listener pentru butonul "New Pin"
            newPinButton.setOnClickListener(v -> {
                toggleLayouts(true); // Afișează opțiunile "What do you want to do?"
            });


            addOldCountyButton.setOnClickListener(v -> {
                Toast.makeText(this, "Select a county to add as visited", Toast.LENGTH_SHORT).show();
                enableMapInteractivity();
            });

            addCurrentCountyButton.setOnClickListener(v -> {
                addCurrentLocationToDatabase();
                toggleLayouts(false); // Afișează istoricul locurilor vizitate
            });        }


        private void loadVisitedCounties(int userId) {
            apiService.getVisitedCounties(userId).enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<String> visitedCounties = response.body();
                        if (!visitedCounties.isEmpty()) {
                            for (String county : visitedCounties) {
                                addPinToMap(county);
                            }
                        } else {
                            Log.d("YourMapsActivityRomania", "No visited counties found for user.");
                        }
                    } else {
                        Log.e("YourMapsActivityRomania", "Failed to fetch visited counties: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("YourMapsActivityRomania", "Error fetching visited counties: " + t.getMessage());
                }
            });
        }

        private void loadVisitedCountiesDesc(Long userId) {
            apiService.getVisitedCounties(userId).enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<String> visitedCounties = response.body();

                        // Transformă datele într-o listă cu nume complete și date
                        List<String> fullNamesWithDates = new ArrayList<>();
                        for (String countyWithDate : visitedCounties) {
                            String[] parts = countyWithDate.split(" "); // Presupunem formatul "RO-XX (data)"
                            String countyCode = parts[0];
                            addPinToMap(countyCode); // Adaugă pinul pe hartă
                            String visitedDate = parts[1]; // "(data)"

                            // Înlocuiește codul județului cu numele complet
                            String countyName = countyFullNames.getOrDefault(countyCode, countyCode);
                            fullNamesWithDates.add(countyName + " " + visitedDate); // Ex. "Alba (2025-07-01)"
                        }

                        // Sortează descrescător lista de nume
                        Collections.reverse(fullNamesWithDates);

                        // Setează lista în adapter
                        adapter = new VisitedCountiesAdapter(fullNamesWithDates);
                        visitedCountiesRecyclerView.setAdapter(adapter);

                        // Afișează istoricul
                        toggleLayouts(false);
                    } else {
                        Log.e("YourMapsActivityRomania", "Failed to load counties: " + response.code());
                        historyContainer.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("YourMapsActivityRomania", "Error fetching visited counties: " + t.getMessage());
                    historyContainer.setVisibility(View.GONE);
                }
            });
        }



        // Comută între opțiuni și istoricul locurilor vizitate
        private void toggleLayouts(boolean showOptions) {
            if (showOptions) {
                optionsLayout.setVisibility(View.VISIBLE);
                historyContainer.setVisibility(View.GONE); // Ascunde istoricul
            } else {
                optionsLayout.setVisibility(View.GONE);
                historyContainer.setVisibility(View.VISIBLE); // Afișează istoricul
            }
        }


        @SuppressLint("SetJavaScriptEnabled")
                    private void setupMap() {
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webView.addJavascriptInterface(new MapJavaScriptInterface(), "Android");
                        webView.loadUrl("file:///android_res/raw/your_maps_romania.svg"); // Confirmă locația fișierului
                        Log.d("YourMapsActivityRomania", "SVG map loaded");
                    }

                    private void enableMapInteractivity() {
                        webView.evaluateJavascript("enableInteractivity()", value -> {
                            Log.d("YourMapsActivityRomania", "Interactivity enabled");
                        });
                    }

                    private void showDatePickerDialog(String countyId) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            saveVisitedCountyToDatabase(countyId, selectedDate);
                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                        datePickerDialog.show();
                    }

                    private void saveVisitedCountyToDatabase(String countyId, String date) {
                        Log.d("YourMapsActivityRomania", "Saving county: " + countyId + ", date: " + date);

                        // Conversia datei în formatul corect
                        String formattedDate = formatDate(date);

                        String visitedCountyName = countyId;
                        Long countryId = 29L;

                        Log.d("YourMapsActivityRomania", "User ID in request: " + userId);


                        // Adăugăm userId-ul în cererea API
                        VisitedCountyRequest visitedCounty = new VisitedCountyRequest(visitedCountyName, countryId, formattedDate, userId);

                        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                        apiService.addVisitedCounty(visitedCounty).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Log.d("YourMapsActivityRomania", "County saved successfully!");
                                    Toast.makeText(YourMapsActivityRomania.this, "County saved successfully!", Toast.LENGTH_SHORT).show();
                                    addPinToMap(countyId);
                                    // Actualizează istoricul locurilor vizitate
                                    loadVisitedCountiesDesc(userId);
                                    toggleLayouts(false); // Comută la istoricul locurilor vizitate

                                } else {
                                    Log.e("YourMapsActivityRomania", "Failed to save county: " + response.code() + " - " + response.message());
                                    Toast.makeText(YourMapsActivityRomania.this, "Failed to save county.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("YourMapsActivityRomania", "Error: " + t.getMessage());
                                Toast.makeText(YourMapsActivityRomania.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    private String formatDate(String date) {
                        try {
                            SimpleDateFormat inputFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);
                            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            return outputFormat.format(inputFormat.parse(date));
                        } catch (ParseException e) {
                            Log.e("YourMapsActivityRomania", "Error parsing date: " + e.getMessage());
                            return date; // Dacă nu se poate converti, returnează data originală
                        }
                    }

                    private void addPinToMap(String countyId) {
                        String jsCommand = "addPin('" + countyId + "')";
                        webView.evaluateJavascript(jsCommand, null);
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
                                        String currentCountyId = getCountyFromCoordinates(location.getLatitude(), location.getLongitude());
                                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                                        saveVisitedCountyToDatabase(currentCountyId, currentDate);
                                    } else {
                                        Toast.makeText(this, "Unable to get location.", Toast.LENGTH_SHORT).show();
                                    }
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

        private String getCountyFromCoordinates(double latitude, double longitude) {
            String closestCountyId = "Unknown";
            double shortestDistance = Double.MAX_VALUE;

            for (Map.Entry<String, Coordinates> entry : countyCoordinates.entrySet()) {
                Coordinates coordinates = entry.getValue();
                double distance = calculateDistance(latitude, longitude, coordinates.getLatitude(), coordinates.getLongitude());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closestCountyId = entry.getKey();
                }
            }

            Log.d("YourMapsActivityRomania", "Closest county: " + closestCountyId + " with distance: " + shortestDistance);
            return closestCountyId;
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

                                        Log.d("YourMapsActivityRomania", "Current location: " + latitude + ", " + longitude);

                                        String currentCountyId = getCountyFromCoordinates(latitude, longitude);

                                        if (currentCountyId.equals("Unknown")) {
                                            Toast.makeText(this, "Unable to identify county from location.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                        Log.d("YourMapsActivityRomania", "Detected county: " + currentCountyId + ", Date: " + currentDate);

                                        saveVisitedCountyToDatabase(currentCountyId, currentDate);
                                    } else {
                                        Toast.makeText(this, "Unable to get location. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("YourMapsActivityRomania", "Error getting location: " + e.getMessage());
                                    Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }

                    private class MapJavaScriptInterface {
                        @JavascriptInterface
                        public void onCountySelected(String countyId) {
                            Log.d("YourMapsActivityRomania", "Selected county: " + countyId);
                            runOnUiThread(() -> {
                                Toast.makeText(YourMapsActivityRomania.this, "County " + countyId + " selected", Toast.LENGTH_SHORT).show();
                                showDatePickerDialog(countyId); // Deschide picker-ul pentru data
                            });
                        }
                    }
                }
