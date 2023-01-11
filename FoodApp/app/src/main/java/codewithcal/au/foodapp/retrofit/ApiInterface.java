package codewithcal.au.foodapp.retrofit;


import java.util.List;

import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("account")
    Call<List<User>> getUser();

    @GET("bill")
    Call<List<Bill>> getAllBill();

    @POST("account")
    Call<User> createUser(@Body User acount);

    @PUT("account/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @POST("bill")
    Call<Bill> createBill(@Body Bill bill);
}
