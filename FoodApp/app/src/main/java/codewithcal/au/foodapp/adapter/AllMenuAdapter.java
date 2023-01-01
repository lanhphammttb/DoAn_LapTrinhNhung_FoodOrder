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
import codewithcal.au.foodapp.R;
import codewithcal.au.foodapp.model.Food;

public class AllMenuAdapter extends RecyclerView.Adapter<AllMenuAdapter.AllMenuViewHolder> {
    Context context;
    List<Food> allmenuList;

    public AllMenuAdapter(Context context ,List<Food> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
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
        String ids = String.valueOf(allmenuList.get(position).getId());
        holder.idFood.setText(ids);
        holder.nameFood.setText(allmenuList.get(position).getName());
        holder.priceFood.setText("$"+allmenuList.get(position).getPrice());
        holder.typeFood.setText(allmenuList.get(position).getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                String ids = String.valueOf(allmenuList.get(position).getId());
                i.putExtra("id", ids);
                i.putExtra("name", allmenuList.get(position).getName());
                i.putExtra("price", allmenuList.get(position).getPrice());
                i.putExtra("type", allmenuList.get(position).getType());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(allmenuList != null){
            return allmenuList.size();
        }
        return 0;
    }

    public static class AllMenuViewHolder extends RecyclerView.ViewHolder{

        private final TextView idFood, nameFood, typeFood, priceFood;

        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            idFood = itemView.findViewById(R.id.id_food);
            nameFood = itemView.findViewById(R.id.name_food);
            priceFood = itemView.findViewById(R.id.price_food);
            typeFood= itemView.findViewById(R.id.type_food);
        }
    }

}
