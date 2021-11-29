package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.HashMap;

public class TransactionActivity extends AppCompatActivity {
    Button btnBatal, btnSimpan;
    Spinner category;
    EditText addMoney, addNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        setActionBar("Transaction");
        btnInitialize();

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(staticAdapter);

//        String[] items = new String[] { "Chai Latte", "Green Tea", "Black Tea" };
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, items);

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
        btnBatal = findViewById(R.id.btnBatal);
        btnSimpan = findViewById(R.id.btnSimpan);
        addMoney = findViewById(R.id.addmoney);
        addNotes = findViewById(R.id.addnotes);
        category = findViewById(R.id.static_spinner);
    }
    protected void btnFunction() {
        btnBatal.setOnClickListener(v -> finish());
        btnSimpan.setOnClickListener(v -> {
            String notes = addNotes.getText().toString();
            String money = addMoney.getText().toString();
            String valueCategory = category.getSelectedItem().toString();

            if(TextUtils.isEmpty(notes)) {
                addNotes.setError("Notes cannot be empty!");
                addNotes.requestFocus();
            } else if (TextUtils.isEmpty(money)) {
                addMoney.setError("Money cannot be empty!");
                addMoney.requestFocus();
            } else if (valueCategory.equals("Choose a Category")) {
                TextView errorSpinner = (TextView) category.getSelectedView();
                errorSpinner.setError("Category cannot be empty!");
                errorSpinner.requestFocus();
            } else {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                HashMap<String, String> transactionMap = new HashMap<>();
                transactionMap.put("notes", notes);
                transactionMap.put("money", money);
                transactionMap.put("category", valueCategory);
                transactionMap.put("tanggal", (mDay + "/" + (mMonth + 1) + "/" + mYear));

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DAOUsers dao = new DAOUsers();
                dao.addTransaction(user.getUid(), transactionMap);

                Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}