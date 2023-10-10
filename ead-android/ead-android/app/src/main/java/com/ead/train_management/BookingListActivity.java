package com.ead.train_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.ead.train_management.models.ViewBookingModel;
import com.ead.train_management.service.ReservationService;
import com.ead.train_management.util.DBManager;
import com.ead.train_management.util.BookingListAdapter;
import com.ead.train_management.util.RetrofitManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class BookingListActivity extends AppCompatActivity {
    private ReservationService bgService; // Service for managing reservations
    private String nic = ""; // User's NIC number
    private String uid = ""; // User's UID (User Identification) number
    private DBManager dbHelper; // Helper class for managing SQLite database
    private SQLiteDatabase db; // Database instance
    private Cursor cursor; // Cursor for database queries

    @SuppressLint({"NonConstantResourceId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view); // Set the content view for this activity

        // Initialize the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.view);

        // Set a listener for bottom navigation items
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.book:
                    // Navigate to ReservationActivity
                    startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                    overridePendingTransition(0, 0); // Apply transition animation
                    return true;
                case R.id.home:
                    // Navigate to UserManagementActivity
                    startActivity(new Intent(getApplicationContext(), UserManagementActivity.class));
                    overridePendingTransition(0, 0); // Apply transition animation
                    return true;
                case R.id.view:
                    return true; // Stay on the current activity
            }
            return false;
        });

        // Initialize the ReservationService using Retrofit
        bgService = RetrofitManager.getClient().create(ReservationService.class);

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

        // Initialize the RecyclerView for displaying booking data
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a Retrofit call to fetch booking data for the user's NIC
        Call<List<ViewBookingModel>> data = bgService.getBooking(nic);

        // Asynchronously handle the response from the server
        data.enqueue(new Callback<List<ViewBookingModel>>() {
            @Override
            public void onResponse(Call<List<ViewBookingModel>> call1, Response<List<ViewBookingModel>> response1) {
                if (response1.isSuccessful() && response1.body() != null) {
                    // If the response is successful and contains data
                    List<ViewBookingModel> dataList = response1.body();
                    // Remove bookings marked as "cc"
                    dataList.removeIf(ViewBookingModel::isCc);
                    // Create and set the adapter for the RecyclerView
                    BookingListAdapter adapter = new BookingListAdapter(dataList);
                    recyclerView.setAdapter(adapter);
                } else {
                    // If there's an error in the response, show a toast message
                    Toast.makeText(BookingListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViewBookingModel>> call, Throwable t) {
                // If the network request fails, show a toast message
                Toast.makeText(BookingListActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
