package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnHome, btnReport, btnTransaction, btnPlanning,
            btnHistory, btnRegister, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnHome = findViewById(R.id.btnHome);
        btnReport = findViewById(R.id.btnReport1);
        btnTransaction = findViewById(R.id.btnTransaction);
        btnPlanning = findViewById(R.id.btnPlanning);
        btnHistory = findViewById(R.id.btnHistory);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogout = findViewById(R.id.btnLogout);

        setActionBar("Pandawa Money Tracker");

        btnLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(loginIntent);
        });
        btnHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(MainActivity.this,
                    HomeActivity.class);
            startActivity(homeIntent);
        });
        btnReport.setOnClickListener(v -> {
            Intent reportIntent = new Intent(MainActivity.this,
                    ReportPage1.class);
            startActivity(reportIntent);
        });
        btnTransaction.setOnClickListener(v -> {
            Intent transactionIntent = new Intent(MainActivity.this,
                    TransactionActivity.class);
            startActivity(transactionIntent);
        });
        btnPlanning.setOnClickListener(v -> {
            Intent planningIntent = new Intent(MainActivity.this,
                    PlanningActivity.class);
            startActivity(planningIntent);
        });
        btnHistory.setOnClickListener(v -> {
            Intent historyIntent = new Intent(MainActivity.this,
                    HistoryActivity.class);
            startActivity(historyIntent);
        });
        btnRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show();
        });
    }
    

    public void setActionBar(String page) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);
    }
}