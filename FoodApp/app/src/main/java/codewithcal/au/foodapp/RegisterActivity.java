package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.databinding.ActivityRegisterBinding;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{
    private EditText edUsername;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnToLogin;
    private ImageView btnBack;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActivityRegisterBinding activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        RegisterViewModel registerViewModel = new RegisterViewModel();
        activityRegisterBinding.setRegisterViewModel(registerViewModel);

        edUsername = findViewById(R.id.ed_username);
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        btnToLogin = findViewById(R.id.button_login);
        btnBack = findViewById(R.id.icon_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickToLogin();
            }
        });
    }

    private void ClickToLogin(){
        String strUsername = edUsername.getText().toString().trim();
        String strEmail = edEmail.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("obj_account", mUser);
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    private void clickBack(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_account", mUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
