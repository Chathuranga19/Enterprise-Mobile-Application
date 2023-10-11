package com.ead.train_management;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ead.train_management.models.UserAuthModel;
import com.ead.train_management.models.AuthResponseDataModel;
import com.ead.train_management.models.TravelerHandlerModel;
import com.ead.train_management.service.AuthService;
import com.ead.train_management.util.DBManager;
import com.ead.train_management.util.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppLaunchActivity extends AppCompatActivity {
    private AuthService authService; // Service for user authentication
    EditText inputUsername; // User input for username
    EditText inputPassword; // User input for password
    Button signinAction; // Button to trigger login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        signinAction = findViewById(R.id.loginButton);

        // Initialize Retrofit service for user authentication
        authService = RetrofitManager.getClient().create(AuthService.class);

        // Initialize database helper
        DBManager databaseManager = new DBManager(getApplicationContext());
        SQLiteDatabase db = databaseManager.getWritableDatabase();

        signinAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputUsername.getText().toString().equals("") && inputPassword.getText().toString().equals("")) {
                    // Display a message when username and password are not filled
                    Toast.makeText(AppLaunchActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a user authentication model and populate it with user input
                    UserAuthModel loginRequest = new UserAuthModel();
                    loginRequest.setNic(inputUsername.getText().toString());
                    loginRequest.setPassword(inputPassword.getText().toString());

                    // Make a Retrofit API call to log in the user
                    Call<AuthResponseDataModel> call = authService.Login(loginRequest);
                    call.enqueue(new Callback<AuthResponseDataModel>() {
                        @Override
                        public void onResponse(Call<AuthResponseDataModel> call, Response<AuthResponseDataModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                AuthResponseDataModel userResponse = response.body();
                                if (userResponse.getRole().equals("traveler")) {
                                    // If the user is a traveler, retrieve user profile
                                    Call<TravelerHandlerModel> data = authService.getUserProfile(userResponse.getNic());

                                    data.enqueue(new Callback<TravelerHandlerModel>() {
                                        @Override
                                        public void onResponse(Call<TravelerHandlerModel> call1, Response<TravelerHandlerModel> response1) {
                                            if (response1.isSuccessful() && response1.body() != null) {
                                                TravelerHandlerModel res = response1.body();
                                                if (res.isAcc()) {
                                                    // If the account is active, insert user data into the database
                                                    ContentValues values = new ContentValues();
                                                    values.put("nic", userResponse.getNic());
                                                    values.put("uid", res.getId());
                                                    long newRowId = db.insert("users", null, values);

                                                    if (newRowId != -1) {
                                                        // Navigate to the main app
                                                        Intent intent = new Intent(getApplicationContext(), UserManagementActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        // Display an error message if database insertion fails
                                                        Toast.makeText(AppLaunchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // Display a message if the account is disabled
                                                    Toast.makeText(AppLaunchActivity.this, "Account is disabled", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                // Display an error message if user profile retrieval fails
                                                Toast.makeText(AppLaunchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<TravelerHandlerModel> call, Throwable t) {
                                            // Display an error message if the API call fails
                                            Toast.makeText(AppLaunchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    // Display a message if the account type is incorrect
                                    Toast.makeText(AppLaunchActivity.this, "Wrong Account Type", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Display an error message if the login API call fails
                                Toast.makeText(AppLaunchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthResponseDataModel> call, Throwable t) {
                            // Display an error message if the login request fails
                            Toast.makeText(AppLaunchActivity.this, "Failed to log", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void navigateToReg(View view) {
        // Navigate to the registration screen
        Intent intent = new Intent(this, AccountRegActivity.class);
        startActivity(intent);
    }
}
