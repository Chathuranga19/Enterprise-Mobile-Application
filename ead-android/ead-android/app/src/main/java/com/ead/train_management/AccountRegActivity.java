package com.ead.train_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ead.train_management.models.TravelerHandlerModel;
import com.ead.train_management.models.UserManagementModel;
import com.ead.train_management.service.AuthService;
import com.ead.train_management.util.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRegActivity extends AppCompatActivity {

    private AuthService authService; // Service for user authentication
    EditText inputNIC; // User input for NIC
    EditText inputPassword; // User input for password
    EditText inputFirstName; // User input for first name
    EditText inputLastName; // User input for last name
    EditText inputPhone; // User input for phone
    Button registerAction; // Button to trigger registration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputNIC = findViewById(R.id.nic1);
        inputPassword = findViewById(R.id.password1);
        inputFirstName = findViewById(R.id.fname1);
        inputLastName = findViewById(R.id.lname1);
        inputPhone = findViewById(R.id.phone1);
        registerAction = findViewById(R.id.regButton);

        // Initialize Retrofit service for user authentication
        authService = RetrofitManager.getClient().create(AuthService.class);

        registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputNIC.getText().toString().equals("") && inputPassword.getText().toString().equals("")
                        && inputFirstName.getText().toString().equals("") && inputLastName.getText().toString().equals("")
                        && inputPhone.getText().toString().equals("")) {
                    // Display a message when all details are not filled
                    Toast.makeText(AccountRegActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a user management model and populate it with user input
                    UserManagementModel u = new UserManagementModel();
                    UserManagementModel.UserInfo ui = u.new UserInfo();
                    u.setAcc(true);
                    u.setNic(inputNIC.getText().toString());
                    u.setPhone(inputPhone.getText().toString());
                    u.setFname(inputFirstName.getText().toString());
                    u.setLname(inputLastName.getText().toString());
                    ui.setPassword(inputPassword.getText().toString());
                    ui.setRole("traveler");
                    u.setData(ui);

                    // Make a Retrofit API call to register the user
                    Call<TravelerHandlerModel> call = authService.Reg(u);
                    call.enqueue(new Callback<TravelerHandlerModel>() {
                        @Override
                        public void onResponse(Call<TravelerHandlerModel> call, Response<TravelerHandlerModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // Registration successful, navigate to the main app
                                Intent intent = new Intent(getApplicationContext(), AppLaunchActivity.class);
                                startActivity(intent);
                            } else {
                                // Registration failed, display an error message
                                Toast.makeText(AccountRegActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TravelerHandlerModel> call, Throwable t) {
                            // Registration request failed, display an error message
                            Toast.makeText(AccountRegActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void navigateToLogin(View view) {
        // Navigate to the login screen
        Intent intent = new Intent(this, AppLaunchActivity.class);
        startActivity(intent);
    }
}
