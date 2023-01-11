package codewithcal.au.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserActivity extends AppCompatActivity {

    EditText edName, edEmail;
    ImageView btnBack;
    Button btnChangeUser;
    private String name, email, password;
    private int id;
    private ApiInterface apiInterface;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        initView();

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

        btnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putUser();
            }
        });
    }

    private void putUser() {
        name = edName.getText().toString().trim();
        email = edEmail.getText().toString().trim();
        User userChange = new User(mUser.getId(), name, email, mUser.getPassword());
        Call<User> call = apiInterface.updateUser(mUser.getId(), userChange);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(ChangeUserActivity.this, "Change account success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangeUserActivity.this, "Call API ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        edName = findViewById(R.id.ed_name_change);
        edEmail = findViewById(R.id.ed_email_change);
        btnChangeUser = findViewById(R.id.btn_change_user);
        btnBack = findViewById(R.id.icon_back_change);
    }
}