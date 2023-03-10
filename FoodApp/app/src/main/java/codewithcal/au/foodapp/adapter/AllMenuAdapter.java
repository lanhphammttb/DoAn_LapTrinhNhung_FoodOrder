package codewithcal.au.foodapp.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import codewithcal.au.foodapp.FoodDetails;
import codewithcal.au.foodapp.HomeFragment;
import codewithcal.au.foodapp.ItemListDialogFragment;
import codewithcal.au.foodapp.R;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.model.Food;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class AllMenuAdapter extends RecyclerView.Adapter<AllMenuAdapter.AllMenuViewHolder> implements Filterable {
    Context context;
    List<Food> allmenuList;
    List<Food> allmenuListOld;
    int id;

    public AllMenuAdapter(Context context, List<Food> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
        this.allmenuListOld = allmenuList;
    }

    public AllMenuAdapter(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public AllMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_recycler_items, parent, false);
        return new AllMenuViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull AllMenuViewHolder holder, int position) {
        holder.idFood.setText(String.valueOf(allmenuList.get(position).getId()));
        holder.nameFood.setText(allmenuList.get(position).getName());
        int pricet = Integer.parseInt(allmenuList.get(position).getPrice());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.priceFood.setText(decimalFormat.format(pricet) + " ??");
        holder.timeFood.setText(allmenuList.get(position).getDeliveryTime());
        holder.ratingFood.setText(allmenuList.get(position).getRating());
        holder.chargesfood.setText(allmenuList.get(position).getDeliveryCharges());
        holder.noteFood.setText(allmenuList.get(position).getNote());

        Glide.with(context).load(allmenuList.get(position).getImageUrl()).into(holder.imageFood);

        holder.imageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("userId", id);
                i.putExtra("id", String.valueOf(allmenuList.get(position).getId()));
                i.putExtra("name", allmenuList.get(position).getName());
                i.putExtra("price", allmenuList.get(position).getPrice());
                i.putExtra("rating", allmenuList.get(position).getRating());
                i.putExtra("image", allmenuList.get(position).getImageUrl());
                i.putExtra("note", allmenuList.get(position).getNote());
                context.startActivity(i);
            }
        });

        holder.nameFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //L??u v??o sqlite
                    DatabaseHandler db = new DatabaseHandler(v.getContext());
                    int price = Integer.parseInt(allmenuList.get(position).getPrice());
                    DetailBill f = new DetailBill(allmenuList.get(position).getId(),
                            allmenuList.get(position).getName(), 1, price);
                    db.addDetailBill(f);
                } catch (Exception ex) {
                    Log.e("Add food to cart", ex.getMessage());
                }
                ItemListDialogFragment item = new ItemListDialogFragment();
                AppCompatActivity activity = (AppCompatActivity) context;
                item.show(activity.getSupportFragmentManager(), "itemListDialogFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (allmenuList != null) {
            return allmenuList.size();
        } else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allmenuList = allmenuListOld;
                } else {
                    List<Food> filteredList = new ArrayList<>();
                    for (Food row : allmenuListOld) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    allmenuList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = allmenuList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allmenuList = (ArrayList<Food>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
    public static class AllMenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView idFood, nameFood, noteFood, ratingFood, timeFood, chargesfood,  priceFood;
        private ImageView imageFood;

        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.image_food);
            idFood = itemView.findViewById(R.id.id_food);
            nameFood = itemView.findViewById(R.id.name_food);
            noteFood = itemView.findViewById(R.id.note_food);
            ratingFood = itemView.findViewById(R.id.rating_food);
            timeFood = itemView.findViewById(R.id.delivery_time_food);
            chargesfood = itemView.findViewById(R.id.delivery_charge_food);
            priceFood = itemView.findViewById(R.id.price_food);
        }
    }

}
