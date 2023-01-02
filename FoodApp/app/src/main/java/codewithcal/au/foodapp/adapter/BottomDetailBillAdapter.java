package codewithcal.au.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codewithcal.au.foodapp.R;
import codewithcal.au.foodapp.databinding.FragmentItemListDialogListDialogItemBinding;
import codewithcal.au.foodapp.model.DetailBill;


public class BottomDetailBillAdapter extends RecyclerView.Adapter<BottomDetailBillAdapter.MyViewHolder> {
    private ArrayList<DetailBill> arrayList;
    private Context context;
    private ClickListeners clickListeners;

    public BottomDetailBillAdapter(Context context, ArrayList<DetailBill> arrayList, ClickListeners clickListeners) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickListeners = clickListeners;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(FragmentItemListDialogListDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DetailBill detailBill = arrayList.get(position);
        String ids = String.valueOf(arrayList.get(position).getId());
        holder.id.setText(ids);
        holder.name.setText(detailBill.getName());
        holder.quantity.setText(Integer.toString(detailBill.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null && arrayList.size()>0)
        return arrayList.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView id, name, quantity;
        public MyViewHolder(FragmentItemListDialogListDialogItemBinding binding) {
            super(binding.getRoot());
            id = binding.bottomIdCart;
            name = binding.bottomNameCart;
            quantity = binding.bottomQuantityCart;

            //dang ky su kien click cho view: cach 2.
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
//
        }

        @Override
        public void onClick(View v) {
            clickListeners.onItemClick(getAdapterPosition(),v);//callback forwarding
        }

        @Override
        public boolean onLongClick(View v) {
            clickListeners.onItemLongClick(getAdapterPosition(),v);
            return true;
        }
    }
    public interface ClickListeners{
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
