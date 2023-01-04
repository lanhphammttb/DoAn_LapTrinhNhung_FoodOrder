package codewithcal.au.foodapp.retrofit;


import java.util.List;

import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    //https://63a2dfae471b38b206ff0ad6.mockapi.io/api/v1/food?fbclid=IwAR39n1rANXBg_wi7kur0O2faFkCOasJ5sXBmLl0wI_D8fMUzKZxqqirOtWo
    @GET("food")
    Call<List<Food>> getAllData();

    @GET("account")
    Call<List<User>> getUser();

    @GET("food")
    Call<List<DetailBill>> getFood();

    @POST("account")
    Call<User> createUser(@Body User acount);

    @PUT("account")
    Call<User> updateAccount(@Body User user);

    @POST("bill")
    Call<Bill> createBill(@Body Bill bill);
}
