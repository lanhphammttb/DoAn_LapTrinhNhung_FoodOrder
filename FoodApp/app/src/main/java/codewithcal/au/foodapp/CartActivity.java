package codewithcal.au.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import codewithcal.au.foodapp.adapter.DetailBillAdapter;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class CartActivity extends AppCompatActivity {
    private ArrayList<DetailBill> arrayList;
    private DetailBill mDetailBill;
    private RecyclerView rcvCart;
    private DetailBillAdapter detailBillAdapter;
    private DatabaseHandler db;
    private int idDelete;
    private FloatingActionButton btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //thay doi title actionbar
        getSupportActionBar().setTitle(getString(R.string.listcart));
        db = new DatabaseHandler(this);
        getAllBills();
        detailBillAdapter = new DetailBillAdapter(this, arrayList, new DetailBillAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                rcvCart = findViewById(R.id.all_bill_recycler);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                alertDialog.setTitle("Bạn có chắc muốn xóa");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                //alertDialog.setMessage(selectedValue);
                idDelete = arrayList.get(position).getId();
                alertDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DetailBill x = arrayList.get(position);
                        db.deleteDetailBill(idDelete);
                        arrayList.remove(x);
                        detailBillAdapter.notifyDataSetChanged();
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
        rcvCart = findViewById(R.id.all_bill_recycler);
        rcvCart.setAdapter(detailBillAdapter);
        rcvCart.addItemDecoration(new DividerItemDecoration(rcvCart.getContext(), DividerItemDecoration.VERTICAL));
        rcvCart.setLayoutManager(new LinearLayoutManager(this));
        btnPay = findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPay();
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

    private void onClickPay() {
        Intent it = new Intent(CartActivity.this, PayActivity.class);
        startActivity(it);
    }
}