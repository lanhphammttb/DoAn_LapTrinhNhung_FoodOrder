package codewithcal.au.foodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity {
    TextView itemId;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().setTitle(getString(R.string.pay));

        Intent intent = getIntent();

        idUser = intent.getStringExtra("id");

        itemId = findViewById(R.id.tv_user_info_pay);

        itemId.setText(idUser);

    }
}
