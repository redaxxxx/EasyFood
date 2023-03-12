package com.example.easyfood.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.easyfood.Constants;
import com.example.easyfood.SharedPrefer;
import com.example.easyfood.VerifyConnection;
import com.example.easyfood.data.pojo.UserTable;
import com.example.easyfood.databinding.ActivityLoginBinding;
import com.example.easyfood.mvvm.LoginViewModel;
import com.example.easyfood.ui.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String email;
    private String password;
    private LoginViewModel viewModel;
    private SharedPrefer sharedPrefer;
    private VerifyConnection verifyConnection;
    private AlertDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseUser = mAuth.getCurrentUser();
        sharedPrefer = new SharedPrefer(this);
        verifyConnection = new VerifyConnection(this);

        Intent intent = getIntent();
        if (intent != null){
            username = intent.getStringExtra(Constants.USERNAME_KEY);
        }
        if (firebaseUser != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else {
            binding.loginButton.setOnClickListener(view -> {
                if (verifyConnection.isConnected()){
                    loginFirebaseAccount(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
                }
            });
        }

        binding.signUPButton.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    private void loginFirebaseAccount(String email ,String password){
        if (!TextUtils.isEmpty(binding.etEmail.getText()) &&
                !TextUtils.isEmpty(binding.etPassword.getText())){

            if (isValidEmail(binding.etEmail.getText().toString())){

                if (isValidPassword(binding.etPassword.getText().toString())){
                    progressDialog.setMessage("Please wait while Login");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        if (mAuth.getCurrentUser().isEmailVerified()){
                                            sharedPrefer.setSignInfo(username, email);
                                            progressDialog.dismiss();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                }
                            });

                }else {
                    binding.etPassword.setError("Invalid password");
                }

            }else {
                binding.etEmail.setError("Invalid email");
            }
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

//    public void observerGetData(){
//        viewModel.getGetAllUser().observe(this, new Observer<List<UserTable>>() {
//            @Override
//            public void onChanged(List<UserTable> userTables) {
//                userList = userTables;
//            }
//        });
//    }

}
