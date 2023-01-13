package codewithcal.au.foodapp;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import codewithcal.au.foodapp.adapter.PayDetailBillAdapter;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class CartFragment extends Fragment {
    private ArrayList<DetailBill> arrayList;
    private RecyclerView rcvCart;
    private PayDetailBillAdapter detailBillAdapter;
    private DatabaseHandler db;
    private int idDelete;
    private FloatingActionButton btnPay;
    private String id;
    private Toolbar toolbar;
    private TextView tvTotalPrice;

    public static CartFragment getInstance(String id) {
        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_id", id);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHandler(getContext());
        getAllBills();
        detailBillAdapter = new PayDetailBillAdapter(getContext(), arrayList, new PayDetailBillAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                rcvCart = view.findViewById(R.id.all_bill_recycler);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Do you want to delete this item?");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                //alertDialog.setMessage(selectedValue);
                idDelete = arrayList.get(position).getId();
                alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DetailBill x = arrayList.get(position);
                        db.deleteDetailBill(idDelete);
                        arrayList.remove(x);
                        detailBillAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        rcvCart = view.findViewById(R.id.all_bill_recycler);
        rcvCart.setAdapter(detailBillAdapter);
        rcvCart.addItemDecoration(new DividerItemDecoration(rcvCart.getContext(), DividerItemDecoration.VERTICAL));
        rcvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            this.id = getArguments().getString("user_id");
        }
        btnPay = view.findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPay();
            }
        });

        toolbar = view.findViewById(R.id.toolbar_total_price);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //activity.getSupportActionBar().setTitle("Total price: " + sum + " VNĐ");
        tvTotalPrice = view.findViewById(R.id.tv_total_price);

        ArrayList<String> list = db.getTotalPrice();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        if (list.isEmpty() || list == null || list.get(0) == null) {
            tvTotalPrice.setText("Total price: 0 VNĐ");
        } else if (list.size() >= 1) {
            for (int i = 0; i < list.size(); i++) {
                numbers.add(Integer.parseInt(list.get(i)));
            }
            int sum = 0;
            for (int i = 0; i < numbers.size(); i++) {
                sum += numbers.get(i);
            }
            tvTotalPrice.setText("Total price: " + String.valueOf(sum) + " VNĐ");
        }

        if(db.getDetailBillCount()==0){
            tvTotalPrice.setText("Total price: 0 VNĐ");
            arrayList.clear();
            detailBillAdapter.notifyDataSetChanged();
        }

    }

    private void getAllBills() {
        try {
            arrayList = new ArrayList<>();
            arrayList = db.getAllDetailBills();
        } catch (Exception ex) {
            Log.e("getAllDetailBills", ex.getMessage());
        }
    }

    private void onClickPay() {
        Intent it = new Intent(getContext(), PayActivity.class);
        it.putExtra("id", id);
        startActivity(it);
    }

    @Override
    public void onStart() {
        if(db.getDetailBillCount()==0){
            tvTotalPrice.setText("Total price: 0 VNĐ");
            arrayList.clear();
            detailBillAdapter.notifyDataSetChanged();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if(db.getDetailBillCount()==0){
            tvTotalPrice.setText("Total price: 0 VNĐ");
            arrayList.clear();
            detailBillAdapter.notifyDataSetChanged();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if(db.getDetailBillCount()==0){
            tvTotalPrice.setText("Total price: 0 VNĐ");
            arrayList.clear();
            detailBillAdapter.notifyDataSetChanged();
        }
        super.onPause();
    }
}
