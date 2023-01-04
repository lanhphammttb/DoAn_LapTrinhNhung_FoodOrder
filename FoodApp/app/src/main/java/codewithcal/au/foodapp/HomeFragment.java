package codewithcal.au.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.adapter.AllMenuAdapter;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ApiInterface apiInterface;

    RecyclerView allMenuRecyclerView;

    AllMenuAdapter allMenuAdapter;

    List<Food> allmenuList;
    String id;

    public static HomeFragment getInstance(String id) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_id", id);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        allMenuRecyclerView = view.findViewById(R.id.all_menu_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        allMenuRecyclerView.addItemDecoration(itemDecoration);

        allmenuList = new ArrayList<>();
        callApiGetUsers();
        if (getArguments() != null) {
            this.id = getArguments().getString("user_id");
        }
        AllMenuAdapter idAdapter;
        idAdapter = new AllMenuAdapter(id);
    }

    private void callApiGetUsers() {
        Call<List<Food>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                allmenuList = response.body();
                allMenuAdapter = new AllMenuAdapter(getContext(), allmenuList);
                allMenuRecyclerView.setAdapter(allMenuAdapter);
                allMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}