package codewithcal.au.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codewithcal.au.foodapp.adapter.PayDetailBillAdapter;
import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.retrofit.ApiInterface;
import codewithcal.au.foodapp.retrofit.RetrofitClient;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    TextView itemId, tvTotalPrice;
    String idUser;
    Button btnOrder;
    private ImageView btnBack;
    ApiInterface apiInterface;
    private RecyclerView rcvPay;
    private ArrayList<DetailBill> arrayList;
    private PayDetailBillAdapter payDetailBillAdapter;
    private DatabaseHandler db;
    private int idDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getSupportActionBar().setTitle(getString(R.string.pay));

        initView();

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        db = new DatabaseHandler(this);
        getAllBills();
        payDetailBillAdapter = new PayDetailBillAdapter(this, arrayList, new PayDetailBillAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PayActivity.this);
                alertDialog.setTitle("Bạn có chắc muốn xóa");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                //alertDialog.setMessage(selectedValue);
                idDelete = arrayList.get(position).getId();
                alertDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DetailBill x = arrayList.get(position);
                        db.deleteDetailBill(idDelete);
                        arrayList.remove(x);
                        payDetailBillAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        Intent intent = getIntent();
        idUser = intent.getStringExtra("id");
        itemId.setText(idUser);

        rcvPay.setAdapter(payDetailBillAdapter);
        rcvPay.addItemDecoration(new DividerItemDecoration(rcvPay.getContext(), DividerItemDecoration.VERTICAL));
        rcvPay.setLayoutManager(new LinearLayoutManager(this));
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBills();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });
        ArrayList<String> list = db.getTotalPrice();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        if(list.isEmpty() || list == null || list.get(0) == null){
            tvTotalPrice.setText("Total price: " + "0" + " VNĐ");
        }
        else if (list.size() >= 1){
            for(int i = 0; i < list.size(); i++) {
                numbers.add(Integer.parseInt(list.get(i)));
            }
            int sum = 0;
                for(int i = 0; i < numbers.size(); i++) {
                    sum += numbers.get(i);
                }
                tvTotalPrice.setText("Total price: " + String.valueOf(sum) + " VNĐ");
        }
    }

    private void initView() {
        rcvPay = findViewById(R.id.all_bill_pay_recycler);
        itemId = findViewById(R.id.tv_user_info_pay);
        btnOrder = findViewById(R.id.btn_order);
        tvTotalPrice = findViewById(R.id.tv_total_price_pay);
        btnBack = findViewById(R.id.icon_back);
    }

    private void getAllBills() {
        try {
            arrayList = new ArrayList<>();
            arrayList = db.getAllDetailBills();
        } catch (Exception ex) {
            Log.e("getAllDetailBills", ex.getMessage());
        }
    }

    private void postBills() {
        for (DetailBill detailBill : arrayList) {
            int idFood = detailBill.getId();
            int quantityFood = detailBill.getQuantity();
            int id = Integer.parseInt(idUser);
            Bill bill = new Bill(id, idFood, quantityFood);
            Call<Bill> call = apiInterface.createBill(bill);
            call.enqueue(new Callback<Bill>() {
                @Override
                public void onResponse(Call<Bill> call, Response<Bill> response) {
                    Toast.makeText(PayActivity.this, "Order " + detailBill.getName() + " success", Toast.LENGTH_SHORT).show();
                    db.dropTableDetailBill();
                }

                @Override
                public void onFailure(Call<Bill> call, Throwable t) {
                    Toast.makeText(PayActivity.this, "Call API ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        Intent intent = new Intent(PayActivity.this, PageActivity.class);
//        startActivity(intent);
        finish();
    }

    private void clickBack() {
        Intent intent = new Intent(PayActivity.this, PageActivity.class);
        startActivity(intent);
    }
}
