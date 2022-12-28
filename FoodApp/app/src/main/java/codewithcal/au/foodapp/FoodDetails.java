package codewithcal.au.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetails extends AppCompatActivity {
    TextView itemName, itemPrice, itemType;

    String name, price, type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

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

    }
}