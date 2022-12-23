package codewithcal.au.foodapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    //https://63a2dfae471b38b206ff0ad6.mockapi.io/api/v1/food?fbclid=IwAR39n1rANXBg_wi7kur0O2faFkCOasJ5sXBmLl0wI_D8fMUzKZxqqirOtWo
    //https://localhost:5001/api/food/getall
    private static final String BASE_URL = "https://63a2dfae471b38b206ff0ad6.mockapi.io/api/v1/";

    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        }
        return retrofit;

    }

}
