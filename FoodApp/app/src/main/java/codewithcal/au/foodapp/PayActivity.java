package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    TextView itemId;
    String idUser;
    Button btnOrder;
    ApiInterface apiInterface;
    private ArrayList<DetailBill> arrayList;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().setTitle(getString(R.string.pay));

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Intent intent = getIntent();
        itemId = findViewById(R.id.tv_user_info_pay);
        idUser = intent.getStringExtra("id");
        itemId.setText(idUser);

        btnOrder = findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBills();
            }
        });
    }

    private void postBills() {
        arrayList = new ArrayList<>();
        arrayList = db.getAllDetailBills();
        for (int i = 0; i<2; i++){
            Bill bill = new Bill(1,i,1);
            Call<Bill> call = apiInterface.createBill(bill);
            call.enqueue(new Callback<Bill>() {
                @Override
                public void onResponse(Call<Bill> call, Response<Bill> response) {
                    Toast.makeText(PayActivity.this, "OK NHA", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PayActivity.this, FinishActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<Bill> call, Throwable t) {
                    Toast.makeText(PayActivity.this, "Call API ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
