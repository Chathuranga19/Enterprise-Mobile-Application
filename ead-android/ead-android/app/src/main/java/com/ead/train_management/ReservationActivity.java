package com.ead.train_management;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ead.train_management.models.ReservationModel;
import com.ead.train_management.models.TravelModel;
import com.ead.train_management.service.ReservationService;
import com.ead.train_management.util.DBManager;
import com.ead.train_management.util.RetrofitManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {

    private ReservationService reservationService; // Service for managing reservations
    private String userNIC = ""; // User's NIC number
    private String userID = ""; // User's ID number
    private DBManager dbManager; // Helper class for managing SQLite database
    private SQLiteDatabase database; // Database instance
    private Cursor dbCursor; // Cursor for database queries
    EditText name;
    EditText email;
    EditText num;
    EditText date;
    EditText phone;
    Button addButton;
    Spinner spinner;

    @SuppressLint({"Range", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking); // Set the content view for this activity

        // Initialize the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.book);

        // Set a listener for bottom navigation items
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.book:
                    return true; // Stay on the current activity
                case R.id.home:
                    // Navigate to UserManagementActivity
                    startActivity(new Intent(getApplicationContext(), UserManagementActivity.class));
                    overridePendingTransition(0, 0); // Apply transition animation
                    return true;
                case R.id.view:
                    // Navigate to BookingListActivity
                    startActivity(new Intent(getApplicationContext(), BookingListActivity.class));
                    overridePendingTransition(0, 0); // Apply transition animation
                    return true;
            }
            return false;
        });

        // Initialize UI elements
        name = findViewById(R.id.name3);
        spinner = findViewById(R.id.spinner);
        phone = findViewById(R.id.phone3);
        date = findViewById(R.id.date3);
        email = findViewById(R.id.email3);
        num = findViewById(R.id.num3);
        addButton = findViewById(R.id.addButton);

        // Initialize ReservationService using Retrofit
        reservationService = RetrofitManager.getClient().create(ReservationService.class);

        // Initialize the DBManager and get a writable database instance
        dbManager = new DBManager(getApplicationContext());
        database = dbManager.getWritableDatabase();

        // Define the columns to retrieve from the "users" table
        String[] projection = {
                "nic", // NIC column
                "uid" // UID column
        };

        // Query the database to retrieve user information
        dbCursor = database.query(
                "users", // Table name
                projection, // Columns to retrieve
                null,
                null,
                null,
                null,
                null
        );

        // Check if there are results in the cursor
        if (dbCursor.moveToFirst()) {
            // Retrieve NIC and UID values from the cursor
            userNIC = dbCursor.getString(dbCursor.getColumnIndex("nic"));
            userID = dbCursor.getString(dbCursor.getColumnIndex("uid"));
        }

        // Create a Retrofit call to fetch train data
        Call<List<TravelModel>> data = reservationService.getTrain();

        // Asynchronously handle the response from the server
        data.enqueue(new Callback<List<TravelModel>>() {
            @Override
            public void onResponse(Call<List<TravelModel>> call1, Response<List<TravelModel>> response1) {
                if (response1.isSuccessful() && response1.body() != null) {
                    // If the response is successful and contains data
                    List<TravelModel> responseData = response1.body();
                    List<String> dt = new ArrayList<>();

                    for (TravelModel d : responseData) {
                        dt.add(d.trainIDFetcher());
                    }

                    // Test output
                    for (String item : dt) {
                        Log.e("ObjTest", item);
                    }
                    populateSpinner(dt);
                } else {
                    // If there's an error in the response, show a toast message
                    Toast.makeText(ReservationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TravelModel>> call, Throwable t) {
                // If the network request fails, show a toast message
                Toast.makeText(ReservationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") && email.getText().toString().equals("") && phone.getText().toString().equals("")) {
                    // Check if required fields are empty and show a toast message
                    Toast.makeText(ReservationActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    String selectedValue = "";
                    if (spinner.getSelectedItem() != null) {
                        selectedValue = spinner.getSelectedItem().toString();
                    }
                    ReservationModel u = new ReservationModel();

                    u.setRfid(userNIC);
                    u.setTid(userID);
                    u.setStatus(false);
                    u.setTrain(selectedValue);
                    u.setPhone(phone.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setName(name.getText().toString());
                    u.setPassno(Integer.parseInt(num.getText().toString()));
                    Call<String> call = reservationService.createBooking(u);

                    // Asynchronously handle the response from the server
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response1) {
                            if (response1.isSuccessful() && response1.body() != null) {
                                // If the response is successful, navigate to BookingListActivity
                                Intent intent = new Intent(getApplicationContext(), BookingListActivity.class);
                                startActivity(intent);
                            } else {
                                Log.e("Foundd-ReservationActivity", "Error: " + response1.message());
                                // If there's an error in the response, show a toast message
                                Toast.makeText(ReservationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            // If the network request fails, show a toast message
                            Toast.makeText(ReservationActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void populateSpinner(List<String> dropdownItems) {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropdownItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
