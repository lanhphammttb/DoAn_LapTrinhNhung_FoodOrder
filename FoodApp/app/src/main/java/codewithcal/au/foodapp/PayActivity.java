package codewithcal.au.foodapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.adapter.DetailBillAdapter;
import codewithcal.au.foodapp.adapter.PayDetailBillAdapter;
import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.User;
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

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        db = new DatabaseHandler(this);
        getAllBills();
        payDetailBillAdapter = new PayDetailBillAdapter(this, arrayList, new PayDetailBillAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                rcvPay = findViewById(R.id.all_bill_pay_recycler);
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(PayActivity.this);
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
                    } });
                alertDialog.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //alertDialog.dismiss();
                    } });
                alertDialog.show();
            }
        });
        itemId = findViewById(R.id.tv_user_info_pay);
        Intent intent = getIntent();
        idUser = intent.getStringExtra("id");
        itemId.setText(idUser);

        rcvPay= findViewById(R.id.all_bill_pay_recycler);
        rcvPay.setAdapter(payDetailBillAdapter);
        rcvPay.addItemDecoration(new DividerItemDecoration(rcvPay.getContext(), DividerItemDecoration.VERTICAL));
        rcvPay.setLayoutManager(new LinearLayoutManager(this));
        btnOrder = findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBills();
            }
        });

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
        for (DetailBill detailBill : arrayList){
            int idFood = detailBill.getId();
            int quantityFood = detailBill.getQuantity();
            int id = Integer.parseInt(idUser);
            Bill bill = new Bill(id,idFood + 1,quantityFood);
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
