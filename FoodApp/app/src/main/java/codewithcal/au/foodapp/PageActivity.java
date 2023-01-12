package codewithcal.au.foodapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class PageActivity extends AppCompatActivity {
    TextView tvUserId;
    TextView tvUserInfo;
    ImageView imageProfile;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    private ArrayList<DetailBill> arrayList;
    private DatabaseHandler db;
    private String ids;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        db = new DatabaseHandler(this);

        initView();

        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null) {
            User user = (User) bundleReceive.get("obj_account");
            if (user != null) {
                tvUserInfo.setText(user.toString());
                ids = String.valueOf(user.getId());
                tvUserId.setText(ids);
                mUser = user;
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.getInstance(mUser.getId(), mUser.toString())).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
        badgeDrawable.setVisible(true);
        int count = getCountFoodInCart();
        badgeDrawable.setNumber(count);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.getInstance(mUser.getId(), mUser.toString())).commit();
                        return true;
                    case R.id.item_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CartFragment.getInstance(ids)).commit();
                        return true;
                    case R.id.item_notify:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, HistoryFragment.getInstance(mUser.getId())).commit();
                        return true;
                    case R.id.item_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ProfileFragment.getInstance(mUser)).commit();
                        return true;
                }

                return false;
            }
        });


    }
    private void initView() {
        tvUserId = findViewById(R.id.tv_user_id_page);
        tvUserInfo = findViewById(R.id.tv_user_info_page);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        imageProfile = findViewById(R.id.profile_image);
    }

    private int getCountFoodInCart() {
        int count = 0;
        try {
            arrayList = new ArrayList<>();
            count = db.getDetailBillCount();
        } catch (Exception ex) {
            Log.e("getDetailBillCount", ex.getMessage());
        }
        return count;
    }
}