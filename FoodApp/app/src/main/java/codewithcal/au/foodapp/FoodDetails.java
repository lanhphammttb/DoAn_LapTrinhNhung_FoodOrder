package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import codewithcal.au.foodapp.databinding.ActivityFoodDetailsBinding;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class FoodDetails extends AppCompatActivity {
    TextView itemId, itemName, itemPrice, itemType;
    String id, name, price, type;
    Button btnAddToCart;
    ImageView btnBack;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        db = new DatabaseHandler(this);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");

        itemId = findViewById(R.id.id);
        itemName = findViewById(R.id.name);
        itemPrice= findViewById(R.id.price);
        itemType = findViewById(R.id.type);
        btnBack = findViewById(R.id.icon_back);

        itemId.setText(id);
        itemName.setText(name);
        itemPrice.setText("$" + price);
        itemType.setText("Loại: " + type);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetails.this, PageActivity.class);
                startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickAdd();
            }
        });
    }

    private void ClickAdd() {
        String idt = itemId.getText().toString();
        int id = Integer.parseInt(idt);
        String name = itemName.getText().toString();
        try {
            //Lưu vào sqlite
            DetailBill f = new DetailBill(id, name, 1);
            db.addDetailBill(f);
            //Intent i = new Intent(FoodDetails.this, CartActivity.class);
            //this.startActivity(i);

            //BottomSheetDialogCart bottomSheet = new BottomSheetDialogCart();
            //bottomSheet.show(getSupportFragmentManager(), "bottomSheetDialogCart");
            ItemListDialogFragment item = new ItemListDialogFragment();
            item.show(getSupportFragmentManager(), "itemListDialogFragment");
        } catch (Exception ex) {
            Log.e("Add food to cart", ex.getMessage());
        }
    }

}