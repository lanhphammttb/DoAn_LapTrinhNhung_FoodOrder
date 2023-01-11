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
import codewithcal.au.foodapp.adapter.BillAdapter;
import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.ApiInterface2;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.retrofit.RetrofitClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    ApiInterface2 apiInterface2;
    ApiInterface apiInterface;

    RecyclerView allMenuRecyclerView;

    BillAdapter billAdapter;

    AllMenuAdapter allMenuAdapter;

    List<Bill> billList;
    List<Food> foodList;
    String id;

    public static HistoryFragment getInstance(String id) {
        HistoryFragment historyFragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_id", id);
        historyFragment.setArguments(bundle);
        return historyFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        apiInterface2 = RetrofitClient2.getRetrofitInstance().create(ApiInterface2.class);

        allMenuRecyclerView = view.findViewById(R.id.all_menu_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        allMenuRecyclerView.addItemDecoration(itemDecoration);

        billList = new ArrayList<>();
        getAllFood();
        callApiGetUsers();
        if (getArguments() != null) {
            this.id = getArguments().getString("user_id");
        }
        BillAdapter idAdapter;
        idAdapter = new BillAdapter(id);
    }

    private void callApiGetUsers() {
        Call<List<Bill>> call = apiInterface.getAllBill();
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                billList = response.body();
                billAdapter = new BillAdapter(getContext(), billList, foodList);
                allMenuRecyclerView.setAdapter(billAdapter);
                billAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(getContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllFood() {
        Call<List<Food>> call = apiInterface2.getAllData();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                foodList = response.body();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
