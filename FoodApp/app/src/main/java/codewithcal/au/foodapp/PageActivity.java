package codewithcal.au.foodapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import codewithcal.au.foodapp.databinding.ActivityPageBinding;
import codewithcal.au.foodapp.databinding.FragmentItemListDialogListDialogBinding;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.User;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class PageActivity extends AppCompatActivity {
    TextView tvUserId;
    TextView tvUserInfo;
    private ArrayList<DetailBill> arrayList;
    private DatabaseHandler db;
    BottomNavigationView bottomNavigationView;
    private ActivityPageBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    CartFragment cartFragment = new CartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        db = new DatabaseHandler(this);
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

        //Intent i = new Intent(PageActivity.this, PayActivity.class);
        //i.putExtra("id",tvUserId.getText());
        //startActivity(i);

        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
        badgeDrawable.setVisible(true);
        int count = getCountFoodInCart();
        badgeDrawable.setNumber(count);

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