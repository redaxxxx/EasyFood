package com.example.easyfood.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.easyfood.Constants;
import com.example.easyfood.GoogleConfigs;
import com.example.easyfood.SharedPrefer;
import com.example.easyfood.VerifyConnection;
import com.example.easyfood.data.pojo.UserTable;
import com.example.easyfood.databinding.ActivitySignUpBinding;
import com.example.easyfood.mvvm.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    LoginViewModel viewModel;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private VerifyConnection verifyConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        verifyConnection = new VerifyConnection(this);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        binding.signUPButton.setOnClickListener(view -> {
            if (verifyConnection.isConnected()){
                createFirebaseAccount(binding.signupEmail.getText().toString(), binding.signupPassword.getText().toString());
            }
        });
    }

    private void createFirebaseAccount(String email, String password){

        if (!TextUtils.isEmpty(binding.signupUsername.getText()) &&
                !TextUtils.isEmpty(binding.signupEmail.getText()) &&
                !TextUtils.isEmpty(binding.signupPassword.getText())){

            if (isValidUserName(binding.signupUsername.getText().toString())){
                if(isValidEmail(binding.signupEmail.getText().toString())){
                    if (isValidPassword(binding.signupPassword.getText().toString())){
                        progressDialog.setMessage("Please wait while registration");
                        progressDialog.setTitle("Registration");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){

                                            progressDialog.dismiss();


                                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                            intent.putExtra(Constants.USERNAME_KEY,
                                                                    binding.signupUsername.getText().toString());
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });
                                        }
                                    }
                                });
//
                    } else {
                        binding.signupPassword.setError("Invalid password");
                    }
                }else {
                    binding.signupEmail.setError("Invalid Email");
                }
            }else {
                binding.signupUsername.setError("Invalid username");
            }

        }


    }

    private boolean isValidUserName(String userName) {
        if (userName.length() >= 8) {
            Toast.makeText(getApplicationContext(), "valid username", Toast.LENGTH_SHORT).show();
            // or
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
            //or
            return false;
        }
    }


    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
            // or
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            //or
            return false;
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}