package codewithcal.au.foodapp.retrofit;

import java.util.List;

import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.Food;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface2 {
    @GET("food")
    Call<List<Food>> getAllData();
}
