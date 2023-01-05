package codewithcal.au.foodapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import codewithcal.au.foodapp.adapter.AllMenuAdapter;
import codewithcal.au.foodapp.databinding.ActivityLoginBinding;
import codewithcal.au.foodapp.databinding.ActivityMainBinding;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private EditText edEmail, edPassword;
    private Button btnLogin, btnRegister;
    private ApiInterface apiInterface;
    private List<User> mListUser;
    private User mUser;
    private DatabaseHandler db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private void initPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        initPreferences();

        db = new DatabaseHandler(this);

        initView();

        String email_saved = sharedPreferences.getString("email", "");
        String password_saved = sharedPreferences.getString("password", "");

        edEmail.setText(email_saved);
        edPassword.setText(password_saved);

        mListUser = new ArrayList<>();
        getListUsers();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickLogin();
            }
        });
    }

    private void initView() {
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void ClickLogin() {
        String strEmail = edEmail.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();

        if (mListUser == null || mListUser.isEmpty()){
            return;
        }

        boolean isHasUser = false;
        for (User user : mListUser){
            if(strEmail.equals(user.getEmail()) && strPassword.equals(user.getPassword())){
                isHasUser = true;
                mUser = user;
                break;
            }
        }

        if (isHasUser){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
            alertDialog.setTitle("Remember me!");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString("email", String.valueOf(edEmail.getText()));
                    editor.putString("password", String.valueOf(edPassword.getText()));
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, PageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("obj_account", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, PageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("obj_account", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
              alertDialog.show();
        }else{
            Toast.makeText(LoginActivity.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
