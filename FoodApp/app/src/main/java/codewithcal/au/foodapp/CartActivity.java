package codewithcal.au.foodapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codewithcal.au.foodapp.adapter.CartAdapter;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Food> arrayList;
    private TextView itemName, itemPrice, itemType;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private ActivityResultLauncher<Intent> launcherforAdd;
    private DatabaseHandler db;
    private int idDelete;
    //String name, price, type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //thay doi title actionbar
        getSupportActionBar().setTitle(getString(R.string.listcart));
        db = new DatabaseHandler(this);
        //initResultLauncher();
        getAllContacts();
        //Intent intent = getIntent();
        /*name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");

        itemName = findViewById(R.id.name_cart);
        itemPrice= findViewById(R.id.price_cart);
        itemType = findViewById(R.id.type_cart);

        itemName.setText(name);
        itemPrice.setText("$" + price);
        itemType.setText("Loại: " + type);*/
        cartAdapter = new CartAdapter(this, arrayList, new CartAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                rcvCart = findViewById(R.id.all_bill_recycler);
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(CartActivity.this);
                alertDialog.setTitle("Bạn có chắc muốn xóa");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                //alertDialog.setMessage(selectedValue);
                idDelete = arrayList.get(position).getId();
                alertDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Food x = arrayList.get(position);
                        db.deleteContact(idDelete);
                        arrayList.remove(x);
                        cartAdapter.notifyDataSetChanged();
                    } });
                alertDialog.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //alertDialog.dismiss();
                    } });
                alertDialog.show();
            }
        });
        rcvCart = findViewById(R.id.all_bill_recycler);
        rcvCart.setAdapter(cartAdapter);
        rcvCart.addItemDecoration(new DividerItemDecoration(rcvCart.getContext(), DividerItemDecoration.VERTICAL));
        rcvCart.setLayoutManager(new LinearLayoutManager(this));
    }
    private void getAllContacts() {
        try {
            arrayList = new ArrayList<>();
            arrayList = db.getAllFoods();
        } catch (Exception ex) {
            Log.e("getAllFood", ex.getMessage());
        }
    }
    private void initResultLauncher() {
        try {
            launcherforAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result != null && result.getResultCode() == RESULT_OK) {
                    //lay ve contact tai day va cap nhat len giao dien
                    Food f = (Food) result.getData().getSerializableExtra("food");
                    //cap nhat tren giao
                    arrayList.add(f);
                    cartAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception ex) {
            Log.e("initResultLauncher", ex.getMessage());
        }
    }
}
