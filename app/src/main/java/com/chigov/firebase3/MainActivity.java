package com.chigov.firebase3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Mike");
        user.put("lastname", "Chigov");
        user.put("age", "50");

//        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this, "Not added", Toast.LENGTH_SHORT).show();
//            }
//        });

//       db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//           @Override
//           public void onComplete(@NonNull Task<QuerySnapshot> task) {
//              if (task.isSuccessful()){
//                  Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                  for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
//                      Map<String,Object> user = documentSnapshot.getData();
//                      Log.d("test", documentSnapshot.getId() + " => " + documentSnapshot.getData());
//                      Log.i("test", Objects.requireNonNull(user.get("name")).toString());
//                      Log.i("test", Objects.requireNonNull(user.get("lastname")).toString());
//                      Log.i("test",user.get("age").toString());
//                  }
//              }
//           else {
//                  Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//              }
//           }
//       });
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){
                    for (QueryDocumentSnapshot documentSnapshot : value){
                      Map<String,Object> user = documentSnapshot.getData();
                      Log.d("test", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                      Log.i("test", Objects.requireNonNull(user.get("name")).toString());
                      Log.i("test", Objects.requireNonNull(user.get("lastname")).toString());
                      Log.i("test",user.get("age").toString());
                  }

                }else
                {
                    Toast.makeText(MainActivity.this, "Error reading data from Firebase", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}