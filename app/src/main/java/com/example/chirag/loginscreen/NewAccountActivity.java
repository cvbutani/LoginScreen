package com.example.chirag.loginscreen;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chirag.loginscreen.data.LoginContract;

public class NewAccountActivity extends AppCompatActivity {

    public static final int LOGIN_LOADER = 1;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmailAddress;
    private EditText etPassword;

    private Button mCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        findAllViews();

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLoginInfo();

                Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findAllViews() {
        etFirstName = findViewById(R.id.first_name);
        etLastName = findViewById(R.id.last_name);
        etEmailAddress = findViewById(R.id.email_address);
        etPassword = findViewById(R.id.user_password);
        mCreateAccount = findViewById(R.id.sign_in);
    }

    private void saveLoginInfo() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String emailAddress = etEmailAddress.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();
        Log.i("USER INFO: ", "FIRSTNAME: " + firstName );
        Log.i("USER INFO: ", "LASTNAME: " + lastName );
        Log.i("USER INFO: ", "EMAIL ADDRESS: " + emailAddress );
        Log.i("USER INFO: ", "PASSWORD: " + userPassword );
        ContentValues values = new ContentValues();

        values.put(LoginContract.LoginEntry.FIRST_NAME, firstName);
        values.put(LoginContract.LoginEntry.LAST_NAME, lastName);
        values.put(LoginContract.LoginEntry.EMAIL_ADDRESS, emailAddress);
        values.put(LoginContract.LoginEntry.USER_PASSWORD, userPassword);
    }
}
