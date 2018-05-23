package pr_2101.josselin.extruder;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by josselin on 18/05/18.
 * C'est le cas.
 */

public class Usr {
    private String id;
    private String name;
    private int rank;
    private int score;
    private DatabaseReference databaseReference;

    private Usr(String _id, String name) {
        this.id = _id;
        this.name = name;
        this.score = ((int) (Math.random() * 100));
        this.rank = -1;

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(_id);

    }

    private Usr(DataSnapshot _snapshot) {
        id = _snapshot.getKey();
        name = _snapshot.child("name").getValue(String.class);
        rank = _snapshot.child("rank").getValue(int.class);
        score = _snapshot.child("score").getValue(int.class);

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(id);
    }

    public static void createUser(String id, String name) {
        new Usr(id, name).sendToDatabase();
    }

    public static void createUser(FirebaseAuth auth, String name) {
        new Usr(auth.getCurrentUser().getUid(), name).sendToDatabase();
    }

    public void setScore(int _score) {
        this.score = _score;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    private void sendToDatabase() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("rank", rank);
        hashMap.put("score", score);

        databaseReference.setValue(hashMap);
    }

    public static void getAllUsers(Context context) {

        if (!(context instanceof OnRetrieveUsersListener)) {
            throw new RuntimeException(context.toString()
                    + " must implement OnRetrieveUsersListener");
        } else {
            final OnRetrieveUsersListener listener = (OnRetrieveUsersListener) context;

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Usr> users = new ArrayList<>();

                    for (DataSnapshot usr : dataSnapshot.getChildren()) {
                        users.add(new Usr(usr));
                    }
                    listener.onRetrieveUsers(users);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
