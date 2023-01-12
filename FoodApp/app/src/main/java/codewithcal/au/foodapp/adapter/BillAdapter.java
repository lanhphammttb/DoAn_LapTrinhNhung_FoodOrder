package codewithcal.au.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import codewithcal.au.foodapp.FoodDetails;
import codewithcal.au.foodapp.HistoryFragment;
import codewithcal.au.foodapp.R;
import codewithcal.au.foodapp.model.Bill;
import codewithcal.au.foodapp.model.Food;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    Context context;
    List<Bill> allmenuList;
    int id;
    List<Food> foodList;

    public BillAdapter(Context context, List<Bill> allmenuList, List<Food> foodList) {
        this.context = context;
        this.allmenuList = allmenuList;
        this.foodList = foodList;
    }

    public BillAdapter(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public BillAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_recycler_items, parent, false);
        return new BillAdapter.BillViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        Bill bill = allmenuList.get(position);
        //String idFoods = String.valueOf(allmenuList.get(position).getId());
        holder.idBill.setText(String.valueOf(allmenuList.get(position).getId()));
        holder.quantity.setText("Quantity: " + String.valueOf(allmenuList.get(position).getQuantity()));
        holder.food.setText(String.valueOf(getFoodName(allmenuList.get(position).getFoodId())));
        int totalPrice = Integer.parseInt(getFoodPrice(bill.getFoodId())) * bill.getQuantity();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        holder.price.setText("Total price: " + decimalFormat.format(totalPrice) + " VNĐ");

        Glide.with(context).load(getFoodImageUrl(allmenuList.get(position).getFoodId())).into(holder.imageFood);

    }

    @Override
    public int getItemCount() {
        if (allmenuList != null) {
            return allmenuList.size();
        } else return 0;
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {

        private final TextView idBill, quantity, food, price;
        private final ImageView imageFood;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            idBill = itemView.findViewById(R.id.id_bill);
            quantity = itemView.findViewById(R.id.quantity_cart);
            food = itemView.findViewById(R.id.name_cart);
            price = itemView.findViewById(R.id.price_cart);
            imageFood = itemView.findViewById(R.id.image_cart);
        }
    }

    private String getFoodImageUrl(int id) {
        String imageUrl = null;
        for (Food food : foodList) {
            if (id == food.getId()) {
                imageUrl= food.getImageUrl();
            }
        }
        return imageUrl;
    }

    private String getFoodName(int id) {
        String name = null;
        for (Food food : foodList) {
            if (id == food.getId()) {
                name = food.getName();
            }
        }
        return name;
    }

    private String getFoodPrice(int id) {
        String price = null;
        for (Food food : foodList) {
            if (id == food.getId()) {
                price = food.getPrice();
            }
        }
        return price;
    }
}
