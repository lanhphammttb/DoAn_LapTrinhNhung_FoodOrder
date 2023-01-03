package codewithcal.au.foodapp;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends BaseObservable {
    private String email;
    private String password;
    public ObservableField<String> messageRegister = new ObservableField<>();
    public ObservableField<Boolean> isShowMessage = new ObservableField<>();
    public ObservableField<Boolean> isSuccess = new ObservableField<>();
    public ObservableField<Boolean> isShowRegister = new ObservableField<>();
    public ObservableField<Boolean> isShowToLogin = new ObservableField<>();
    ApiInterface apiInterface;
    private List<User> mListUser;
    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickRegister(){
        User user = new User(getEmail(), getPassword());
        isShowMessage.set(true);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        mListUser = new ArrayList<>();
        getListUsers();
        boolean checkUser = true;
        for (User users : mListUser){
            if(getEmail().equals(users.getEmail())){
                checkUser = false;
                break;
            }
        }

        if (user.isValidEmail() && user.isValidPassword() && checkUser){
            messageRegister.set("Register success");
            isSuccess.set(true);
            isShowToLogin.set(true);
            isShowRegister.set(false);
            postUser();
        }else
        {
            messageRegister.set("Email or password invalid/Email has existed");
            isSuccess.set(false);
        }
    }

    private void postUser() {
        User user = new User(getEmail(), getPassword());
        Call<User> call = apiInterface.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
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
                messageRegister.set("Server is not responding");
                isSuccess.set(false);
                isShowToLogin.set(false);
            }
        });
    }
}
