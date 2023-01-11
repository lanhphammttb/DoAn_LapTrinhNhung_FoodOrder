package codewithcal.au.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    String id;
    List<Food> foodList;

    public BillAdapter(Context context, List<Bill> allmenuList, List<Food> foodList) {
        this.context = context;
        this.allmenuList = allmenuList;
        this.foodList = foodList;
    }

    public BillAdapter(String id) {
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
        holder.quantity.setText(String.valueOf(allmenuList.get(position).getQuantity()));
        holder.food.setText(String.valueOf(allmenuList.get(position).getFoodId()));
        String foodName = getFoodName(bill.getId());
        holder.food.setText(foodName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, HistoryFragment.class);
                //String idFoods = String.valueOf(allmenuList.get(position).getId());
                i.putExtra("userId", id);
                i.putExtra("id", String.valueOf(allmenuList.get(position).getId()));
                i.putExtra("food", allmenuList.get(position).getFoodId());
                i.putExtra("quantity", allmenuList.get(position).getQuantity());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (allmenuList != null) {
            return allmenuList.size();
        } else return 0;
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {

        private final TextView idBill, quantity, food;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            idBill = itemView.findViewById(R.id.id_bill);
            quantity = itemView.findViewById(R.id.quantity_cart);
            food = itemView.findViewById(R.id.name_cart);
        }
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
}
