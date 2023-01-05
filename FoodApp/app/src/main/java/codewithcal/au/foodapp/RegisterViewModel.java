package codewithcal.au.foodapp;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends Observable {
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public ObservableInt progressBar;
    private ApiInterface apiInterface;
    private final Context context;
    private List<User> mListUser;

    public RegisterViewModel(Context context) {
        this.context = context;
        progressBar = new ObservableInt(View.GONE);
    }

    public void sendRegisterRequest(String name, String email, String pass) {
        progressBar.set(View.VISIBLE);
        mListUser = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        CheckUser(name, email, pass);
    }

    private void CheckUser(String name, String email, String pass) {
        Call<List<User>> call = apiInterface.getUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressBar.set(View.GONE);
                //showToast(""+response.body().toString());
                mListUser = response.body();
                User user = new User(email, pass);
                if (user.isValidEmail() && user.isValidPassword()) {
                    boolean isHasUser = true;
                    for (User users : mListUser) {
                        if (email.equals(users.getEmail())) {
                            isHasUser = false;
                            break;
                        }
                    }
                    if (isHasUser) {
                        showToast("Registering");
                        User new_account = new User(name, email, pass);
                        Call<User> post = apiInterface.createUser(new_account);
                        post.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> post, Response<User> response) {
                                showToast("Create account success");
                            }

                            @Override
                            public void onFailure(Call<User> post, Throwable t) {
                                showToast("Create account fail");
                            }
                        });
                    } else {
                        showToast("Email has existed");
                    }
                } else {
                    showToast("Email or password invalid");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.set(View.GONE);
                showToast("" + t.getMessage());
            }
        });
    }

    void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
