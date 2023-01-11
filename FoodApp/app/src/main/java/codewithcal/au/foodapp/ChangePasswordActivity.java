package codewithcal.au.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.ApiInterface2;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.retrofit.RetrofitClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView tvOldPass, tvNewPass, tvReNewPass;
    EditText edOldPass, edNewPass, edReNewPass;
    ImageView btnBack;
    Button btnChangePass;
    private ApiInterface apiInterface;
    private User mUser;
    private String newpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null) {
            User user = (User) bundleReceive.get("user_to_change");
            if (user != null) {
                mUser = user;
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(FoodDetails.this, PageActivity.class);
                finish();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putUser();
            }
        });
    }

    private void putUser() {
        if(edOldPass.getText().equals(mUser.getPassword())){
            newpass = edNewPass.getText().toString().trim();
            User userChange = new User(mUser.getId(), mUser.getUsername(), mUser.getEmail(), newpass);
            Call<User> call = apiInterface.updateUser(mUser.getId(), userChange);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(ChangePasswordActivity.this, "Change password success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ChangePasswordActivity.this, "Call API ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Error password", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        tvOldPass = findViewById(R.id.tv_old_password_change);
        tvNewPass = findViewById(R.id.tv_new_password_change);
        tvReNewPass = findViewById(R.id.tv_renew_password_change);
        edOldPass = findViewById(R.id.ed_old_password_change);
        edNewPass = findViewById(R.id.ed_new_password_change);
        edReNewPass = findViewById(R.id.ed_renew_password_change);
        btnBack = findViewById(R.id.icon_back_change_password);
        btnChangePass = findViewById(R.id.btn_change_password);
    }
}