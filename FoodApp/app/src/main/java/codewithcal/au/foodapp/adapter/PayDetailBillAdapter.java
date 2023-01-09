package codewithcal.au.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codewithcal.au.foodapp.R;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;


public class PayDetailBillAdapter extends RecyclerView.Adapter<PayDetailBillAdapter.MyViewHolder> {
    private final ArrayList<DetailBill> arrayList;
    private final Context context;
    private final ClickListeners clickListeners;
    private DatabaseHandler db;
    private PayDetailBillAdapter payDetailBillAdapter;

    public PayDetailBillAdapter(Context context, ArrayList<DetailBill> arrayList, ClickListeners clickListeners) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickListeners = clickListeners;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_pay_recycler_items, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DetailBill detailBill = arrayList.get(position);
        //String ids = String.valueOf(arrayList.get(position).getId());
        holder.id.setText(String.valueOf(arrayList.get(position).getId()));
        holder.name.setText(detailBill.getName());
        holder.quantity.setText(Integer.toString(detailBill.getQuantity()));
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db = new DatabaseHandler(v.getContext());
                    int ida = arrayList.get(position).getId();
                    DetailBill f = db.getDetailBill(ida);
                    DetailBill d = new DetailBill(ida, f.getName(), f.getQuantity() + 1);
                    db.updateDetailBill(d);
                    holder.quantity.setText(Integer.toString(f.getQuantity()));
                    //notifyItemChanged(holder.getBindingAdapterPosition());
                } catch (Exception ex) {
                    Log.e("Add food to cart", ex.getMessage());
                }
            }
        });

        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db = new DatabaseHandler(v.getContext());
                    int ids = arrayList.get(position).getId();
                    DetailBill f = db.getDetailBill(ids);
                    if (f.getQuantity() == 1) {
                        db.deleteDetailBill(ids);
                        arrayList.remove(arrayList.get(position));
                        notifyDataSetChanged();
                    } else {
                        DetailBill d = new DetailBill(ids, f.getName(), f.getQuantity() - 1);
                        db.updateDetailBill(d);
                        holder.quantity.setText(Integer.toString(d.getQuantity()));
                    }
                } catch (Exception ex) {
                    Log.e("Sub food to cart", ex.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null && arrayList.size() > 0)
            return arrayList.size();
        else
            return 0;
    }

    public interface ClickListeners {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView id, name, quantity, price;
        Button btnAdd, btnSub;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_pay);
            name = itemView.findViewById(R.id.name_pay);
            quantity = itemView.findViewById(R.id.quantity_pay);
            price = itemView.findViewById(R.id.price_pay);
            btnAdd = itemView.findViewById(R.id.btn_add_pay);
            btnSub = itemView.findViewById(R.id.btn_sub_pay);

            //dang ky su kien click cho view: cach 2.
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
//
        }

        @Override
        public void onClick(View v) {
            clickListeners.onItemClick(getBindingAdapterPosition(), v);//callback forwarding
        }

        @Override
        public boolean onLongClick(View v) {
            clickListeners.onItemLongClick(getBindingAdapterPosition(), v);
            return true;
        }
    }
}
