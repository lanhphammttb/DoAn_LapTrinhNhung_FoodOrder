package codewithcal.au.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.adapter.AllMenuAdapter;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    RecyclerView allMenuRecyclerView;

    AllMenuAdapter allMenuAdapter;

    List<Food> allmenuList;

    TextView tvUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);


        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        tvUserInfo = findViewById(R.id.tv_user_info);
        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null){
            User user = (User) bundleReceive.get("obj_account");
            if(user != null){
                tvUserInfo.setText(user.toString());
            }
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        allMenuRecyclerView.addItemDecoration(itemDecoration);

        allmenuList= new ArrayList<>();

        callApiGetUsers();
    }

    private void callApiGetUsers() {
        Call<List<Food>> call = apiInterface.getAllData("IwAR39n1rANXBg_wi7kur0O2faFkCOasJ5sXBmLl0wI_D8fMUzKZxqqirOtWo");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {

                allmenuList = response.body();
                allMenuAdapter = new AllMenuAdapter(allmenuList);
                allMenuRecyclerView.setAdapter(allMenuAdapter);
                allMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
