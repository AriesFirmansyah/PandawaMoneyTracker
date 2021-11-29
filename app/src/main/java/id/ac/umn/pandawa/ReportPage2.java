package id.ac.umn.pandawa;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportPage2 extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    String startdate, enddate;
    Date dateStart, dateEnd;
    public float jumlah, jumlahTransaction;

    TextView totalPlanning, totalTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page2);

        setActionBar("Report");
        Calendar c = Calendar.getInstance();

        Intent intent = getIntent();
        startdate = intent.getStringExtra("startdate");
        enddate = intent.getStringExtra("enddate");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateStart = format.parse(startdate);
            dateEnd = format.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        readData();

    }

    public void setActionBar(String page) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

    }

    protected void readData() {
        readPlanning();
        readTransaction();
    }
    protected void readPlanning() {
        databaseReference = db.getReference("users").child(user.getUid()).child("planning");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        UserData dataPlanning = data.getValue(UserData.class);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            String tempData = dataPlanning.getTanggal();
                            Date temp = format.parse(String.valueOf(tempData));

//                            If temp after dateStart dan temp before dateEnd
                            if (temp.compareTo(dateStart) > 0 && temp.compareTo(dateEnd) < 0) {
                                jumlah += Float.parseFloat(dataPlanning.money);

                                System.out.println(
                                        "true"
                                );
                            } else {
                                System.out.println(
                                        "false"
                                );
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println(
                                jumlah
                        );
                    }
                    totalPlanning = findViewById(R.id.totalPlanning);

                    jumlah = (int) jumlah;

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    totalPlanning.setText(formatRupiah.format((double) jumlah));

                } else {
                    totalPlanning = findViewById(R.id.totalPlanning);
                    totalPlanning.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);
    }
    protected void readTransaction() {
        databaseReference = db.getReference("users").child(user.getUid()).child("transaction");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        UserData dataPlanning = data.getValue(UserData.class);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            String tempData = dataPlanning.getTanggal();
                            Date temp = format.parse(String.valueOf(tempData));

//                            If temp after dateStart dan temp before dateEnd
                            if (temp.compareTo(dateStart) > 0 && temp.compareTo(dateEnd) < 0) {
                                jumlahTransaction += Float.parseFloat(dataPlanning.money);

                                System.out.println(
                                        "true"
                                );
                            } else {
                                System.out.println(
                                        "false"
                                );
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println(
                                jumlahTransaction
                        );
                    }
                    totalTransaction = findViewById(R.id.totalTransaction);

                    jumlahTransaction = (int) jumlahTransaction;

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    totalTransaction.setText(formatRupiah.format((double) jumlahTransaction));

                } else {
                    totalTransaction = findViewById(R.id.totalPlanning);
                    totalTransaction.setText("0");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent report2Intent = new Intent(ReportPage2.this,
                        HomeActivity.class);
                startActivity(report2Intent);
                return true;
            case R.id.action_logout:
                mAuth.signOut();
                Intent logoutIntent = new Intent(ReportPage2.this,
                        HomeActivity.class);
                startActivity(logoutIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}