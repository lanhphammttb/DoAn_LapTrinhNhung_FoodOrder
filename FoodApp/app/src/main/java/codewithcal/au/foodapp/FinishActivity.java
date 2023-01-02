package codewithcal.au.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {
    TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        tvOk = findViewById(R.id.tv_ok);
    }
}