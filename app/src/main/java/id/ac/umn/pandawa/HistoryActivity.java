package id.ac.umn.pandawa;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;

public class HistoryActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    TextView categoryHistory, notesHistory, priceHistory,
            noTransaction, noPlanning;
    ImageView fotoUser;
    ProgressBar loading;
    LinearLayout linearLoading;

    RecyclerView rvHistory;
    TransactionAdapter mAdapter;
    LinkedList<TransactionData> mdata = new LinkedList<>();

    RecyclerView rvPlanning;
    PlanningAdapter mAdapterPlanning;
    LinkedList<TransactionData> mdataPlanning = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setActionBar("History");
        btnInitialize();
        readData();
    }
    public void setActionBar(String page) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

    }

    protected void btnInitialize() {
        categoryHistory = findViewById(R.id.categoryHistory);
        notesHistory = findViewById(R.id.notesHistory);
        priceHistory = findViewById(R.id.priceHistory);
        fotoUser = findViewById(R.id.fotoUser);

        rvHistory = findViewById(R.id.recyclerView);
        rvPlanning = findViewById(R.id.rvPlanning);

        loading = findViewById(R.id.progressBar);
        linearLoading = findViewById(R.id.linearLoading);

        noTransaction = findViewById(R.id.noTransaction);
        noTransaction.setVisibility(View.GONE);

        noPlanning = findViewById(R.id.noPlanning);
        noPlanning.setVisibility(View.GONE);
    }

    protected void readData() {
        if (user != null){
            readPlanning();
            readTransaction();
        } else {
            linearLoading.setVisibility(View.GONE);
            noTransaction.setVisibility(View.VISIBLE);
            noPlanning.setVisibility(View.VISIBLE);
        }

    }
    protected void readPlanning() {
        databaseReference = db.getReference("users").child(user.getUid()).child("planning");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        UserData dataTransaction = data.getValue(UserData.class);
                        System.out.println(
                                dataTransaction.notes
                        );

                        mdataPlanning.add(new TransactionData(
                                dataTransaction.category,
                                dataTransaction.money,
                                dataTransaction.notes,
                                dataTransaction.tanggal
                        ));
                    }
                    linearLoading.setVisibility(View.GONE);
                    mAdapterPlanning = new PlanningAdapter(HistoryActivity.this, mdataPlanning);
                    rvPlanning.setAdapter(mAdapterPlanning);
                    rvPlanning.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                } else {
                    noPlanning.setVisibility(View.VISIBLE);
                    linearLoading.setVisibility(View.GONE);
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
                        UserData dataTransaction = data.getValue(UserData.class);
                        System.out.println(
                                dataTransaction.notes
                        );
                        mdata.add(new TransactionData(
                                dataTransaction.category,
                                dataTransaction.money,
                                dataTransaction.notes,
                                dataTransaction.tanggal
                        ));
                    }
                    linearLoading.setVisibility(View.GONE);
                    mAdapter = new TransactionAdapter(HistoryActivity.this, mdata);
                    rvHistory.setAdapter(mAdapter);
                    rvHistory.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                } else {
                    noTransaction.setVisibility(View.VISIBLE);
                    linearLoading.setVisibility(View.GONE);
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
}