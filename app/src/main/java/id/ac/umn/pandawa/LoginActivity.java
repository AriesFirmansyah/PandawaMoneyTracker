package id.ac.umn.pandawa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    ActionBar actionBar;

    Button btnLogin;
    EditText username, password;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 321;
    final String web_client = "1084214908538-fibtin9rt4gnknd9bs3309rku1vf1fpn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setActionBar("Login");

        btnLogin = findViewById(R.id.loginbtn);
        SignInButton btnGoogle = findViewById(R.id.googleBtn);

        mAuth = FirebaseAuth.getInstance();
        configure();

        btnGoogle.setOnClickListener(v -> signIn());
        btnLogin.setOnClickListener(v -> loginMain());

    }
    public void setActionBar(String page) {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(page);
    }

    protected void loginMain() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        if(TextUtils.isEmpty(usernameString)) {
            username.setError("Username cannot be empty!");
            username.requestFocus();
        } else if (TextUtils.isEmpty(passwordString)) {
            password.setError("Password cannot be empty!");
            password.requestFocus();
        } else {
           mAuth.signInWithEmailAndPassword(usernameString, passwordString)
           .addOnCompleteListener(comp -> {
              if (comp.isSuccessful()) {
                  Toast.makeText(this, "Log in successfully",
                          Toast.LENGTH_SHORT).show();
                  FirebaseUser user = mAuth.getCurrentUser();
                  updateUI(user);
              } else {
                  Toast.makeText(this, "Username or Password is wrong!",
                          Toast.LENGTH_SHORT).show();
              }
           });
        }
    }

    protected void configure() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(web_client)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                Log.w(TAG, "onActivityResult: " + e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
        .addOnCompleteListener(comp -> {
            if (comp.isSuccessful()) {
                Log.d(TAG, "signInWithCredential:success");
                FirebaseUser user = mAuth.getCurrentUser();
                createUser(user);
            } else {
                Log.w(TAG, "signInWithCredential:failure", comp.getException());
                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Toast.makeText(this, "Login successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));

        } else {
            Toast.makeText(this, "Login Error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void createUser(FirebaseUser user) {

        DAOUsers dao = new DAOUsers();
        Users dataUsers = new Users(user.getEmail());

        dao.add(user.getUid().toString(), dataUsers)
        .addOnSuccessListener(succ -> updateUI(user));
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
//        FirebaseAuth.getInstance().signOut();

    }

}