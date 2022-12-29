package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class FoodDetails extends AppCompatActivity {
    TextView itemName, itemPrice, itemType;
    String name, price, type;
    Button btnAddToCart;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        db = new DatabaseHandler(this);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");

        itemName = findViewById(R.id.name);
        itemPrice= findViewById(R.id.price);
        itemType = findViewById(R.id.type);

        itemName.setText(name);
        itemPrice.setText("$" + price);
        itemType.setText("Loại: " + type);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickAdd();
            }
        });
    }

    private void ClickAdd() {
        String name = itemName.getText().toString();
        String price = itemPrice.getText().toString();
        String type = itemType.getText().toString();
        try {
            //Lưu vào sqlite
            Food f = new Food(name, price, type);
            long newid = db.addFood(f);
            f.setId((int) newid);
            Intent i = new Intent(FoodDetails.this, CartActivity.class);
            i.putExtra("id",f.getId());
            i.putExtra("name",f.getName());
            i.putExtra("price",f.getPrice());
            i.putExtra("type",f.getType());
            this.startActivity(i);
        } catch (Exception ex) {
            Log.e("Them food to cart", ex.getMessage());
        }
    }
}