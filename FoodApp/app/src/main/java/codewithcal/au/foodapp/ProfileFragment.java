package codewithcal.au.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class ProfileFragment extends Fragment {
    private ApiInterface apiInterface;
    private DatabaseHandler db;
    private User user;
    private TextView tvUserName, tvEmail;

    public static ProfileFragment getInstance(User user) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_id", user);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        db = new DatabaseHandler(getContext());
        if (getArguments() != null) {
            this.user = (User) getArguments().getSerializable("user_id");
        }
        tvUserName = view.findViewById(R.id.text_username);
        tvEmail = view.findViewById(R.id.text_email);

        tvUserName.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
    }

}
