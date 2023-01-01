package codewithcal.au.foodapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import codewithcal.au.foodapp.model.User;

public class PageActivity extends AppCompatActivity {
    TextView tvUserId;
    TextView tvUserInfo;
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        tvUserId = findViewById(R.id.tv_user_id_page);
        tvUserInfo = findViewById(R.id.tv_user_info_page);
        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null){
            User user = (User) bundleReceive.get("obj_account");
            if(user != null){
                tvUserInfo.setText(user.toString());
                String ids = String.valueOf(user.getId());
                tvUserId.setText(ids);
            }
        }
        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(8);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.item_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cartFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }
}