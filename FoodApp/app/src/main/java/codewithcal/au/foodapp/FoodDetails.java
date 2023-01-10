package codewithcal.au.foodapp;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class FoodDetails extends AppCompatActivity {
    ImageView imageView;
    RatingBar ratingBar;
    TextView itemId, itemName, itemPrice, itemRating, itemNote;
    String userId, id, name, price, rating, imageUrl, note;
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
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");
        note = intent.getStringExtra("note");

        imageView = findViewById(R.id.image);
        itemId = findViewById(R.id.id);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        itemNote = findViewById(R.id.note);
        ratingBar = findViewById(R.id.ratingBar);

        btnBack = findViewById(R.id.icon_back);

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
        itemId.setText(id);
        itemName.setText(name);
        itemPrice.setText(price + " đ");
        itemRating.setText(rating);
        itemNote.setText(note);
        ratingBar.setRating(Float.parseFloat(rating));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(FoodDetails.this, PageActivity.class);
                finish();
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
            ItemListDialogFragment item = new ItemListDialogFragment();
            item.show(getSupportFragmentManager(), "itemListDialogFragment");
        } catch (Exception ex) {
            Log.e("Add food to cart", ex.getMessage());
        }
    }
}