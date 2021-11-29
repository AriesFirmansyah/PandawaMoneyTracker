package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class ReportPage1 extends AppCompatActivity {
    EditText date, dateEnd;
    DatePickerDialog datePickerDialog;
    Button btnReport;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page1);

        date = findViewById(R.id.date);
        dateEnd = findViewById(R.id.dateEnd);
        btnReport = findViewById(R.id.btnReport);

        setActionBar("Report");

        date.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            // date picker dialog
            datePickerDialog = new DatePickerDialog(ReportPage1.this,
            (view, year, monthOfYear, dayOfMonth) ->
                    date.setText(dayOfMonth + "/"  + (monthOfYear + 1) + "/" + year)
                    , mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        dateEnd.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            // date picker dialog
            datePickerDialog = new DatePickerDialog(ReportPage1.this,
                    (view, year, monthOfYear, dayOfMonth) ->
                            dateEnd.setText(dayOfMonth + "/"  + (monthOfYear + 1) + "/" + year)
                    , mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        btnReport.setOnClickListener(v -> {
            Intent reportIntent = new Intent(ReportPage1.this,
                    ReportPage2.class);
            reportIntent.putExtra("startdate", date.getText().toString());
            reportIntent.putExtra("enddate", dateEnd.getText().toString());
            startActivity(reportIntent);
        });
    }


    public void setActionBar(String page) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                finish();
                return true;
            case R.id.action_logout:
                mAuth.signOut();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}