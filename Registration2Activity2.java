package com.example.myapplication1;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Registration2Activity2 extends AppCompatActivity {

    private EditText phoneInput;
    private CountryCodePicker ccp;
    private Button registerBtn;
    private RadioGroup genderGroup;
    private DatePicker datePicker;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration22);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Customers");

        // Get UI elements
        phoneInput = findViewById(R.id.phoneInput);
        ccp = findViewById(R.id.ccp);
        registerBtn = findViewById(R.id.buttonRegister);
        genderGroup = findViewById(R.id.genderGroup);
        datePicker = findViewById(R.id.datePicker);

        // Button Click Listener
        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String phoneNumber = ccp.getSelectedCountryCodeWithPlus() + phoneInput.getText().toString().trim();

        if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            phoneInput.setError("Valid phone number is required");
            phoneInput.requestFocus();
            return;
        }

        // Get selected gender
        int selectedId = genderGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedGender = findViewById(selectedId);
        String gender = selectedGender.getText().toString();

        // Get selected date
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month starts from 0
        int year = datePicker.getYear();
        String birthDate = day + "/" + month + "/" + year;

        // Save user details to Firebase Database
        saveUserToDatabase(phoneNumber, birthDate, gender);
    }

    private void saveUserToDatabase(String phoneNumber, String birthDate, String gender) {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("phone", phoneNumber);
        userMap.put("birthDate", birthDate);
        userMap.put("gender", gender);

        databaseReference.child(phoneNumber).setValue(userMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registration2Activity2.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registration2Activity2.this, CustomerActivity3.class));
                        finish();
                    } else {
                        Toast.makeText(Registration2Activity2.this, "Failed to register", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
