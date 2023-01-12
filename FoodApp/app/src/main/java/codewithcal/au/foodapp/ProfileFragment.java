package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class ProfileFragment extends Fragment {
    private ApiInterface apiInterface;
    private DatabaseHandler db;
    private User user;
    private TextView tvUserName, tvEmail, btnChangeInfo;
    private CardView cardLogout, btnChangePass;

    public static ProfileFragment getInstance(User user) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
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
            this.user = (User) getArguments().getSerializable("user");
        }

        initView(view);

        tvUserName.setText(user.getUsername());
        tvEmail.setText(user.getEmail());

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_to_change", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_to_change", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        tvUserName = view.findViewById(R.id.text_username);
        tvEmail = view.findViewById(R.id.text_email);
        cardLogout = view.findViewById(R.id.card_logout);
        btnChangeInfo = view.findViewById(R.id.btn_change_info);
        btnChangePass = view.findViewById(R.id.card_change_pass);
    }

}
