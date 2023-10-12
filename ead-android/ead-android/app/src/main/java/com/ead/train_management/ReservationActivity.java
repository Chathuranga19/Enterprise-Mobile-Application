package com.ead.train_management;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.app.Dialog;
import androidx.appcompat.app.AlertDialog;
import com.ead.train_management.models.ReservationModel;
import com.ead.train_management.models.TravelModel;
import com.ead.train_management.service.ReservationService;
import com.ead.train_management.util.DBManager;
import com.ead.train_management.util.RetrofitManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

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

    String choosedId = "";
    private int year, month, day, hour, minute;
    static final int DATE_DIALOG_ID = 999;

    @SuppressLint({"Range", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking); // Set the content view for this activity

        //Picker data 
        final Calendar calendar = Calendar.getInstance();
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


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
        Call<List<TravelModel>> data = reservationService.fetchTrainData();

        // Asynchronously handle the response from the server
        data.enqueue(new Callback<List<TravelModel>>() {
            @Override
            public void onResponse(Call<List<TravelModel>> call1, Response<List<TravelModel>> response1) {
                if (response1.isSuccessful() && response1.body() != null) {
                    // If the response is successful and contains data
                    List<TravelModel> responseData = response1.body();
                    List<String> tData = new ArrayList<>();
                    List<String> tDetails = new ArrayList<>();

                    for (TravelModel d : responseData) {
                        tData.add(d.trainIDFetcher());
                        tDetails.add(d.getTrainName());
                    }

                    // Test output
                    for (String item : tDetails) {
                        Log.e("ObjTest", item);
                    }

                    populateSpinner(tDetails, tData);
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
            // Verify if the mandatory fields remain unpopulated and present a concise notification
            Toast.makeText(ReservationActivity.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
        } else {
            String selectedValue = "";
            if (spinner.getSelectedItem() != null) {
                selectedValue = spinner.getSelectedItem().toString();
            }

            // Isolate the date part of the reservation date
            String reservationDate = date.getText().toString().split("T")[0];

            // Retrieve the ticket count from the 'num' EditText
            String numberOfTickets = num.getText().toString();

            // Construct a confirmation message, incorporating the date and ticket count
            String confirmationMessage = "Chosen Train: " + selectedValue +
                    "\nDate: " + reservationDate +
                    "\nTotal Tickets: " + numberOfTickets;

            // Generate a confirmation dialog comprising the message
            AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
            builder.setTitle("Confirm Reservation");
            builder.setMessage(confirmationMessage);

            // Include a positive button to confirm the reservation
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User confirmed, proceed with creating the reservation
                    makeReserv();
                }
            });

            // Include a negative button to cancel
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            // Construct and display the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
});

        // Generate an OnClickListener to manage date-related events
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

// Method for exhibiting the DatePicker dialog
@Override
protected Dialog onCreateDialog(int id) {
    if (id == DATE_DIALOG_ID) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }
    return null;
}

// Listener for the date picker dialog
private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
        year = selectedYear;
        month = selectedMonth;
        day = selectedDay;

        // Display the chosen date in the EditText
        String dateTime = String.format("%04d-%02d-%02dT%02d:%02d", year, month + 1, day, hour, minute);
        date.setText(dateTime);
    }
};


    private void makeReserv() {
        String selectedValue = "";
        if (spinner.getSelectedItem() != null) {
            selectedValue = spinner.getSelectedItem().toString();
        }

        ReservationModel u = new ReservationModel();

        Log.e("DateTest", date.getText().toString());
        u.setRfid(userNIC);
        u.setTid(userID);
        u.setStatus(false);
        u.setTrain(choosedId);
        u.setDate(date.getText().toString());
        u.setPhone(phone.getText().toString());
        u.setEmail(email.getText().toString());
        u.setName(name.getText().toString());
        u.setPassno(Integer.parseInt(num.getText().toString()));
        Call<String> call = reservationService.makeReservation(u);

        // Asynchronously handle the response from the server
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response1) {
                 Log.e("ObjTest", response1.body());
                Intent intent = new Intent(getApplicationContext(), BookingListActivity.class);
                startActivity(intent);
                Toast.makeText(ReservationActivity.this, "Reservation is Created", Toast.LENGTH_SHORT).show();
                // if (response1.isSuccessful() && response1.body() != null) {
                //     // If the response is successful, navigate to BookingListActivity
                //     Intent intent = new Intent(getApplicationContext(), BookingListActivity.class);
                //     startActivity(intent);
                // } else {
                //     Log.e("Foundd-ReservationActivity", "Error: " + response1.message());
                //     // If there's an error in the response, show a toast message
                //     Toast.makeText(ReservationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                // }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // If the network request fails, show a toast message
                Toast.makeText(ReservationActivity.this, "Reservation Failed. Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSpinner(List<String> tDetails, final List<String> tData) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tDetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                choosedId = tData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
}
