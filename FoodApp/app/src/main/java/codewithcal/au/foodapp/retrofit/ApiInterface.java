package codewithcal.au.foodapp.retrofit;


import java.util.List;

import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //https://63a2dfae471b38b206ff0ad6.mockapi.io/api/v1/food?fbclid=IwAR39n1rANXBg_wi7kur0O2faFkCOasJ5sXBmLl0wI_D8fMUzKZxqqirOtWo
    @GET("food")
    Call<List<Food>> getAllData(@Query("fbclid") String fbclid);

    @GET("account")
    Call<List<User>> getUser(@Query("fbclid") String fbclid);


}
