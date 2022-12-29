package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.databinding.ActivityLoginBinding;
import codewithcal.au.foodapp.databinding.ActivityRegisterBinding;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edUsername;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnRegister;
    ApiInterface apiInterface;

    private List<User> mListUser;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        mListUser = new ArrayList<>();
        getListUsers();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickRegister();
            }
        });
    }

    private void ClickRegister(){
        String strUsername = edUsername.getText().toString().trim();
        String strEmail = edEmail.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();

        if (mListUser == null || mListUser.isEmpty()){
            return;
        }

        boolean checkUser = true;
        for (User user : mListUser){
            if(strEmail.equals(user.getEmail())){
                checkUser = false;
            }
        }

        if (checkUser){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj_account", mUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(RegisterActivity.this, "Email has existed", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListUsers(){
        Call<List<User>> call = apiInterface.getUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mListUser = response.body();
                Log.e("List Users", mListUser.size() + "");
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
