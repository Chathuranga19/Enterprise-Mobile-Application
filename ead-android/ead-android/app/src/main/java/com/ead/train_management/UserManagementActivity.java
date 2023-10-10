package com.ead.train_management;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ead.train_management.models.AccStatusModel;
import com.ead.train_management.models.TravelerHandlerModel;
import com.ead.train_management.service.AuthService;
import com.ead.train_management.util.DBManager;
import com.ead.train_management.util.RetrofitManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class UserManagementActivity extends AppCompatActivity {

    private AuthService lgService; // Service for user authentication
    private String nic = ""; // User's NIC number
    private String uid = ""; // User's UID (User Identification) number
    private DBManager dbHelper; // Helper class for managing SQLite database
    private SQLiteDatabase db; // Database instance
    private Cursor cursor; // Cursor for database queries
    EditText fname;
    EditText lname;
    EditText phone;
    Button upButton;
    Button rmButton;
    Button lgButton;
    EditText date;

    @SuppressLint({"Range", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Set the content view for this activity

        // Initialize the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set a listener for bottom navigation items
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.book:
                    // Navigate to ReservationActivity
                    startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                    overridePendingTransition(0, 0); // Apply transition animation
                    return true;
                case R.id.home:
                    return true; // Stay on the current activity
                case R.id.view:
                    // Navigate to BookingListActivity
                    startActivity(new Intent(getApplicationContext(), BookingListActivity.class));
                    overridePendingTransition(0, 0); // Apply transition animation
                    return true;
            }
            return false;
        });

        // Initialize UI elements
        fname = findViewById(R.id.fname2);
        lname = findViewById(R.id.lname2);
        phone = findViewById(R.id.phone2);
        date = findViewById(R.id.date2);
        upButton = findViewById(R.id.upButton);
        rmButton = findViewById(R.id.rmButton);
        lgButton = findViewById(R.id.lgButton);

        // Initialize AuthService using Retrofit
        lgService = RetrofitManager.getClient().create(AuthService.class);

        // Initialize the DBManager and get a writable database instance
        dbHelper = new DBManager(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        // Define the columns to retrieve from the "users" table
        String[] projection = {
                "nic", // NIC column
                "uid" // UID column
        };

        // Query the database to retrieve user information
        cursor = db.query(
                "users", // Table name
                projection, // Columns to retrieve
                null,
                null,
                null,
                null,
                null
        );

        // Check if there are results in the cursor
        if (cursor.moveToFirst()) {
            // Retrieve NIC and UID values from the cursor
            nic = cursor.getString(cursor.getColumnIndex("nic"));
            uid = cursor.getString(cursor.getColumnIndex("uid"));
        }

        // Create a Retrofit call to fetch user profile data
        Call<TravelerHandlerModel> data = lgService.getUserProfile(nic);

        // Asynchronously handle the response from the server
        data.enqueue(new Callback<TravelerHandlerModel>() {
            @Override
            public void onResponse(Call<TravelerHandlerModel> call1, Response<TravelerHandlerModel> response1) {
                if (response1.isSuccessful() && response1.body() != null) {
                    // If the response is successful and contains data
                    TravelerHandlerModel res = response1.body();
                    // Populate UI elements with user profile data
                    fname.setText(res.getFname());
                    lname.setText(res.getLname());
                    phone.setText(res.getPhone());
                    date.setText(res.getDate());
                } else {
                    // If there's an error in the response, show a toast message
                    Toast.makeText(UserManagementActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TravelerHandlerModel> call, Throwable t) {
                // If the network request fails, show a toast message
                Toast.makeText(UserManagementActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().equals("") && lname.getText().toString().equals("") && phone.getText().toString().equals("")) {
                    // Check if required fields are empty and show a toast message
                    Toast.makeText(UserManagementActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a TravelerHandlerModel object with updated user data
                    TravelerHandlerModel u = new TravelerHandlerModel();
                    u.setAcc(true);
                    u.setNic(nic);
                    u.setPhone(phone.getText().toString());
                    u.setFname(fname.getText().toString());
                    u.setLname(lname.getText().toString());
                    u.setDate(date.getText().toString());
                    u.setId(uid);
                    // Create a Retrofit call to update user profile
                    Call<TravelerHandlerModel> call = lgService.Update(u);

                    // Asynchronously handle the response from the server
                    call.enqueue(new Callback<TravelerHandlerModel>() {
                        @Override
                        public void onResponse(Call<TravelerHandlerModel> call, Response<TravelerHandlerModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // If the response is successful, navigate to UserManagementActivity
                                Intent intent = new Intent(getApplicationContext(), UserManagementActivity.class);
                                startActivity(intent);
                            } else {
                                // If there's an error in the response, show a toast message
                                Toast.makeText(UserManagementActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TravelerHandlerModel> call, Throwable t) {
                            // If the network request fails, show a toast message
                            Toast.makeText(UserManagementActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    // Handle the logout button click
    public void LogOut(View view) {
        // Delete user data from the database and navigate to AppLaunchActivity
        int deletedRows = db.delete("users", null, null);
        cursor.close();
        dbHelper.close();
        Intent intent = new Intent(this, AppLaunchActivity.class);
        startActivity(intent);
    }

    // Handle the disable button click
    public void Disable(View view) {
        AccStatusModel d = new AccStatusModel();
        d.setAcc(false);
        // Create a Retrofit call to disable the user account
        Call<TravelerHandlerModel> data = lgService.Dis(nic, d);

        // Asynchronously handle the response from the server
        data.enqueue(new Callback<TravelerHandlerModel>() {
            @Override
            public void onResponse(Call<TravelerHandlerModel> call1, Response<TravelerHandlerModel> response1) {
                if (response1.isSuccessful() && response1.body() != null) {
                    // If the response is successful, log out the user
                    LogOut(view);
                } else {
                    // If there's an error in the response, show a toast message
                    Toast.makeText(UserManagementActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TravelerHandlerModel> call, Throwable t) {
                // If the network request fails, show a toast message
                Toast.makeText(UserManagementActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
