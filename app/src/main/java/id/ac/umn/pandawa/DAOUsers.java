package id.ac.umn.pandawa;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class DAOUsers {

    final DatabaseReference databaseReference;
    public DAOUsers() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("users");
    }

    public Task<Void> add(String UID, Users user) {
        return databaseReference.child(UID).child("username").setValue(user);
    }

    public Task<Void> addTransaction(String UID, HashMap<String, String> transactionMap) {
        return databaseReference.child(UID).child("transaction").push().setValue(transactionMap);
    }

    public Task<Void> addPlanning(String UID, HashMap<String, String> planningMap) {
        return databaseReference.child(UID).child("planning").push().setValue(planningMap);
    }
//    databaseReference.push().setValue(user);
}
