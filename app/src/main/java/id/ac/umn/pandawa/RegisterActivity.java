package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password;
    Button submitBTN;

    FirebaseAuth authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submitBTN = findViewById(R.id.submitBTN);
        authentication = FirebaseAuth.getInstance();
        TextView loginIntentLink = findViewById(R.id.signInIntent);

        setActionBar("Register");

        submitBTN.setOnClickListener(v -> createAuth());
        loginIntentLink.setOnClickListener(v -> {
            finish();
        });
    }
    public void setActionBar(String page) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

    }
    protected void createAuth() {
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        if(TextUtils.isEmpty(usernameString)) {
            username.setError("Username cannot be empty!");
            username.requestFocus();
        } else if (TextUtils.isEmpty(passwordString)) {
            password.setError("Password cannot be empty!");
            password.requestFocus();
        } else {
            authentication.createUserWithEmailAndPassword(usernameString, passwordString)
            .addOnCompleteListener(comp -> {
                if(comp.isSuccessful()) {
                    FirebaseUser user = authentication.getCurrentUser();
                    createUser(user);
                } else {
                    Toast.makeText(this, "Error : "
                            + Objects.requireNonNull(comp.getException()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void createUser(FirebaseUser user) {
        String usernameString = username.getText().toString();

        DAOUsers dao = new DAOUsers();
        Users dataUsers = new Users(user.getEmail());

        dao.add(user.getUid().toString(), dataUsers)
        .addOnSuccessListener(succ -> {
            Toast.makeText(this, "User registered successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = authentication.getCurrentUser();
        if (user != null){
            finish();
        }
//        FirebaseAuth.getInstance().signOut();

    }


}