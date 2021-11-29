package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth authentication;
    ImageView btnTransaction, btnHistory, btnPlanning, btnReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setActionBar("Home");

        btnInitialize();
        btnFunction();


    }
    public void setActionBar(String page) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    protected void btnInitialize() {
        btnTransaction = findViewById(R.id.btnTransaction);
        btnHistory = findViewById(R.id.btnHistory);
        btnPlanning = findViewById(R.id.btnPlanning);
        btnReport = findViewById(R.id.btnReport);
    }
    protected void btnFunction() {
        btnTransaction.setOnClickListener(v -> startActivity(new Intent(this, TransactionActivity.class)));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        btnPlanning.setOnClickListener(v -> startActivity(new Intent(this, PlanningActivity.class)));
        btnReport.setOnClickListener(v -> startActivity(new Intent(this, ReportPage1.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        authentication = FirebaseAuth.getInstance();
        FirebaseUser currentuser = authentication.getCurrentUser();

        if(currentuser == null) {
            Toast.makeText(this, "Not Signed in",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Welcome " + currentuser.getEmail(),
                    Toast.LENGTH_SHORT).show();
        }
//        authentication.signOut();
    }
}