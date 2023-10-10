package com.ead.train_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ead.train_management.models.userManagementModel;
import com.ead.train_management.models.travelerHandlerModel;
import com.ead.train_management.service.AuthService;
import com.ead.train_management.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRegActivity extends AppCompatActivity {

    private AuthService lgService;
    EditText nic;
    EditText password;
    EditText fname;
    EditText lname;
    EditText phone;
    Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nic = findViewById(R.id.nic1);
        password = findViewById(R.id.password1);
        fname = findViewById(R.id.fname1);
        lname = findViewById(R.id.lname1);
        phone = findViewById(R.id.phone1);
        regButton = findViewById(R.id.regButton);

        // Initialize Retrofit service for user authentication
        lgService = RetrofitClient.getClient().create(AuthService.class);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nic.getText().toString().equals("") && password.getText().toString().equals("")
                        && fname.getText().toString().equals("") && lname.getText().toString().equals("")
                        && phone.getText().toString().equals("")) {
                    // Display a message when all details are not filled
                    Toast.makeText(AccountRegActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a user management model and populate it with user input
                    userManagementModel u = new userManagementModel();
                    userManagementModel.UserInfo ui = u.new UserInfo();
                    u.setAcc(true);
                    u.setNic(nic.getText().toString());
                    u.setPhone(phone.getText().toString());
                    u.setFname(fname.getText().toString());
                    u.setLname(lname.getText().toString());
                    ui.setPassword(password.getText().toString());
                    ui.setRole("traveler");
                    u.setData(ui);

                    // Make a Retrofit API call to register the user
                    Call<travelerHandlerModel> call = lgService.Reg(u);
                    call.enqueue(new Callback<travelerHandlerModel>() {
                        @Override
                        public void onResponse(Call<travelerHandlerModel> call, Response<travelerHandlerModel> response) {
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
                        public void onFailure(Call<travelerHandlerModel> call, Throwable t) {
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
