package com.example.android.hawkeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Validate extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase2;
    private DatabaseReference mDatabaseReference7;
    Button val;
    Button den;
    String id;
    TextView cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getStringExtra("ID");
        setContentView(R.layout.activity_validate);
        val=(Button)findViewById(R.id.accept);
        cid=(TextView)findViewById(R.id.c_id);
        den=(Button)findViewById(R.id.deny);
        mFirebaseDatabase2=FirebaseDatabase.getInstance();
        mDatabaseReference7=mFirebaseDatabase2.getReference().child("entrylog");


        cid.setText(id);

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Validate.this,UserActivity.class);
                i.putExtra("ID",id);
                startActivity(i);


                mDatabaseReference7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // dataSnapshot.getRef().child("cl_status").setValue(true);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (id.equals((String) snapshot.child("uid").getValue())) {
                                snapshot.child("exit_status").getRef().setValue(true);
                                break;
                            }
                        }

//                        Intent intent = new Intent(context,AdminActivity.class);
//                        intent.putExtra("ID",cl.get(i).getId());

                        Toast.makeText(Validate.this,"Validated Exit of your Vehicle",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Validate.this,UserActivity.class);
                        i.putExtra("ID",id);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


            }
        });
        den.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Validate.this,UserActivity.class);
                i.putExtra("ID",id);
                startActivity(i);
            }
        });
    }
}
