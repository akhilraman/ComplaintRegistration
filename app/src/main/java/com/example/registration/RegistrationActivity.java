package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

public class RegistrationActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference referenceExpert;

    ArrayList<Expert> expertList;

    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText title=findViewById(R.id.title);

        EditText regno=findViewById(R.id.regno);

        EditText name=findViewById(R.id.name);

        EditText incident_info=findViewById(R.id.name1);



        expertList= new ArrayList<>();


        Button button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String titleValue=title.getText().toString().trim();
                String regnoValue=regno.getText().toString().trim();
                String nameValue=name.getText().toString().trim();
                String incidentInfoValue=incident_info.getText().toString().trim();


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Complaints");

                //TO PICK A RANDOM EXPERT TO CONNECT WITH OUR USER
                referenceExpert = rootNode.getReference("Experts");
                referenceExpert.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Expert expert=new Expert("akhil","akhil1","akhil@g");
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            expert=dataSnapshot.getValue(Expert.class);
                            expertList.add(expert);
                        }
                        Random random= new Random();
                        int x=random.nextInt(expertList.size());

                        String uniqueID = UUID.randomUUID().toString();

                        referenceExpert = rootNode.getReference("Users");

                        referenceExpert.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    user=dataSnapshot.getValue(User.class);

                                    String u_email=user.getEmail();
                                    String check_email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                    Log.i("this",u_email);
                                    Log.i("2nd",check_email);
                                    if(u_email.equals(check_email)){
                                        Log.i("checker",check_email);
                                        Complaint c= new Complaint(uniqueID,titleValue,nameValue,regnoValue,incidentInfoValue,user,"registered",expertList.get(x));
                                        reference.child(uniqueID).setValue(c);
                                    }
                                }
                                Log.i("final",user.getName());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                HomeFragment.arrayList.clear();
                HomeFragment.adapter.notifyDataSetChanged();
                finish();

            }
        });
    }
}