package codewithcal.au.foodapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codewithcal.au.foodapp.adapter.DetailBillAdapter;
import codewithcal.au.foodapp.adapter.UserAdapter;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class ProfileFragment extends Fragment {
    private ArrayList<User> arrayList;
    ApiInterface apiInterface;
    private UserAdapter userAdapter;

    RecyclerView allMenuRecyclerView;

    private DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        db = new DatabaseHandler(getContext());
        try {
            arrayList = new ArrayList<>();
            arrayList = db.getUser();
        } catch (Exception ex) {
            Log.e("getProfile", ex.getMessage());
        }
        userAdapter = new UserAdapter(getContext(), arrayList, new UserAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }
}
