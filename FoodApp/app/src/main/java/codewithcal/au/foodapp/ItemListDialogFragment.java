package codewithcal.au.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import codewithcal.au.foodapp.adapter.BottomDetailBillAdapter;
import codewithcal.au.foodapp.adapter.DetailBillAdapter;
import codewithcal.au.foodapp.databinding.FragmentItemListDialogListDialogBinding;
import codewithcal.au.foodapp.model.DetailBill;
import codewithcal.au.foodapp.sqlite.DatabaseHandler;

public class ItemListDialogFragment extends BottomSheetDialogFragment {
    private ArrayList<DetailBill> arrayList;
    private BottomDetailBillAdapter bottomDetailBillAdapter;
    private DatabaseHandler db;
    private FragmentItemListDialogListDialogBinding binding;
    private RecyclerView rcvCart;
    private int idDelete;
    private FloatingActionButton btnPay;

    public static ItemListDialogFragment newInstance(int itemCount) {
        final ItemListDialogFragment fragment = new ItemListDialogFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        db = new DatabaseHandler(getContext());
        getAllBills();
        bottomDetailBillAdapter = new BottomDetailBillAdapter(getContext(), arrayList, new BottomDetailBillAdapter.ClickListeners() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                rcvCart = view.findViewById(R.id.all_bill_recycler);
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(getContext());
                alertDialog.setTitle("Bạn có chắc muốn xóa");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                //alertDialog.setMessage(selectedValue);
                idDelete = arrayList.get(position).getId();
                alertDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DetailBill x = arrayList.get(position);
                        db.deleteDetailBill(idDelete);
                        arrayList.remove(x);
                        bottomDetailBillAdapter.notifyDataSetChanged();
                    } });
                alertDialog.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //alertDialog.dismiss();
                    } });
                alertDialog.show();
            }
        });
        btnPay = view.findViewById(R.id.bottom_btn_pay);
        //btnPay = binding.bottomBtnPay;
        RecyclerView rcvCart = view.findViewById(R.id.list);
        rcvCart.setAdapter(bottomDetailBillAdapter);
        rcvCart.addItemDecoration(new DividerItemDecoration(rcvCart.getContext(), DividerItemDecoration.VERTICAL));
        rcvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickPay();}
        });
    }

    private void getAllBills() {
        try {
            arrayList = new ArrayList<>();
            arrayList = db.getAllDetailBills();
        } catch (Exception ex) {
            Log.e("getAllDetailBills", ex.getMessage());
        }
    }

    private void onClickPay(){
        Intent it = new Intent(getContext(), PayActivity.class);
        startActivity(it);
    }
}