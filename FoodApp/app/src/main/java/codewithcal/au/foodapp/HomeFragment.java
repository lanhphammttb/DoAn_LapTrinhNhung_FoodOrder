package codewithcal.au.foodapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.adapter.AllMenuAdapter;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.ApiInterface2;
import codewithcal.au.foodapp.retrofit.RetrofitClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ApiInterface2 apiInterface;

    RecyclerView allMenuRecyclerView;

    AllMenuAdapter allMenuAdapter;

    List<Food> allmenuList;

    int id;

    String name;

    private MenuItem menuItem;

    private SearchView searchView;

    private Toolbar toolbar;

    TextView tvUserInfo;

    public static HomeFragment getInstance(int id, String name) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_id", id);
        bundle.putSerializable("user_name", name);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        //activity.getSupportActionBar().setTitle(" Search...");
        apiInterface = RetrofitClient2.getRetrofitInstance().create(ApiInterface2.class);

        allMenuRecyclerView = view.findViewById(R.id.all_menu_recycler);
        allMenuRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        allMenuRecyclerView.addItemDecoration(itemDecoration);

        allmenuList = new ArrayList<>();
        callApiGetFoods();

        if (getArguments() != null) {
            this.id = getArguments().getInt("user_id");
            this.name = getArguments().getString("user_name");
        }
        AllMenuAdapter idAdapter;
        idAdapter = new AllMenuAdapter(id);
        tvUserInfo = view.findViewById(R.id.tv_user_info);
        tvUserInfo.setText("Hi " + name);
    }
    private void callApiGetFoods() {
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(true);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                allMenuAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                allMenuAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}