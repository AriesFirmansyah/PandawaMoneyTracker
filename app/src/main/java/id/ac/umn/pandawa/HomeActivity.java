package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth authentication;
    CardView btnTransaction, btnHistory, btnPlanning, btnReport;
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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeIntent = new Intent(HomeActivity.this,
                        HomeActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.action_logout:
                Intent logoutIntent = new Intent(HomeActivity.this,
                        LoginActivity.class);
                startActivity(logoutIntent);
                authentication.signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}