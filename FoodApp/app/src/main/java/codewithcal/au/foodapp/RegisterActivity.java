package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import codewithcal.au.foodapp.databinding.ActivityRegisterBinding;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.presenter.Presenter;
public class RegisterActivity extends AppCompatActivity {
    private String username, email, password;
    private EditText edUsername, edEmail, edPassword;
    private Button btnRegister;
    private ImageView btnBack;
    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding activityRegisterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerViewModel = new RegisterViewModel(this);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);

        initView();

        activityRegisterBinding.setPresenter(new Presenter() {
            @Override
            public void registerData() {
                username = activityRegisterBinding.edUsername.getText().toString().trim();
                email = activityRegisterBinding.edEmail.getText().toString();
                password = activityRegisterBinding.edPassword.getText().toString();
                registerViewModel.sendRegisterRequest(username, email, password);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickRegister();
            }
        });
    }

    private void initView() {
        edUsername = findViewById(R.id.ed_username);
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        btnRegister = findViewById(R.id.button_register);
        btnBack = findViewById(R.id.icon_back);
    }

    private void ClickRegister() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void clickBack() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
